package com.u1fukui.bbs.ui.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import com.u1fukui.bbs.customview.ErrorView
import com.u1fukui.bbs.helper.LoadingManager
import com.u1fukui.bbs.repository.NotificationListRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class NotificationListViewModel(
        private val repository: NotificationListRepository
) : ViewModel(), ErrorView.ErrorViewListener {

    val loadingManager = LoadingManager()

    //TOOD: 整理する
    internal val notificationBindingModelList: ObservableList<NotificationBindingModel> = ObservableArrayList()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun start() {
        if (notificationBindingModelList.isEmpty()) {
            fetchNotificationList()
        }
    }

    private fun fetchNotificationList() {
        if (loadingManager.isLoading) {
            return
        }
        loadingManager.startLoading()

        repository.fetchNotificationList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = { response ->
                            val bindingModelList = response.map { NotificationBindingModel(it) }
                            renderNotificationList(bindingModelList)
                        },
                        onError = {
                            loadingManager.showErrorView(it)
                        }
                )
                .addTo(compositeDisposable)
    }

    private fun renderNotificationList(list: List<NotificationBindingModel>) {
        notificationBindingModelList.clear()
        notificationBindingModelList.addAll(list)

        loadingManager.showContentView()
    }

    override fun onClickReloadButton() {
        fetchNotificationList()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    class Factory(
            private val repository: NotificationListRepository
    ) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = NotificationListViewModel(
                repository
        ) as T
    }
}
