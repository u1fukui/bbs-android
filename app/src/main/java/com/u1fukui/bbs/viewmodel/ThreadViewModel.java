package com.u1fukui.bbs.viewmodel;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;

import com.u1fukui.bbs.model.Thread;

public class ThreadViewModel implements ViewModel {

    private static final String DATE_FORMAT_PATTERN = "yyyy/MM/dd(E) kk:mm";

    public final Thread thread;

    public final String name;

    public final CharSequence updatedAt;

    public ThreadViewModel(Thread thread) {
        this.thread = thread;
        this.name = thread.getAuthor().name;
        this.updatedAt = DateFormat.format(DATE_FORMAT_PATTERN, thread.getUpdatedAt());
    }

    public void onClickThread(View view) {
        //TODO: 実装
        Log.d("TAG", "click thread: " + thread.getTitle());
    }

    @Override
    public void destroy() {

    }
}
