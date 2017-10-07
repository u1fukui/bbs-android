package com.u1fukui.bbs.ui.detail;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.view.View;

import com.u1fukui.bbs.customview.ErrorView;
import com.u1fukui.bbs.helper.LoadingManager;
import com.u1fukui.bbs.model.BbsThread;
import com.u1fukui.bbs.model.Comment;
import com.u1fukui.bbs.repository.ThreadRepository;
import com.u1fukui.bbs.ui.ViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import lombok.Getter;

public class ThreadDetailViewModel implements ViewModel, ErrorView.ErrorViewListener {

    //region DataBinding
    public final BbsThread bbsThread;

    public final ObservableBoolean refreshing = new ObservableBoolean(false);

    public final LoadingManager loadingManager = new LoadingManager();
    //endregion

    @Getter
    private ObservableList<CommentViewModel> commentViewModelList = new ObservableArrayList<>();

    private final ThreadRepository repository;

    private final ThreadDetailNavigator navigator;

    public ThreadDetailViewModel(BbsThread bbsThread,
                                 ThreadRepository repository,
                                 ThreadDetailNavigator navigator) {
        this.bbsThread = bbsThread;
        this.repository = repository;
        this.navigator = navigator;
    }

    //region Databinding
    public void onSwipeRefresh() {
        refreshing.set(true);
        fetchCommentList();
    }
    //endregion

    public void onClickFloatingActionButton(View view) {
        navigator.navigateToCreateCommentPage(bbsThread);
    }

    public void start() {
        if (commentViewModelList.isEmpty()) {
            fetchCommentList();
        }
    }

    private void fetchCommentList() {
        if (loadingManager.isLoading()) {
            refreshing.set(false);
            return;
        }
        loadingManager.startLoading();

        repository.fetchCommentList(bbsThread.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Comment>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        // nop
                    }

                    @Override
                    public void onSuccess(@NonNull List<Comment> bbsComments) {
                        List<CommentViewModel> viewModelList = new ArrayList<>();
                        for (Comment comment : bbsComments) {
                            viewModelList.add(new CommentViewModel(comment));
                        }
                        renderCommentList(viewModelList);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        loadingManager.showErrorView(e);
                    }
                });
    }

    private void renderCommentList(List<CommentViewModel> list) {
        commentViewModelList.clear();
        commentViewModelList.addAll(list);

        loadingManager.showContentView();
        refreshing.set(false);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void onClickReloadButton() {
        fetchCommentList();
    }
}
