package com.u1fukui.bbs.helper

import android.content.Context
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog

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
                          listener: ConfirmDialogListener) {

        AlertDialog.Builder(context, R.style.DialogTheme)
                .setTitle(titleResId)
                .setMessage(messageResId)
                .setPositiveButton(android.R.string.ok) { dialogInterface, i -> listener.onClickPositiveButton() }
                .setNegativeButton(android.R.string.cancel) { dialogInterface, i -> listener.onClickNegativeButton() }
                .show()
    }
}
