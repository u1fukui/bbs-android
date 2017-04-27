package com.u1fukui.bbs.view.helper;


import android.support.annotation.IdRes;

import com.u1fukui.bbs.model.Category;
import com.u1fukui.bbs.view.activity.CreateThreadActivity;
import com.u1fukui.bbs.view.fragment.InputThreadInfoFragment;

public class CreateThreadNavigator {

    private final CreateThreadActivity activity;

    @IdRes
    private final int fragmentContainerId;

    public CreateThreadNavigator(CreateThreadActivity activity, @IdRes int fragmentContainerId) {
        this.activity = activity;
        this.fragmentContainerId = fragmentContainerId;
    }

    public void navigateToInputInfoPage(Category category) {
        activity.getSupportFragmentManager().beginTransaction()
                .replace(fragmentContainerId, InputThreadInfoFragment.newInstance(category))
                .addToBackStack(null)
                .commit();
    }

    public void finish() {
        activity.finish();
    }
}
