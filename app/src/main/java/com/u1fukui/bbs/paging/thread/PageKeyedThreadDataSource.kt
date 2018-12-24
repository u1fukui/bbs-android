package com.u1fukui.bbs.paging.thread

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import com.u1fukui.bbs.model.BbsThread
import com.u1fukui.bbs.paging.NetworkState
import com.u1fukui.bbs.repository.thread_list.ThreadListRepository
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class PageKeyedThreadDataSource(
    private val repository: ThreadListRepository,
    private val job: Job
) : PageKeyedDataSource<Long, BbsThread>() {

    val networkState = MutableLiveData<NetworkState>()
    val initialLoad = MutableLiveData<NetworkState>()

    override fun loadBefore(
        params: LoadParams<Long>,
        callback: LoadCallback<Long, BbsThread>
    ) {
        // ignored, since we only ever append to our initial load
    }

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, BbsThread>
    ) {

        fetchThreadList(null, params.requestedLoadSize) { threads, nextId ->
            callback.onResult(threads, null, nextId)
        }
    }

    override fun loadAfter(
        params: LoadParams<Long>,
        callback: LoadCallback<Long, BbsThread>
    ) {
        fetchThreadList(params.key, params.requestedLoadSize) { threads, nextId ->
            callback.onResult(threads, nextId)
        }
    }

    private fun fetchThreadList(
        lastId: Long?,
        size: Int,
        callback: (repos: List<BbsThread>, nextId: Long?) -> Unit
    ) {
        val isInitial = lastId == null
        updateNetworkState(NetworkState.LOADING, isInitial)
        launch(job + UI) {
            try {
                repository.fetchThreadList(lastId, size).await()
                    .let {
                        val nextKey = if (it.size < size) {
                            null
                        } else {
                            it[it.lastIndex].id
                        }
                        updateNetworkState(NetworkState.LOADED, isInitial)
                        callback(it, nextKey)
                    }
            } catch (t: Throwable) {
                val state = NetworkState.error(t)
                updateNetworkState(state, isInitial)
            }
        }
    }

    private fun updateNetworkState(state: NetworkState, isInitial: Boolean) {
        if (isInitial) initialLoad.postValue(state)
        networkState.postValue(state)
    }
}