package com.u1fukui.bbs.repository;


import android.os.SystemClock;

import com.u1fukui.bbs.model.Category;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class CategoryListRepository {

    @Inject
    public CategoryListRepository() {
    }

    public Single<List<Category>> fetchCategoryList() {
        //TODO: API実装
        return Single.create(e -> {
            SystemClock.sleep(1000);

            List<Category> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                list.add(new Category(i, "カテゴリ" + i));
            }
            e.onSuccess(list);
        });
    }
}
