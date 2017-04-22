package com.u1fukui.bbs.viewmodel;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.View;

import com.u1fukui.bbs.model.BbsThread;
import com.u1fukui.bbs.view.activity.ThreadDetailActivity;

import java.lang.ref.WeakReference;

public class ThreadViewModel implements ViewModel {

    private static final String DATE_FORMAT_PATTERN = "yyyy/MM/dd(E) kk:mm";

    public final BbsThread bbsThread;

    public final CharSequence updatedAt;

    private WeakReference<Context> contextRef;

    public ThreadViewModel(Context context, BbsThread bbsThread) {
        this.contextRef = new WeakReference<>(context);
        this.bbsThread = bbsThread;
        this.updatedAt = DateFormat.format(DATE_FORMAT_PATTERN, bbsThread.updatedAt);
    }

    public void onClickThread(View view) {
        Context context = contextRef.get();
        if (context != null) {
            context.startActivity(ThreadDetailActivity.createIntent(context, bbsThread));
        }
    }

    @Override
    public void destroy() {
    }
}
