package com.u1fukui.bbs.utils;


import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

public class ToastUtils {

    private final Context context;

    private Toast toast;

    public ToastUtils(Context context) {
        this.context = context;
    }

    public void showToast(@StringRes int messageId) {
        showToast(context.getString(messageId));
    }

    public void showToast(CharSequence message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
