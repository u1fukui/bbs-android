package com.u1fukui.bbs.viewmodel;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;

import com.u1fukui.bbs.model.Comment;

public class CommentViewModel implements ViewModel {

    private static final String DATE_FORMAT_PATTERN = "yyyy/MM/dd(E) kk:mm";

    public final Comment comment;

    public final String commentNumber;

    public final String userName;

    public final CharSequence createdAt;

    public CommentViewModel(Comment comment) {
        this.comment = comment;
        this.commentNumber = Integer.toString(comment.displayNumber);
        this.userName = comment.author.name;
        this.createdAt = DateFormat.format(DATE_FORMAT_PATTERN, comment.createdAt);
    }

    public void onClickComment(View view) {
        //TODO: 実装
        Log.d("TAG", "click comment: " + comment.id);
    }

    @Override
    public void destroy() {

    }
}
