package com.u1fukui.bbs.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.u1fukui.bbs.databinding.FragmentInputThreadInfoBinding;
import com.u1fukui.bbs.model.Category;
import com.u1fukui.bbs.repository.ThreadRepository;
import com.u1fukui.bbs.view.helper.CreateThreadNavigator;
import com.u1fukui.bbs.view.helper.DialogHelper;
import com.u1fukui.bbs.viewmodel.InputThreadInfoViewModel;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class InputThreadInfoFragment extends DaggerFragment {

    private static final String ARG_CATEGORY = "arg.category";

    @Inject
    ThreadRepository repository;

    @Inject
    CreateThreadNavigator navigator;

    private Category category;

    private FragmentInputThreadInfoBinding binding;

    private InputThreadInfoViewModel viewModel;

    public static InputThreadInfoFragment newInstance(Category category) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CATEGORY, category);

        InputThreadInfoFragment instance = new InputThreadInfoFragment();
        instance.setArguments(args);
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        category = (Category) getArguments().getSerializable(ARG_CATEGORY);

        DialogHelper dialogHelper = new DialogHelper(getContext());
        viewModel = new InputThreadInfoViewModel(category, repository, navigator, dialogHelper);
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
}
