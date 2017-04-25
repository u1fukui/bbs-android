package com.u1fukui.bbs.viewmodel;

import android.view.View;

import com.u1fukui.bbs.model.Category;

public class CategoryViewModel implements ViewModel {

    public final Category category;

    public CategoryViewModel(Category category) {
        this.category = category;
    }

    public void onClickCategory(View view) {
        //TODO: 実装
    }

    @Override
    public void destroy() {
    }
}
