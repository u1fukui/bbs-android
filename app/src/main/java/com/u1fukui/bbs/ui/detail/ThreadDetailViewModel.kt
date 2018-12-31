package com.u1fukui.bbs.ui.detail

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import android.view.View
import com.u1fukui.bbs.App
import com.u1fukui.bbs.customview.ErrorView
import com.u1fukui.bbs.helper.LoadingManager
import com.u1fukui.bbs.model.BbsThread
import com.u1fukui.bbs.paging.Status
import com.u1fukui.bbs.paging.comment.CommentDataSourceFactory
import com.u1fukui.bbs.repository.ThreadRepository
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlin.coroutines.CoroutineContext

class ThreadDetailViewModel(
    private val owner: LifecycleOwner,
    val bbsThread: BbsThread,
    private val repository: ThreadRepository,
    private val navigator: ThreadDetailNavigator
) : ViewModel(), ErrorView.ErrorViewListener, CoroutineScope {

    //region CoroutineScope
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    //endregion

    //region DataBinding
    val refreshing = MutableLiveData<Boolean>()
    val loadingManager = LoadingManager()
    //endregion

    internal val commentBindingModelList: LiveData<PagedList<CommentBindingModel>>

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val job = Job()

    private val factory = CommentDataSourceFactory(repository, bbsThread.id, this)

    init {
        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(ThreadDetailViewModel.PAGE_SIZE)
            .setPageSize(ThreadDetailViewModel.PAGE_SIZE)
            .setEnablePlaceholders(false)
            .setPrefetchDistance(5)
            .build()

        commentBindingModelList = LivePagedListBuilder(
            factory.map { CommentBindingModel(it) },
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

    //region DataBinding
    fun onSwipeRefresh() {
        refreshing.postValue(true)
        factory.source.invalidate()
    }
    //endregion

    fun onClickFloatingActionButton(view: View) {
        navigator.navigateToCreateCommentPage(bbsThread)
    }

    override fun onClickReloadButton() {
        factory.source.invalidate()
    }

    override fun onCleared() {
        compositeDisposable.clear()
        job.cancelChildren()
        super.onCleared()
    }

    class Factory(
        private val lifecycle: LifecycleOwner,
        private val bbsThread: BbsThread,
        private val repository: ThreadRepository,
        private val navigator: ThreadDetailNavigator
    ) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = ThreadDetailViewModel(
            lifecycle,
            bbsThread,
            repository,
            navigator
        ) as T
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
