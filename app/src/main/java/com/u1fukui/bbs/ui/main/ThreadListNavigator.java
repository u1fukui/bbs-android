package com.u1fukui.bbs.ui.main;


import android.app.Activity;
import android.content.Intent;

import com.u1fukui.bbs.ui.Navigator;
import com.u1fukui.bbs.model.BbsThread;
import com.u1fukui.bbs.ui.detail.ThreadDetailActivity;

public class ThreadListNavigator extends Navigator {

    public ThreadListNavigator(Activity activity) {
        super(activity);
    }

    public void navigateToThreadDetailPage(BbsThread thread) {
        Intent intent = ThreadDetailActivity.createIntent(getActivity(), thread);
        getActivity().startActivity(intent);
    }
}
