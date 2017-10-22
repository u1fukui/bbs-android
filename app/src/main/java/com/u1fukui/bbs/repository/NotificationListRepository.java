package com.u1fukui.bbs.repository;


import android.os.SystemClock;

import com.u1fukui.bbs.model.Notification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class NotificationListRepository {

    @Inject
    public NotificationListRepository() {
    }

    public Single<List<Notification>> fetchNotificationList() {
        //TODO: API実装
        return Single.create(e -> {
            SystemClock.sleep(1000);

            List<Notification> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                list.add(new Notification((long) i, "http://example.com", "通知です" + i, new Date()));
            }
            e.onSuccess(list);
        });
    }
}
