package com.u1fukui.bbs.viewmodel;

import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;

import com.u1fukui.bbs.model.Category;

public class InputThreadInfoViewModel implements ViewModel {

    public final Category category;

    public final ObservableInt loadingVisibility = new ObservableInt(View.GONE);

    public String title;

    public String description;

    public InputThreadInfoViewModel(Category category) {
        this.category = category;
    }

    public void onClickPostButton(View view) {
        //TOOD: 実装
        Log.d("TAG10", "click: title=" + title + ", description=" + description);
    }

    @Override
    public void destroy() {

    }
}
