package com.u1fukui.bbs.view.helper;


import android.app.Activity;

import com.u1fukui.bbs.model.Category;
import com.u1fukui.bbs.view.activity.CreateThreadActivity;
import com.u1fukui.bbs.view.fragment.InputThreadInfoFragment;

import javax.inject.Inject;

public class CreateThreadNavigator extends Navigator {

    private final CreateThreadActivity activity;

    @Inject
    public CreateThreadNavigator(CreateThreadActivity activity) {
        this.activity = activity;
    }

    @Override
    protected Activity getActivity() {
        return activity;
    }

    public void navigateToInputInfoPage(Category category) {
        activity.getSupportFragmentManager().beginTransaction()
                .replace(activity.getFragmentContainerId(), InputThreadInfoFragment.newInstance(category))
                .addToBackStack(null)
                .commit();
    }

    public void finish() {
        activity.finish();
    }
}
