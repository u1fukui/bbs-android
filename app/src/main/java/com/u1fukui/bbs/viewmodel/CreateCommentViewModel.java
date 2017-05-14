package com.u1fukui.bbs.viewmodel;


import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.view.View;

import com.u1fukui.bbs.App;
import com.u1fukui.bbs.R;
import com.u1fukui.bbs.model.ApiResponse;
import com.u1fukui.bbs.model.BbsThread;
import com.u1fukui.bbs.model.User;
import com.u1fukui.bbs.repository.ThreadRepository;
import com.u1fukui.bbs.utils.StringUtils;
import com.u1fukui.bbs.view.helper.CreateCommentNavigator;
import com.u1fukui.bbs.view.helper.DialogHelper;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CreateCommentViewModel implements ViewModel {

    public static final int MAX_DESCRIPTION_LENGTH = 200;

    public final ObservableInt loadingVisibility = new ObservableInt(View.GONE);

    public final ObservableBoolean postButtonEnabled = new ObservableBoolean(false);

    public final BbsThread bbsThread;

    public final User user;

    public String description;

    private final ThreadRepository repository;

    private final CreateCommentNavigator navigator;

    private final DialogHelper dialogHelper;

    public CreateCommentViewModel(BbsThread bbsThread,
                                  User user,
                                  ThreadRepository repository,
                                  CreateCommentNavigator navigator,
                                  DialogHelper dialogHelper) {
        this.bbsThread = bbsThread;
        this.user = user;
        this.repository = repository;
        this.navigator = navigator;
        this.dialogHelper = dialogHelper;
    }

    public void onDescriptionTextChanged(CharSequence charSequence, int start , int before, int count) {
        boolean isValid = isValid(charSequence.toString(), MAX_DESCRIPTION_LENGTH);
        postButtonEnabled.set(isValid);
    }

    public void onClickPostButton(View view) {
        dialogHelper.showConfirmDialog(R.string.create_comment_confirm_dialog_title,
                R.string.create_comment_confirm_dialog_description,
                new DialogHelper.ConfirmDialogListener() {
                    @Override
                    public void onClickPositiveButton() {
                        postComment();
                    }

                    @Override
                    public void onClickNegativeButton() {
                        // nop
                    }
                });
    }

    private void postComment() {
        boolean isValid = isValid(description, MAX_DESCRIPTION_LENGTH);

        if (!isValid) {
            return;
        } else if (loadingVisibility.get() == View.VISIBLE) {
            return;
        }
        loadingVisibility.set(View.VISIBLE);

        repository.postComment()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onSuccess(@NonNull ApiResponse apiResponse) {
                        loadingVisibility.set(View.GONE);
                        App.getInstance().getToastUtils().showToast(R.string.create_comment_complete_toast);
                        navigator.finish();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        loadingVisibility.set(View.GONE);
                        //TODO: エラー文言
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
