package com.u1fukui.bbs.customview;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class RecyclerViewScrolledEndSubject {

    enum Irrelevant {
        INSTANCE
    }

    private static final int THRESHOLD_COUNT = 5;

    private RecyclerView recyclerView;

    private Subject subject = PublishSubject.create();

    private RecyclerView.OnScrollListener listener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();

            int visibleCount = recyclerView.getChildCount();
            int totalCount = manager.getItemCount();
            int firstPos = manager.findFirstVisibleItemPosition();

            if (totalCount <= firstPos + visibleCount + THRESHOLD_COUNT) {
                subject.onNext(Irrelevant.INSTANCE);
            }
        }
    };

    public RecyclerViewScrolledEndSubject(RecyclerView recyclerView) {
        if (!(recyclerView.getLayoutManager() instanceof LinearLayoutManager)) {
            throw new IllegalArgumentException("RecyclerView needs to have LinearLayoutManager");
        }
        this.recyclerView = recyclerView;
        this.recyclerView.addOnScrollListener(listener);
    }

    public Subject connect() {
        return subject;
    }

    public void shutdown() {
        recyclerView.removeOnScrollListener(listener);
        recyclerView = null;
    }
}
