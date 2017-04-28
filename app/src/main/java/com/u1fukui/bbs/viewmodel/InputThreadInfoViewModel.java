package com.u1fukui.bbs.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.view.View;

import com.u1fukui.bbs.App;
import com.u1fukui.bbs.model.ApiResponse;
import com.u1fukui.bbs.model.Category;
import com.u1fukui.bbs.repository.ThreadRepository;
import com.u1fukui.bbs.utils.StringUtils;
import com.u1fukui.bbs.view.helper.CreateThreadNavigator;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class InputThreadInfoViewModel implements ViewModel {

    public static final int MAX_TITLE_LENGTH = 20;

    public static final int MAX_DESCRIPTION_LENGTH = 200;

    public final Category category;

    public final ObservableInt loadingVisibility = new ObservableInt(View.GONE);

    public final ObservableBoolean postButtonEnabled = new ObservableBoolean(false);

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

    public void onTitleTextChanged(CharSequence charSequence, int start , int before, int count) {
        boolean isValid = isValid(charSequence.toString(), MAX_TITLE_LENGTH)
                && isValid(description, MAX_DESCRIPTION_LENGTH);
        postButtonEnabled.set(isValid);
    }

    public void onDescriptionTextChanged(CharSequence charSequence, int start , int before, int count) {
        boolean isValid = isValid(charSequence.toString(), MAX_DESCRIPTION_LENGTH)
                && isValid(title, MAX_TITLE_LENGTH);
        postButtonEnabled.set(isValid);
    }

    public void onClickPostButton(View view) {
        boolean isValid = isValid(title, MAX_TITLE_LENGTH)
                && isValid(description, MAX_DESCRIPTION_LENGTH);

        if (!isValid) {
            return;
        } else if (loadingVisibility.get() == View.VISIBLE) {
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

    private boolean isValid(String text, int maxLength) {
        return !StringUtils.isBlank(text) && StringUtils.isLength(text) <= maxLength;
    }

    @Override
    public void destroy() {

    }
}
