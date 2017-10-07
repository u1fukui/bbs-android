package com.u1fukui.bbs.ui.notification;

import android.text.format.DateFormat;
import android.view.View;

import com.u1fukui.bbs.App;
import com.u1fukui.bbs.model.Notification;
import com.u1fukui.bbs.ui.ViewModel;

public class NotificationViewModel implements ViewModel {

    private static final String DATE_FORMAT_PATTERN = "yyyy/MM/dd(E) kk:mm";

    public final Notification notification;

    public final CharSequence createdAt;

    public NotificationViewModel(Notification notification) {
        this.notification = notification;
        this.createdAt = DateFormat.format(DATE_FORMAT_PATTERN, notification.createdAt);
    }

    public void onClickNotification(View view) {
        //TODO: 実装
        App.getInstance().getToastUtils().showToast("click notification " + notification.id);
    }

    @Override
    public void destroy() {
    }
}
