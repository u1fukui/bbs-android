package com.u1fukui.bbs.view.helper;


import android.app.Activity;

import com.u1fukui.bbs.view.activity.CreateCommentActivity;

import javax.inject.Inject;

public class CreateCommentNavigator extends Navigator {

    private final CreateCommentActivity activity;

    @Inject
    public CreateCommentNavigator(CreateCommentActivity activity) {
        this.activity = activity;
    }

    @Override
    protected Activity getActivity() {
        return activity;
    }

    public void finish() {
        getActivity().finish();
    }
}
