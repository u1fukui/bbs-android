package com.u1fukui.bbs.ui.main

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.u1fukui.bbs.App
import com.u1fukui.bbs.customview.ErrorView
import com.u1fukui.bbs.helper.LoadingManager
import com.u1fukui.bbs.paging.Status
import com.u1fukui.bbs.paging.ThreadDataSourceFactory
import com.u1fukui.bbs.repository.thread_list.ThreadListRepository
import kotlinx.coroutines.experimental.Job

class ThreadListViewModel(
    private val owner: LifecycleOwner,
    private val repository: ThreadListRepository,
    private val navigator: ThreadListNavigator
) : ViewModel(), ErrorView.ErrorViewListener {

    //region DataBinding
    val loadingManager = LoadingManager()
    val refreshing = MutableLiveData<Boolean>()
    //endregion

    //    internal val threadBindingModelList: ObservableList<ThreadBindingModel> = ObservableArrayList()

    private val job = Job()

    internal val threadBindingModelList: LiveData<PagedList<ThreadBindingModel>>

    val factory = ThreadDataSourceFactory(repository, job)

    init {
        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(false)
            .setPrefetchDistance(5)
            .build()

        threadBindingModelList = LivePagedListBuilder(
            factory.map { ThreadBindingModel(navigator, it) },
            config
        ).build()

        observeNetworkState()
    }

    private fun observeNetworkState() {
        factory.source.initialLoad.observe(owner, Observer {
            when (it?.status) {
                Status.RUNNING -> loadingManager.startLoading()
                Status.SUCCESS -> {
                    loadingManager.showContentView()
                    refreshing.postValue(false)
                }
                Status.FAILED -> {
                    loadingManager.showErrorView(it.throwable!!)
                    refreshing.postValue(false)
                }
            }
        })

        factory.source.networkState.observe(owner, Observer {
            when (it?.status) {
                Status.RUNNING -> loadingManager.startLoading()
                Status.SUCCESS -> loadingManager.finishLoading()
                Status.FAILED -> {
                    loadingManager.finishLoading()
                    App.getToastUtils().showApiError(it.throwable!!)
                }
            }
        })
    }

    //region Databinding
    fun onSwipeRefresh() {
        refreshing.postValue(true)
        factory.source.invalidate()
    }
    //endregion

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }

    override fun onClickReloadButton() {
        factory.source.invalidate()
    }

    class Factory(
        private val lifecycle: LifecycleOwner,
        private val repository: ThreadListRepository,
        private val navigator: ThreadListNavigator
    ) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = ThreadListViewModel(
            lifecycle,
            repository,
            navigator
        ) as T
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
