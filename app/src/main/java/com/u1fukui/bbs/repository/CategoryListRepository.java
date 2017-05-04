package com.u1fukui.bbs.repository;


import android.os.SystemClock;

import com.u1fukui.bbs.model.Category;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.annotations.NonNull;

public class CategoryListRepository {

    @Inject
    public CategoryListRepository() {
    }

    public Single<List<Category>> fetchCategoryList() {
        //TODO: API実装
        return Single.create(new SingleOnSubscribe<List<Category>>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<List<Category>> e) throws Exception {
                SystemClock.sleep(1000);

                List<Category> list = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    list.add(new Category(i, "カテゴリ" + i));
                }
                e.onSuccess(list);
            }
        });
    }
}
