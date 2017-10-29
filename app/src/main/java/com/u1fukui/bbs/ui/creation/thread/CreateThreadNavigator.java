package com.u1fukui.bbs.ui.creation.thread;


import com.u1fukui.bbs.model.Category;
import com.u1fukui.bbs.ui.Navigator;

import javax.inject.Inject;

public class CreateThreadNavigator extends Navigator {

    @Inject
    public CreateThreadNavigator(CreateThreadActivity activity) {
        super(activity);
    }

    public void navigateToInputInfoPage(Category category) {
        if (getActivity() instanceof CreateThreadActivity) {
            CreateThreadActivity activity = (CreateThreadActivity) getActivity();
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(activity.getFragmentContainerId(), InputThreadInfoFragment.newInstance(category))
                    .addToBackStack(null)
                    .commit();
        }
    }
}