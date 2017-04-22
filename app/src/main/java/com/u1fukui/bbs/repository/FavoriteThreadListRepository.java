package com.u1fukui.bbs.repository;


import android.os.SystemClock;

import com.u1fukui.bbs.model.BbsThread;
import com.u1fukui.bbs.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.annotations.NonNull;

public class FavoriteThreadListRepository implements ThreadListRepository {

    @Override
    public Single<List<BbsThread>> fetchThreadList() {
        //TODO: APIを使う
        return Single.create(new SingleOnSubscribe<List<BbsThread>>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<List<BbsThread>> e) throws Exception {
                SystemClock.sleep(1000);

                List<BbsThread> list = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    User author = new User(i, "作者" + i);
                    list.add(new BbsThread(i, "お気に入りスレッド" + i, author, i, new Date(), new Date()));
                }
                e.onSuccess(list);
            }
        });
    }
}
