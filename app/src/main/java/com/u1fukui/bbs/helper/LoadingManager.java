package com.u1fukui.bbs.helper;


import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

public class LoadingManager {

    public final ObservableInt contentVisibility = new ObservableInt(View.GONE);

    public final ObservableInt loadingVisibility = new ObservableInt(View.GONE);

    public final ObservableInt errorViewVisibility = new ObservableInt(View.GONE);

    public final ObservableField<String> errorMessage = new ObservableField<>();

    public boolean isLoading() {
        return loadingVisibility.get() == View.VISIBLE;
    }

    public void startLoading() {
        loadingVisibility.set(View.VISIBLE);
    }

    public void finishLoading() {
        loadingVisibility.set(View.GONE);
    }

    public void showContentView() {
        errorViewVisibility.set(View.GONE);
        contentVisibility.set(View.VISIBLE);
        finishLoading();
    }

    public void showErrorView(Throwable t) {
        contentVisibility.set(View.GONE);

        //TODO: エラー文言
        errorMessage.set("エラーです");
        errorViewVisibility.set(View.VISIBLE);

        finishLoading();
    }
}
