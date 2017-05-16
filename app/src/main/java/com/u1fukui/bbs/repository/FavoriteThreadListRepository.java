package com.u1fukui.bbs.repository;


import android.os.SystemClock;

import com.u1fukui.bbs.model.BbsThread;
import com.u1fukui.bbs.model.ThreadListResponse;
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
    public Single<ThreadListResponse> fetchThreadList(final long lastId) {
        //TODO: APIを使う
        return Single.create(new SingleOnSubscribe<ThreadListResponse>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<ThreadListResponse> e) throws Exception {
                SystemClock.sleep(1000);

                List<BbsThread> list = new ArrayList<>();
                for (long i = lastId + 1; i <= lastId + 20; i++) {
                    User author = new User(i, "作者" + i);
                    list.add(new BbsThread(i, "お気に入りスレッド" + i, author, 0, new Date(), new Date()));
                }

                ThreadListResponse res = new ThreadListResponse();
                res.status = 200;
                res.threadList = list;
                res.isCompleted = lastId >= 100;
                e.onSuccess(res);
            }
        });
    }
}
