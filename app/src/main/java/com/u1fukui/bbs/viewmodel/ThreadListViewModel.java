package com.u1fukui.bbs.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;

import com.u1fukui.bbs.App;
import com.u1fukui.bbs.model.BbsThread;
import com.u1fukui.bbs.model.ThreadListResponse;
import com.u1fukui.bbs.repository.ThreadListRepository;
import com.u1fukui.bbs.view.customview.ErrorView;
import com.u1fukui.bbs.view.helper.LoadingManager;
import com.u1fukui.bbs.view.helper.ThreadListNavigator;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import lombok.Getter;

public class ThreadListViewModel implements ViewModel, ErrorView.ErrorViewListener {

    //region DataBinding
    public final LoadingManager loadingManager = new LoadingManager();

    public final ObservableBoolean refreshing = new ObservableBoolean(false);
    //endregion

    @Getter
    private ObservableList<ThreadViewModel> threadViewModelList = new ObservableArrayList<>();

    private boolean isThreadListCompleted;

    private final ThreadListRepository repository;

    private final ThreadListNavigator navigator;

    public ThreadListViewModel(ThreadListRepository repository, ThreadListNavigator navigator) {
        this.repository = repository;
        this.navigator = navigator;
    }

    //region Databinding
    public void onSwipeRefresh() {
        refreshing.set(true);
        fetchThreadList(0);
    }
    //endregion

    public void start() {
        if (threadViewModelList.isEmpty()) {
            fetchThreadList(0);
        }
    }

    public void loadNextPage() {
        if (threadViewModelList.isEmpty()) {
            return;
        }
        ThreadViewModel thread = threadViewModelList.get(threadViewModelList.size() - 1);
        fetchThreadList(thread.bbsThread.id);
    }

    private void fetchThreadList(final long lastId) {
        if (loadingManager.isLoading() || isThreadListCompleted) {
            refreshing.set(false);
            return;
        }
        loadingManager.startLoading();

        repository.fetchThreadList(lastId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ThreadListResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        // nop
                    }

                    @Override
                    public void onSuccess(@NonNull ThreadListResponse response) {
                        List<ThreadViewModel> viewModelList = new ArrayList<>();
                        for (BbsThread thread : response.threadList) {
                            viewModelList.add(new ThreadViewModel(navigator, thread));
                        }

                        isThreadListCompleted = response.isCompleted;
                        if (lastId == 0) {
                            threadViewModelList.clear();
                        }
                        threadViewModelList.addAll(viewModelList);
                        loadingManager.showContentView();
                        refreshing.set(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (lastId == 0) {
                            loadingManager.showErrorView(e);
                        } else {
                            App.getInstance().getToastUtils().showToast("エラー"); //TODO: エラーメッセージ
                            isThreadListCompleted = true;
                            loadingManager.finishLoading();
                        }
                        refreshing.set(false);

                    }
                });
    }

    @Override
    public void destroy() {
    }

    @Override
    public void onClickReloadButton() {
        fetchThreadList(0);
    }
}
