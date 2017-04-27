package com.u1fukui.bbs.viewmodel;

import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;

import com.u1fukui.bbs.App;
import com.u1fukui.bbs.model.ApiResponse;
import com.u1fukui.bbs.model.Category;
import com.u1fukui.bbs.repository.ThreadRepository;
import com.u1fukui.bbs.view.helper.CreateThreadNavigator;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class InputThreadInfoViewModel implements ViewModel {

    public final Category category;

    public final ObservableInt loadingVisibility = new ObservableInt(View.GONE);

    public String title;

    public String description;

    private final ThreadRepository repository;

    private final CreateThreadNavigator navigator;

    public InputThreadInfoViewModel(Category category,
                                    ThreadRepository repository,
                                    CreateThreadNavigator navigator) {
        this.category = category;
        this.repository = repository;
        this.navigator = navigator;
    }

    public void onClickPostButton(View view) {
        //TOOD: 実装
        Log.d("TAG10", "click: title=" + title + ", description=" + description);

        if (loadingVisibility.get() == View.VISIBLE) {
            return;
        }
        loadingVisibility.set(View.VISIBLE);

        repository.postThread()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onSuccess(@NonNull ApiResponse apiResponse) {
                        loadingVisibility.set(View.GONE);
                        App.getInstance().getToastUtils().showToast("投稿しました");
                        navigator.finish();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        loadingVisibility.set(View.GONE);
                        //TOOD: エラー文言
                        App.getInstance().getToastUtils().showToast("エラー");
                    }
                });
    }

    @Override
    public void destroy() {

    }
}
