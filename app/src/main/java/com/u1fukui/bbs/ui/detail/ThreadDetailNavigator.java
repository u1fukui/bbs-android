package com.u1fukui.bbs.ui.detail;


import android.content.Intent;

import com.u1fukui.bbs.model.BbsThread;
import com.u1fukui.bbs.ui.creation.comment.CreateCommentActivity;
import com.u1fukui.bbs.ui.Navigator;

public class ThreadDetailNavigator extends Navigator {

    public ThreadDetailNavigator(ThreadDetailActivity activity) {
        super(activity);
    }

    public void navigateToCreateCommentPage(BbsThread thread) {
        Intent intent = CreateCommentActivity.createIntent(getActivity(), thread);
        getActivity().startActivity(intent);
    }
}
