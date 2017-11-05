package com.u1fukui.bbs.ui.main

import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableList
import com.u1fukui.bbs.App
import com.u1fukui.bbs.customview.ErrorView
import com.u1fukui.bbs.helper.LoadingManager
import com.u1fukui.bbs.model.ThreadListResponse
import com.u1fukui.bbs.repository.ThreadListRepository
import com.u1fukui.bbs.ui.ViewModel
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ThreadListViewModel(
        private val repository: ThreadListRepository,
        private val navigator: ThreadListNavigator
) : ViewModel, ErrorView.ErrorViewListener {

    //region DataBinding
    val loadingManager = LoadingManager()

    val refreshing = ObservableBoolean(false)
    //endregion

    internal val threadViewModelList: ObservableList<ThreadViewModel> = ObservableArrayList()

    private var isThreadListCompleted: Boolean = false

    //region Databinding
    fun onSwipeRefresh() {
        refreshing.set(true)
        fetchThreadList(0)
    }
    //endregion

    fun start() {
        if (threadViewModelList.isEmpty()) {
            fetchThreadList(0)
        }
    }

    fun loadNextPage() {
        if (threadViewModelList.isEmpty()) {
            return
        }
        val thread = threadViewModelList[threadViewModelList.size - 1]
        fetchThreadList(thread.bbsThread.id)
    }

    private fun fetchThreadList(lastId: Long) {
        if (loadingManager.isLoading || isThreadListCompleted) {
            refreshing.set(false)
            return
        }
        loadingManager.startLoading()

        repository.fetchThreadList(lastId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<ThreadListResponse> {
                    override fun onSubscribe(@NonNull d: Disposable) {
                        // nop
                    }

                    override fun onSuccess(@NonNull response: ThreadListResponse) {
                        val viewModelList = response.threadList.map { ThreadViewModel(navigator, it) }

                        isThreadListCompleted = response.isCompleted
                        if (lastId == 0L) {
                            threadViewModelList.clear()
                        }
                        threadViewModelList.addAll(viewModelList)
                        loadingManager.showContentView()
                        refreshing.set(false)
                    }

                    override fun onError(@NonNull e: Throwable) {
                        if (lastId == 0L) {
                            loadingManager.showErrorView(e)
                        } else {
                            App.getToastUtils().showToast("エラー") //TODO: エラーメッセージ
                            isThreadListCompleted = true
                            loadingManager.finishLoading()
                        }
                        refreshing.set(false)

                    }
                })
    }

    override fun destroy() {}

    override fun onClickReloadButton() {
        fetchThreadList(0)
    }
}
