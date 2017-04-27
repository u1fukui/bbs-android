package com.u1fukui.bbs.repository;


import android.os.SystemClock;

import com.u1fukui.bbs.model.ApiResponse;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.annotations.NonNull;

public class ThreadRepository {

    public Single<ApiResponse> postThread() {
        return Single.create(new SingleOnSubscribe<ApiResponse>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<ApiResponse> e) throws Exception {
                SystemClock.sleep(1000);
                e.onSuccess(new ApiResponse());
            }
        });
    }
}
