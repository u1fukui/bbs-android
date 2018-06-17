package com.u1fukui.bbs.ui.main

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableList
import com.u1fukui.bbs.App
import com.u1fukui.bbs.customview.ErrorView
import com.u1fukui.bbs.helper.LoadingManager
import com.u1fukui.bbs.model.BbsThread
import com.u1fukui.bbs.repository.thread_list.ThreadListRepository
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class ThreadListViewModel(
    private val repository: ThreadListRepository,
    private val navigator: ThreadListNavigator
) : ViewModel(), ErrorView.ErrorViewListener {

    //region DataBinding
    val loadingManager = LoadingManager()
    val refreshing = ObservableBoolean(false)
    //endregion

    internal val threadBindingModelList: ObservableList<ThreadBindingModel> = ObservableArrayList()
    private var isThreadListCompleted: Boolean = false

    private val job = Job()

    //region Databinding
    fun onSwipeRefresh() {
        refreshing.set(true)
        fetchThreadList(0)
    }
    //endregion

    fun start() {
        if (threadBindingModelList.isEmpty()) {
            fetchThreadList(0)
        }
    }

    fun loadNextPage() {
        if (threadBindingModelList.isEmpty()) {
            return
        }
        val thread = threadBindingModelList[threadBindingModelList.size - 1]
        fetchThreadList(thread.bbsThread.id)
    }

    private fun fetchThreadList(lastId: Long) {
        if (loadingManager.isLoading || isThreadListCompleted) {
            refreshing.set(false)
            return
        }
        loadingManager.startLoading()

        launch(job + UI) {
            val isListHead = lastId == 0L
            try {
                repository.fetchThreadList(lastId).await()
                    .let {
                        bindThreadList(it, isListHead)
                        loadingManager.showContentView()
                        refreshing.set(false)
                    }
            } catch (t: Throwable) {
                if (isListHead) {
                    loadingManager.showErrorView(t)
                } else {
                    App.getToastUtils().showToast("エラー") //TODO: エラーメッセージ
                    isThreadListCompleted = true
                    loadingManager.finishLoading()
                }
                refreshing.set(false)
            }
        }
    }

    private fun bindThreadList(list: List<BbsThread>, isListHead: Boolean) {
        if (isListHead) {
            threadBindingModelList.clear()
        }

        val bindingModelList = list.map { ThreadBindingModel(navigator, it) }
        threadBindingModelList.addAll(bindingModelList)

        //TODO: ちゃんとする
        isThreadListCompleted = list.size < 20
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }

    override fun onClickReloadButton() {
        fetchThreadList(0)
    }

    class Factory(
        private val repository: ThreadListRepository,
        private val navigator: ThreadListNavigator
    ) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = ThreadListViewModel(
            repository,
            navigator
        ) as T
    }
}
