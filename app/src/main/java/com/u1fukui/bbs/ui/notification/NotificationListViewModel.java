package com.u1fukui.bbs.ui.notification;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.u1fukui.bbs.customview.ErrorView;
import com.u1fukui.bbs.helper.LoadingManager;
import com.u1fukui.bbs.model.Notification;
import com.u1fukui.bbs.repository.NotificationListRepository;
import com.u1fukui.bbs.ui.ViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import lombok.Getter;

public class NotificationListViewModel implements ViewModel, ErrorView.ErrorViewListener {

    public final LoadingManager loadingManager = new LoadingManager();

    @Getter
    private ObservableList<NotificationViewModel> notificationViewModelList = new ObservableArrayList<>();

    private final NotificationListRepository repository;

    @Inject
    public NotificationListViewModel(NotificationListRepository repository) {
        this.repository = repository;
    }

    public void start() {
        if (notificationViewModelList.isEmpty()) {
            fetchNotificationList();
        }
    }

    private void fetchNotificationList() {
        if (loadingManager.isLoading()) {
            return;
        }
        loadingManager.startLoading();

        repository.fetchNotificationList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Notification>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        // nop
                    }

                    @Override
                    public void onSuccess(@NonNull List<Notification> notificationList) {
                        List<NotificationViewModel> viewModelList = new ArrayList<>();
                        for (Notification notification : notificationList) {
                            viewModelList.add(new NotificationViewModel(notification));
                        }
                        renderNotificationList(viewModelList);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        loadingManager.showErrorView(e);
                    }
                });
    }

    private void renderNotificationList(List<NotificationViewModel> list) {
        notificationViewModelList.clear();
        notificationViewModelList.addAll(list);

        loadingManager.showContentView();
    }

    @Override
    public void destroy() {
    }

    @Override
    public void onClickReloadButton() {
        fetchNotificationList();
    }
}
