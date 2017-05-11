package com.u1fukui.bbs.view.helper;


import android.app.Activity;
import android.content.Intent;

import com.u1fukui.bbs.model.BbsThread;
import com.u1fukui.bbs.view.activity.ThreadDetailActivity;

public abstract class ThreadListNavigator extends Navigator {

    public void navigateToThreadDetailPage(BbsThread thread) {
        Activity activity = getActivity();
        Intent intent = ThreadDetailActivity.createIntent(activity, thread);
        activity.startActivity(intent);
    }
}
