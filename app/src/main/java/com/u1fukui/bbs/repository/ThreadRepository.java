package com.u1fukui.bbs.repository;


import android.os.SystemClock;

import com.u1fukui.bbs.model.ApiResponse;
import com.u1fukui.bbs.model.Comment;
import com.u1fukui.bbs.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.annotations.NonNull;

public class ThreadRepository {

    public Single<List<Comment>> fetchCommentList(final long threadId) {
        return Single.create(new SingleOnSubscribe<List<Comment>>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<List<Comment>> e) throws Exception {
                SystemClock.sleep(1000);

                List<Comment> list = new ArrayList<>();
                for (int i = 1; i <= 20; i++) {
                    User author = new User(i, "コメンター" + i);
                    Comment comment = new Comment(i, threadId, i, "コメント", author, i, false, new Date());
                    list.add(comment);
                }
                e.onSuccess(list);
            }
        });
    }

    public Single<ApiResponse> postThread() {
        return Single.create(new SingleOnSubscribe<ApiResponse>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<ApiResponse> e) throws Exception {
                SystemClock.sleep(1000);
                e.onSuccess(new ApiResponse());
            }
        });
    }

    public Single<ApiResponse> postComment() {
        return Single.create(new SingleOnSubscribe<ApiResponse>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<ApiResponse> e) throws Exception {
                SystemClock.sleep(1000);
                e.onSuccess(new ApiResponse());
            }
        });
    }
}
