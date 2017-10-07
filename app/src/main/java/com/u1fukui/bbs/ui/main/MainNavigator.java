package com.u1fukui.bbs.ui.main;


import android.content.Intent;

import com.u1fukui.bbs.ui.creation.thread.CreateThreadActivity;
import com.u1fukui.bbs.ui.notification.NotificationListActivity;

import javax.inject.Inject;

public class MainNavigator extends ThreadListNavigator {

    @Inject
    public MainNavigator(MainActivity activity) {
        super(activity);
    }

    public void navigateToCreateThreadPage() {
        Intent intent = new Intent(getActivity(), CreateThreadActivity.class);
        getActivity().startActivity(intent);
    }

    public void navigateToNotificationPage() {
        Intent intent = new Intent(getActivity(), NotificationListActivity.class);
        getActivity().startActivity(intent);
    }
}
