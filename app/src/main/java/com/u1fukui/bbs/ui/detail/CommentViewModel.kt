package com.u1fukui.bbs.ui.detail

import android.databinding.ObservableBoolean
import android.databinding.ObservableInt
import android.text.format.DateFormat
import android.util.Log
import android.view.View

import com.u1fukui.bbs.model.Comment
import com.u1fukui.bbs.ui.ViewModel

class CommentViewModel(val comment: Comment) : ViewModel {

    val createdAt: CharSequence

    val isLiked: ObservableBoolean

    val likeCount: ObservableInt

    init {
        this.createdAt = DateFormat.format(DATE_FORMAT_PATTERN, comment.createdAt)
        this.isLiked = ObservableBoolean(comment.isLiked)
        this.likeCount = ObservableInt(comment.likeCount)
    }

    fun onClickComment(view: View) {
        //TODO: 実装
        Log.d("TAG", "click comment: " + comment.id)
    }

    fun onClickLikeButton(view: View) {
        isLiked.set(!isLiked.get())
        likeCount.set(if (isLiked.get()) likeCount.get() + 1 else likeCount.get() - 1)

        //TODO: アニメーション
    }

    override fun destroy() {

    }

    companion object {

        private val DATE_FORMAT_PATTERN = "yyyy/MM/dd(E) kk:mm"
    }
}
