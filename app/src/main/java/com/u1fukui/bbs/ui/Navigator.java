package com.u1fukui.bbs.ui;


import android.app.Activity;

import javax.inject.Inject;

public class Navigator {

    private final Activity activity;

    @Inject
    public Navigator(Activity activity) {
        this.activity = activity;
    }

    public void finish() {
        activity.finish();
    }

    protected Activity getActivity() {
        return activity;
    }
}
