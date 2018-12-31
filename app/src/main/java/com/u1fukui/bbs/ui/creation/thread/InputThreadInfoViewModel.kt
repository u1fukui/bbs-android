package com.u1fukui.bbs.ui.creation.thread

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import android.view.View
import com.u1fukui.bbs.App
import com.u1fukui.bbs.R
import com.u1fukui.bbs.helper.DialogHelper
import com.u1fukui.bbs.model.Category
import com.u1fukui.bbs.repository.ThreadRepository
import com.u1fukui.bbs.utils.StringUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class InputThreadInfoViewModel(
        val category: Category,
        private val repository: ThreadRepository,
        private val navigator: CreateThreadNavigator,
        private val dialogHelper: DialogHelper
) : ViewModel() {

    //region DataBinding
    val loadingVisibility = ObservableInt(View.GONE)
    val postButtonEnabled = ObservableBoolean(false)
    var title: String? = null
    var description: String? = null
    //endregion

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun onTitleTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
        val isValid = isValid(charSequence.toString(), MAX_TITLE_LENGTH) && isValid(description, MAX_DESCRIPTION_LENGTH)
        postButtonEnabled.set(isValid)
    }

    fun onDescriptionTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
        val isValid = isValid(charSequence.toString(), MAX_DESCRIPTION_LENGTH) && isValid(title, MAX_TITLE_LENGTH)
        postButtonEnabled.set(isValid)
    }

    fun onClickPostButton(view: View) {
        dialogHelper.showConfirmDialog(R.string.create_thread_confirm_dialog_title,
                R.string.create_thread_confirm_dialog_description,
                object : DialogHelper.ConfirmDialogListener {
                    override fun onClickPositiveButton() {
                        postThread()
                    }

                    override fun onClickNegativeButton() {
                        // nop
                    }
                })
    }

    private fun postThread() {
        val isValid = isValid(title, MAX_TITLE_LENGTH) && isValid(description, MAX_DESCRIPTION_LENGTH)

        if (!isValid) {
            return
        } else if (loadingVisibility.get() == View.VISIBLE) {
            return
        }
        loadingVisibility.set(View.VISIBLE)

        repository.postThread()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            loadingVisibility.set(View.GONE)
                            App.getToastUtils().showToast(R.string.create_thread_complete_toast)
                            navigator.finish()
                        },
                        onError = {
                            loadingVisibility.set(View.GONE)
                            //TODO: apply correct message and avoid using static method
                            App.getToastUtils().showToast("エラー")
                        }
                )
                .addTo(compositeDisposable)
    }

    private fun isValid(text: String?, maxLength: Int): Boolean {
        return !StringUtils.isBlank(text) && StringUtils.isLength(text) <= maxLength
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    companion object {

        const val MAX_TITLE_LENGTH = 20

        const val MAX_DESCRIPTION_LENGTH = 200
    }

    class Factory(
            private val category: Category,
            private val repository: ThreadRepository,
            private val navigator: CreateThreadNavigator,
            private val dialogHelper: DialogHelper
    ) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = InputThreadInfoViewModel(
                category,
                repository,
                navigator,
                dialogHelper
        ) as T
    }
}
