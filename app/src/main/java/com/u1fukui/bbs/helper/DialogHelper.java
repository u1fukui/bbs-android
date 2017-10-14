package com.u1fukui.bbs.helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

import com.u1fukui.bbs.R;

import javax.inject.Inject;

public class DialogHelper {

    public interface ConfirmDialogListener {

        void onClickPositiveButton();

        void onClickNegativeButton();
    }

    private final Context context;

    @Inject
    public DialogHelper(Context context) {
        this.context = context;
    }

    public void showConfirmDialog(@StringRes int titleResId,
                                  @StringRes int messageResId,
                                  @NonNull final ConfirmDialogListener listener) {

        new AlertDialog.Builder(context, R.style.DialogTheme)
                .setTitle(titleResId)
                .setMessage(messageResId)
                .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> listener.onClickPositiveButton())
                .setNegativeButton(android.R.string.cancel, (dialogInterface, i) -> listener.onClickNegativeButton())
                .show();
    }
}
