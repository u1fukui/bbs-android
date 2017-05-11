package com.u1fukui.bbs.view.helper;


import android.app.Activity;
import android.content.Intent;

import com.u1fukui.bbs.model.BbsThread;
import com.u1fukui.bbs.view.activity.CreateCommentActivity;
import com.u1fukui.bbs.view.activity.ThreadDetailActivity;

public class ThreadDetailNavigator extends Navigator {

    private ThreadDetailActivity activity;

    public ThreadDetailNavigator(ThreadDetailActivity activity) {
        this.activity = activity;
    }

    @Override
    protected Activity getActivity() {
        return activity;
    }

    public void navigateToCreateCommentPage(BbsThread thread) {
        Intent intent = CreateCommentActivity.createIntent(activity, thread);
        activity.startActivity(intent);
    }
}
