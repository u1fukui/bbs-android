package com.u1fukui.bbs.view.helper;


import android.content.Intent;

import com.u1fukui.bbs.view.activity.CreateThreadActivity;
import com.u1fukui.bbs.view.activity.MainActivity;
import com.u1fukui.bbs.view.activity.NotificationListActivity;

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
