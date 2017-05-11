package com.u1fukui.bbs.view.fragment;

import android.content.Context;
import android.os.Bundle;

import com.u1fukui.bbs.model.Category;
import com.u1fukui.bbs.repository.CategoryThreadListRepository;
import com.u1fukui.bbs.repository.ThreadListRepository;
import com.u1fukui.bbs.view.activity.MainActivity;
import com.u1fukui.bbs.view.helper.MainNavigator;
import com.u1fukui.bbs.view.helper.ThreadListNavigator;

public class CategoryThreadListFragment extends BaseThreadListFragment {

    public static final String TAG = CategoryThreadListFragment.class.getSimpleName();

    private static final String ARG_CATEGORY = "arg.categroy";

    private MainNavigator navigator;

    public static CategoryThreadListFragment newInstance(Category category) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CATEGORY, category);

        CategoryThreadListFragment instance = new CategoryThreadListFragment();
        instance.setArguments(args);
        return instance;
    }

    public CategoryThreadListFragment() {
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            navigator = ((MainActivity) getActivity()).getNavigator();
        }
    }

    @Override
    ThreadListRepository getRepository() {
        return new CategoryThreadListRepository();
    }

    @Override
    ThreadListNavigator getNavigator() {
        return navigator;
    }
}
