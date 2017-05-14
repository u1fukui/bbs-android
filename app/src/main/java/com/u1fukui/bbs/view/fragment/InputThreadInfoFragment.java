package com.u1fukui.bbs.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.u1fukui.bbs.databinding.FragmentInputThreadInfoBinding;
import com.u1fukui.bbs.model.Category;
import com.u1fukui.bbs.viewmodel.InputThreadInfoViewModel;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class InputThreadInfoFragment extends DaggerFragment {

    private static final String ARG_CATEGORY = "arg.category";

    @Inject
    InputThreadInfoViewModel viewModel;

    private FragmentInputThreadInfoBinding binding;

    public static InputThreadInfoFragment newInstance(Category category) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CATEGORY, category);

        InputThreadInfoFragment instance = new InputThreadInfoFragment();
        instance.setArguments(args);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentInputThreadInfoBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        initViews();

        return binding.getRoot();
    }

    private void initViews() {
        binding.editTextTitle.requestFocus();
    }

    public Category getCategory() {
        return (Category) getArguments().getSerializable(ARG_CATEGORY);
    }
}
