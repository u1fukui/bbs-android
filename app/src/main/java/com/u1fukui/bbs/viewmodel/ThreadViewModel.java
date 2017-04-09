package com.u1fukui.bbs.viewmodel;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;

import com.u1fukui.bbs.model.BbsThread;

public class ThreadViewModel implements ViewModel {

    private static final String DATE_FORMAT_PATTERN = "yyyy/MM/dd(E) kk:mm";

    public final BbsThread bbsThread;

    public final CharSequence updatedAt;

    public ThreadViewModel(BbsThread bbsThread) {
        this.bbsThread = bbsThread;
        this.updatedAt = DateFormat.format(DATE_FORMAT_PATTERN, bbsThread.updatedAt);
    }

    public void onClickThread(View view) {
        //TODO: 実装
        Log.d("TAG", "click bbsThread: " + bbsThread.getTitle());
    }

    @Override
    public void destroy() {

    }
}
