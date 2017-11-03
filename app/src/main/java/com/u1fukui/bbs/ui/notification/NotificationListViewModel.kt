package com.u1fukui.bbs.ui.notification

import android.databinding.ObservableArrayList
import android.databinding.ObservableList

import com.u1fukui.bbs.customview.ErrorView
import com.u1fukui.bbs.helper.LoadingManager
import com.u1fukui.bbs.model.Notification
import com.u1fukui.bbs.repository.NotificationListRepository
import com.u1fukui.bbs.ui.ViewModel

import java.util.ArrayList

import javax.inject.Inject

import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class NotificationListViewModel @Inject
constructor(private val repository: NotificationListRepository) : ViewModel, ErrorView.ErrorViewListener {

    val loadingManager = LoadingManager()

    //TOOD: 整理する
    internal val notificationViewModelList: ObservableList<NotificationViewModel> = ObservableArrayList()

    fun start() {
        if (notificationViewModelList.isEmpty()) {
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
                .subscribe(object : SingleObserver<List<Notification>> {
                    override fun onSubscribe(@NonNull d: Disposable) {
                        // nop
                    }

                    override fun onSuccess(@NonNull notificationList: List<Notification>) {
                        val viewModelList = ArrayList<NotificationViewModel>()
                        for (notification in notificationList) {
                            viewModelList.add(NotificationViewModel(notification))
                        }
                        renderNotificationList(viewModelList)

                    }

                    override fun onError(@NonNull e: Throwable) {
                        loadingManager.showErrorView(e)
                    }
                })
    }

    private fun renderNotificationList(list: List<NotificationViewModel>) {
        notificationViewModelList.clear()
        notificationViewModelList.addAll(list)

        loadingManager.showContentView()
    }

    override fun destroy() {}

    override fun onClickReloadButton() {
        fetchNotificationList()
    }
}
