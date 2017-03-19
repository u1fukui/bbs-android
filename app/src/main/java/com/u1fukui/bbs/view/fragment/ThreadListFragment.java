package com.u1fukui.bbs.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.u1fukui.bbs.databinding.FragmentThreadListBinding;
import com.u1fukui.bbs.model.Category;

public class ThreadListFragment extends Fragment {

    public static final String TAG = ThreadListFragment.class.getSimpleName();

    private static final String ARG_CATEGORY = "arg.categroy";

    private FragmentThreadListBinding binding;

    public static ThreadListFragment newInstance(Category category) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CATEGORY, category);

        ThreadListFragment instance = new ThreadListFragment();
        instance.setArguments(args);
        return instance;
    }

    public ThreadListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentThreadListBinding.inflate(inflater, container, false);

        Category category = (Category) getArguments().getSerializable(ARG_CATEGORY);
        binding.title.setText(category.getName());

        return binding.getRoot();
    }
}
