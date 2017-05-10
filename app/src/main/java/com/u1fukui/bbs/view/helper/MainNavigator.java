package com.u1fukui.bbs.view.helper;


import android.app.Activity;
import android.content.Intent;

import com.u1fukui.bbs.view.activity.CreateThreadActivity;
import com.u1fukui.bbs.view.activity.MainActivity;

import javax.inject.Inject;

public class MainNavigator extends Navigator {

    private final MainActivity activity;

    @Inject
    public MainNavigator(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected Activity getActivity() {
        return activity;
    }

    public void navigateToCreateThreadPage() {
        Intent intent = new Intent(getActivity(), CreateThreadActivity.class);
        getActivity().startActivity(intent);
    }
}
