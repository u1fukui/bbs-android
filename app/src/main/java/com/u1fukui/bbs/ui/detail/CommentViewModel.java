package com.u1fukui.bbs.ui.detail;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;

import com.u1fukui.bbs.model.Comment;
import com.u1fukui.bbs.ui.ViewModel;

public class CommentViewModel implements ViewModel {

    private static final String DATE_FORMAT_PATTERN = "yyyy/MM/dd(E) kk:mm";

    public final Comment comment;

    public final CharSequence createdAt;

    public final ObservableBoolean isLiked;

    public final ObservableInt likeCount;

    public CommentViewModel(Comment comment) {
        this.comment = comment;
        this.createdAt = DateFormat.format(DATE_FORMAT_PATTERN, comment.createdAt);
        this.isLiked = new ObservableBoolean(comment.isLiked);
        this.likeCount = new ObservableInt(comment.likeCount);
    }

    public void onClickComment(View view) {
        //TODO: 実装
        Log.d("TAG", "click comment: " + comment.id);
    }

    public void onClickLikeButton(View view) {
        isLiked.set(!isLiked.get());
        likeCount.set(isLiked.get() ? likeCount.get() + 1 : likeCount.get() - 1);

        //TODO: アニメーション
    }

    @Override
    public void destroy() {

    }
}
