package com.u1fukui.bbs.viewmodel;

import android.text.format.DateFormat;
import android.view.View;

import com.u1fukui.bbs.model.BbsThread;
import com.u1fukui.bbs.view.helper.ThreadListNavigator;

public class ThreadViewModel implements ViewModel {

    private static final String DATE_FORMAT_PATTERN = "yyyy/MM/dd(E) kk:mm";

    public final BbsThread bbsThread;

    public final CharSequence updatedAt;

    private final ThreadListNavigator navigator;

    public ThreadViewModel(ThreadListNavigator navigator, BbsThread bbsThread) {
        this.navigator = navigator;
        this.bbsThread = bbsThread;
        this.updatedAt = DateFormat.format(DATE_FORMAT_PATTERN, bbsThread.updatedAt);
    }

    public void onClickThread(View view) {
        navigator.navigateToThreadDetailPage(bbsThread);
    }

    @Override
    public void destroy() {
    }
}
