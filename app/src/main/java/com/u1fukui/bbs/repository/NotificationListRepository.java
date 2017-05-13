package com.u1fukui.bbs.repository;


import android.os.SystemClock;

import com.u1fukui.bbs.model.Notification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.annotations.NonNull;

public class NotificationListRepository {

    @Inject
    public NotificationListRepository() {
    }

    public Single<List<Notification>> fetchNotificationList() {
        //TODO: API実装
        return Single.create(new SingleOnSubscribe<List<Notification>>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<List<Notification>> e) throws Exception {
                SystemClock.sleep(1000);

                List<Notification> list = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    list.add(new Notification(i, "http://example.com", "通知です" + i, new Date()));
                }
                e.onSuccess(list);
            }
        });
    }
}
