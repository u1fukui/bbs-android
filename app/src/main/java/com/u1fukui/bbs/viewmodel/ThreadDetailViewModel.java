package com.u1fukui.bbs.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.view.View;

import com.u1fukui.bbs.model.BbsThread;
import com.u1fukui.bbs.model.Comment;
import com.u1fukui.bbs.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;

public class ThreadDetailViewModel implements ViewModel {

    //region DataBinding
    public final BbsThread bbsThread;

    public final ObservableBoolean refreshing = new ObservableBoolean(false);

    public final ObservableInt loadingVisibility = new ObservableInt(View.GONE);
    //endregion

    @Getter
    private ObservableList<CommentViewModel> commentViewModelList = new ObservableArrayList<>();

    public ThreadDetailViewModel(BbsThread bbsThread) {
        this.bbsThread = bbsThread;
    }

    //region Databinding
    public void onSwipeRefresh() {
        refreshing.set(true);
        loadCommentList();
    }
    //endregion

    public void start() {
        loadCommentList();
    }

    private void loadCommentList() {
        if (loadingVisibility.get() == View.VISIBLE) {
            refreshing.set(false);
            return;
        }
        loadingVisibility.set(View.VISIBLE);

        //TODO: サーバからデータを取得する
        List<CommentViewModel> list = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            User author = new User(i, "コメンター" + i);
            Comment comment = new Comment(i, bbsThread.id, i, "コメント", author, i, new Date());
            list.add(new CommentViewModel(comment));
        }
        renderCommentList(list);
    }

    private void renderCommentList(List<CommentViewModel> list) {
        commentViewModelList.clear();
        commentViewModelList.addAll(list);

        loadingVisibility.set(View.GONE);
        refreshing.set(false);
    }

    @Override
    public void destroy() {

    }
}
