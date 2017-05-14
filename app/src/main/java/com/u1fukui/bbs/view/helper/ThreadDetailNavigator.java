package com.u1fukui.bbs.view.helper;


import android.content.Intent;

import com.u1fukui.bbs.model.BbsThread;
import com.u1fukui.bbs.view.activity.CreateCommentActivity;
import com.u1fukui.bbs.view.activity.ThreadDetailActivity;

public class ThreadDetailNavigator extends Navigator {

    public ThreadDetailNavigator(ThreadDetailActivity activity) {
        super(activity);
    }

    public void navigateToCreateCommentPage(BbsThread thread) {
        Intent intent = CreateCommentActivity.createIntent(getActivity(), thread);
        getActivity().startActivity(intent);
    }
}
