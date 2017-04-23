package com.u1fukui.bbs.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.view.View;

import com.u1fukui.bbs.model.BbsThread;
import com.u1fukui.bbs.repository.ThreadListRepository;
import com.u1fukui.bbs.view.activity.CreateThreadActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import lombok.Getter;

public class ThreadListViewModel implements ViewModel {

    //region DataBinding
    public final ObservableBoolean refreshing = new ObservableBoolean(false);

    public final ObservableInt loadingVisibility = new ObservableInt(View.GONE);
    //endregion

    @Getter
    private ObservableList<ThreadViewModel> threadViewModelList = new ObservableArrayList<>();

    private WeakReference<Context> contextRef;

    private ThreadListRepository repository;

    public ThreadListViewModel(Context context, ThreadListRepository repository) {
        this.contextRef = new WeakReference<>(context);
        this.repository = repository;
    }

    //region Databinding
    public void onSwipeRefresh() {
        refreshing.set(true);
        loadThreadList();
    }

    public void onClickFloatingActionButton(View view) {
        Context context = contextRef.get();
        if (context != null) {
            context.startActivity(new Intent(context, CreateThreadActivity.class));
        }
    }
    //endregion

    public void start() {
        if (threadViewModelList.isEmpty()) {
            loadThreadList();
        }
    }

    private void loadThreadList() {
        if (loadingVisibility.get() == View.VISIBLE) {
            refreshing.set(false);
            return;
        }
        loadingVisibility.set(View.VISIBLE);

        repository.fetchThreadList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<BbsThread>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        // nop
                    }

                    @Override
                    public void onSuccess(@NonNull List<BbsThread> bbsThreads) {
                        List<ThreadViewModel> viewModelList = new ArrayList<>();
                        for (BbsThread thread : bbsThreads) {
                            viewModelList.add(new ThreadViewModel(contextRef.get(), thread));
                        }
                        renderThreadList(viewModelList);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        //TODO: 実装
                    }
                });
    }

    private void renderThreadList(List<ThreadViewModel> list) {
        threadViewModelList.clear();
        threadViewModelList.addAll(list);

        loadingVisibility.set(View.GONE);
        refreshing.set(false);
    }

    @Override
    public void destroy() {

    }
}
