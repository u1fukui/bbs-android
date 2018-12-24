package com.u1fukui.bbs.paging.comment

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import com.u1fukui.bbs.model.Comment
import com.u1fukui.bbs.paging.NetworkState
import com.u1fukui.bbs.repository.ThreadRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PageKeyedCommentDataSource(
    private val repository: ThreadRepository,
    private val threadId: Long,
    private val coroutineScope: CoroutineScope
) : PageKeyedDataSource<Long, Comment>() {

    val networkState = MutableLiveData<NetworkState>()
    val initialLoad = MutableLiveData<NetworkState>()

    override fun loadBefore(
        params: LoadParams<Long>,
        callback: LoadCallback<Long, Comment>
    ) {
        // ignored, since we only ever append to our initial load
    }

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, Comment>
    ) {

        fetchCommentList(null, params.requestedLoadSize) { comments, nextId ->
            callback.onResult(comments, null, nextId)
        }
    }

    override fun loadAfter(
        params: LoadParams<Long>,
        callback: LoadCallback<Long, Comment>
    ) {
        fetchCommentList(params.key, params.requestedLoadSize) { comments, nextId ->
            callback.onResult(comments, nextId)
        }
    }

    private fun fetchCommentList(
        lastId: Long?,
        size: Int,
        callback: (repos: List<Comment>, nextId: Long?) -> Unit
    ) {
        val isInitial = lastId == null
        updateNetworkState(NetworkState.LOADING, isInitial)
        coroutineScope.launch {
            try {
                repository.fetchCommentList(threadId, lastId, size).await()
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