package com.u1fukui.bbs.view.helper;


import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

import com.u1fukui.bbs.R;

public abstract class Navigator {

    public interface ConfirmDialogListener {

        void onClickPositiveButton();

        void onClickNegativeButton();
    }

    protected abstract Activity getActivity();

    public void showConfirmDialog(@StringRes int titleResId,
                                   @StringRes int messageResId,
                                   @NonNull final ConfirmDialogListener listener) {

        new AlertDialog.Builder(getActivity(), R.style.DialogTheme)
                .setTitle(titleResId)
                .setMessage(messageResId)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onClickPositiveButton();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onClickNegativeButton();
                    }
                })
                .show();
    }
}
