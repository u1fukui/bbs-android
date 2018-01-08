package com.u1fukui.bbs.ui.detail

import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableList
import android.view.View
import com.u1fukui.bbs.customview.ErrorView
import com.u1fukui.bbs.helper.LoadingManager
import com.u1fukui.bbs.model.BbsThread
import com.u1fukui.bbs.model.Comment
import com.u1fukui.bbs.repository.ThreadRepository
import com.u1fukui.bbs.ui.BindingModel
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ThreadDetailBindingModel(//region DataBinding
        val bbsThread: BbsThread,
        private val repository: ThreadRepository,
        private val navigator: ThreadDetailNavigator
) : BindingModel, ErrorView.ErrorViewListener {

    val refreshing = ObservableBoolean(false)

    val loadingManager = LoadingManager()
    //endregion

    //TODO: 整理する
    internal val commentBindingModelList: ObservableList<CommentBindingModel> = ObservableArrayList()

    //region Databinding
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
                .subscribe(object : SingleObserver<List<Comment>> {
                    override fun onSubscribe(@NonNull d: Disposable) {
                        // nop
                    }

                    override fun onSuccess(@NonNull bbsComments: List<Comment>) {
                        val bindingModelList = bbsComments.map { CommentBindingModel(it) }
                        renderCommentList(bindingModelList)
                    }

                    override fun onError(@NonNull e: Throwable) {
                        loadingManager.showErrorView(e)
                    }
                })
    }

    private fun renderCommentList(list: List<CommentBindingModel>) {
        commentBindingModelList.clear()
        commentBindingModelList.addAll(list)

        loadingManager.showContentView()
        refreshing.set(false)
    }

    override fun destroy() {

    }

    override fun onClickReloadButton() {
        fetchCommentList()
    }
}
