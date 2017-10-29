package com.u1fukui.bbs.ui.creation.comment


import android.databinding.ObservableBoolean
import android.databinding.ObservableInt
import android.view.View

import com.u1fukui.bbs.App
import com.u1fukui.bbs.R
import com.u1fukui.bbs.model.ApiResponse
import com.u1fukui.bbs.model.BbsThread
import com.u1fukui.bbs.model.User
import com.u1fukui.bbs.repository.ThreadRepository
import com.u1fukui.bbs.utils.StringUtils
import com.u1fukui.bbs.ui.Navigator
import com.u1fukui.bbs.helper.DialogHelper
import com.u1fukui.bbs.ui.ViewModel

import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CreateCommentViewModel(val bbsThread: BbsThread,
                             val user: User,
                             private val repository: ThreadRepository,
                             private val navigator: Navigator,
                             private val dialogHelper: DialogHelper) : ViewModel {

    val loadingVisibility = ObservableInt(View.GONE)

    val postButtonEnabled = ObservableBoolean(false)

    var description: String? = null

    fun onDescriptionTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
        val isValid = isValid(charSequence.toString(), MAX_DESCRIPTION_LENGTH)
        postButtonEnabled.set(isValid)
    }

    fun onClickPostButton(view: View) {
        dialogHelper.showConfirmDialog(R.string.create_comment_confirm_dialog_title,
                R.string.create_comment_confirm_dialog_description,
                object : DialogHelper.ConfirmDialogListener {
                    override fun onClickPositiveButton() {
                        postComment()
                    }

                    override fun onClickNegativeButton() {
                        // nop
                    }
                })
    }

    private fun postComment() {
        val isValid = isValid(description, MAX_DESCRIPTION_LENGTH)

        if (!isValid) {
            return
        } else if (loadingVisibility.get() == View.VISIBLE) {
            return
        }
        loadingVisibility.set(View.VISIBLE)

        repository.postComment()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<ApiResponse> {
                    override fun onSubscribe(@NonNull d: Disposable) {}

                    override fun onSuccess(@NonNull apiResponse: ApiResponse) {
                        loadingVisibility.set(View.GONE)
                        App.getToastUtils()!!.showToast(R.string.create_comment_complete_toast)
                        navigator.finish()
                    }

                    override fun onError(@NonNull e: Throwable) {
                        loadingVisibility.set(View.GONE)
                        //TODO: エラー文言
                        App.getToastUtils()!!.showToast("エラー")
                    }
                })
    }

    private fun isValid(text: String?, maxLength: Int): Boolean {
        return !StringUtils.isBlank(text) && StringUtils.isLength(text) <= maxLength
    }

    override fun destroy() {

    }

    companion object {

        val MAX_DESCRIPTION_LENGTH = 200
    }
}
