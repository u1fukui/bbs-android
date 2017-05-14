package com.u1fukui.bbs.view.fragment;

import android.os.Bundle;

import com.u1fukui.bbs.model.Category;
import com.u1fukui.bbs.repository.CategoryThreadListRepository;
import com.u1fukui.bbs.repository.ThreadListRepository;

public class CategoryThreadListFragment extends BaseThreadListFragment {

    public static final String TAG = CategoryThreadListFragment.class.getSimpleName();

    private static final String ARG_CATEGORY = "arg.categroy";

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
    ThreadListRepository getRepository() {
        return new CategoryThreadListRepository();
    }
}
