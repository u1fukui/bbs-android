package com.u1fukui.bbs.ui.creation.thread;

import android.view.View;

import com.u1fukui.bbs.model.Category;
import com.u1fukui.bbs.ui.ViewModel;

public class CategoryViewModel implements ViewModel {

    public final Category category;

    private final CreateThreadNavigator navigator;

    public CategoryViewModel(Category category, CreateThreadNavigator navigator) {
        this.category = category;
        this.navigator = navigator;
    }

    public void onClickCategory(View view) {
        navigator.navigateToInputInfoPage(category);
    }

    @Override
    public void destroy() {
    }
}
