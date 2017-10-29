package com.u1fukui.bbs.ui.creation.thread;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.view.View;

import com.u1fukui.bbs.App;
import com.u1fukui.bbs.R;
import com.u1fukui.bbs.helper.DialogHelper;
import com.u1fukui.bbs.model.ApiResponse;
import com.u1fukui.bbs.model.Category;
import com.u1fukui.bbs.repository.ThreadRepository;
import com.u1fukui.bbs.utils.StringUtils;
import com.u1fukui.bbs.ui.ViewModel;

import javax.inject.Inject;

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

    private final DialogHelper dialogHelper;

    @Inject
    public InputThreadInfoViewModel(Category category,
                                    ThreadRepository repository,
                                    CreateThreadNavigator navigator,
                                    DialogHelper dialogHelper) {
        this.category = category;
        this.repository = repository;
        this.navigator = navigator;
        this.dialogHelper = dialogHelper;
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
        dialogHelper.showConfirmDialog(R.string.create_thread_confirm_dialog_title,
                R.string.create_thread_confirm_dialog_description,
                new DialogHelper.ConfirmDialogListener() {
                    @Override
                    public void onClickPositiveButton() {
                        postThread();
                    }

                    @Override
                    public void onClickNegativeButton() {
                        // nop
                    }
                });
    }

    private void postThread() {
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
                        App.getToastUtils().showToast(R.string.create_thread_complete_toast);
                        navigator.finish();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        loadingVisibility.set(View.GONE);
                        //TODO: エラー文言
                        App.getToastUtils().showToast("エラー");
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
