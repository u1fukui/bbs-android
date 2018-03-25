package com.u1fukui.bbs.ui.detail

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableList
import android.view.View
import com.u1fukui.bbs.customview.ErrorView
import com.u1fukui.bbs.helper.LoadingManager
import com.u1fukui.bbs.model.BbsThread
import com.u1fukui.bbs.repository.ThreadRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ThreadDetailViewModel(
        val bbsThread: BbsThread,
        private val repository: ThreadRepository,
        private val navigator: ThreadDetailNavigator
) : ViewModel(), ErrorView.ErrorViewListener {

    //region DataBinding
    val refreshing = ObservableBoolean(false)
    val loadingManager = LoadingManager()
    //endregion

    //TODO: 整理する
    internal val commentBindingModelList: ObservableList<CommentBindingModel> = ObservableArrayList()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    //region DataBinding
    fun onSwipeRefresh() {
        refreshing.set(true)
        fetchCommentList()
    }
    //endregion

    fun onClickFloatingActionButton(view: View) {
        navigator.navigateToCreateCommentPage(bbsThread)
    }

    fun start() {
        if (commentBindingModelList.isEmpty()) {
            fetchCommentList()
        }
    }

    private fun fetchCommentList() {
        if (loadingManager.isLoading) {
            refreshing.set(false)
            return
        }
        loadingManager.startLoading()

        repository.fetchCommentList(bbsThread.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            val bindingModelList = it.map { CommentBindingModel(it) }
                            renderCommentList(bindingModelList)
                        },
                        onError = {
                            loadingManager.showErrorView(it)
                        }
                )
                .addTo(compositeDisposable)
    }

    private fun renderCommentList(list: List<CommentBindingModel>) {
        commentBindingModelList.clear()
        commentBindingModelList.addAll(list)

        loadingManager.showContentView()
        refreshing.set(false)
    }

    override fun onClickReloadButton() {
        fetchCommentList()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    class Factory(
            private val bbsThread: BbsThread,
            private val repository: ThreadRepository,
            private val navigator: ThreadDetailNavigator
    ) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = ThreadDetailViewModel(
                bbsThread,
                repository,
                navigator
        ) as T
    }
}
