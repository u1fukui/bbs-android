package com.u1fukui.bbs.viewmodel;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.view.View;

import com.u1fukui.bbs.model.BbsThread;
import com.u1fukui.bbs.model.User;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;

public class ThreadListViewModel implements ViewModel {

    //region DataBinding
    public final ObservableBoolean refreshing = new ObservableBoolean(false);

    public final ObservableInt loadingVisibility = new ObservableInt(View.GONE);
    //endregion

    @Getter
    private ObservableList<ThreadViewModel> threadViewModelList = new ObservableArrayList<>();

    private WeakReference<Context> contextRef;

    public ThreadListViewModel(Context context) {
        contextRef = new WeakReference<>(context);
    }

    //region Databinding
    public void onSwipeRefresh() {
        refreshing.set(true);
        loadThreadList();
    }
    //endregion

    public void start() {
        loadThreadList();
    }

    private void loadThreadList() {
        if (loadingVisibility.get() == View.VISIBLE) {
            refreshing.set(false);
            return;
        }
        loadingVisibility.set(View.VISIBLE);

        //TODO: サーバからデータを取得する
        List<ThreadViewModel> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            User author = new User(i, "作者" + i);
            BbsThread bbsThread = new BbsThread(i, "タイトル" + i, author, i, new Date(), new Date());
            list.add(new ThreadViewModel(contextRef.get(), bbsThread));
        }
        renderThreadList(list);
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
