package com.u1fukui.bbs.ui.notification

import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.u1fukui.bbs.customview.ErrorView
import com.u1fukui.bbs.helper.LoadingManager
import com.u1fukui.bbs.model.Notification
import com.u1fukui.bbs.repository.NotificationListRepository
import com.u1fukui.bbs.ui.BindingModel
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NotificationListBindingModel @Inject
constructor(private val repository: NotificationListRepository) : BindingModel, ErrorView.ErrorViewListener {

    val loadingManager = LoadingManager()

    //TOOD: 整理する
    internal val notificationBindingModelList: ObservableList<NotificationBindingModel> = ObservableArrayList()

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
                .subscribe(object : SingleObserver<List<Notification>> {
                    override fun onSubscribe(@NonNull d: Disposable) {
                        // nop
                    }

                    override fun onSuccess(@NonNull notificationList: List<Notification>) {
                        val bindingModelList = notificationList.map { NotificationBindingModel(it) }
                        renderNotificationList(bindingModelList)

                    }

                    override fun onError(@NonNull e: Throwable) {
                        loadingManager.showErrorView(e)
                    }
                })
    }

    private fun renderNotificationList(list: List<NotificationBindingModel>) {
        notificationBindingModelList.clear()
        notificationBindingModelList.addAll(list)

        loadingManager.showContentView()
    }

    override fun destroy() {}

    override fun onClickReloadButton() {
        fetchNotificationList()
    }
}
