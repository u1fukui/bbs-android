package com.u1fukui.bbs.helper

import android.content.Context
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog

import com.u1fukui.bbs.R

import javax.inject.Inject

class DialogHelper @Inject
constructor(private val context: Context) {

    interface ConfirmDialogListener {

        fun onClickPositiveButton()

        fun onClickNegativeButton()
    }

    fun showConfirmDialog(@StringRes titleResId: Int,
                          @StringRes messageResId: Int,
                          listener: ConfirmDialogListener?) {

        AlertDialog.Builder(context, R.style.DialogTheme)
                .setTitle(titleResId)
                .setMessage(messageResId)
                .setPositiveButton(android.R.string.ok) { _, _ -> listener?.onClickPositiveButton() }
                .setNegativeButton(android.R.string.cancel) { _, _ -> listener?.onClickNegativeButton() }
                .show()
    }
}
