package com.u1fukui.bbs.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.u1fukui.bbs.databinding.FragmentInputThreadInfoBinding;
import com.u1fukui.bbs.model.Category;
import com.u1fukui.bbs.repository.ThreadRepository;
import com.u1fukui.bbs.view.activity.CreateThreadActivity;
import com.u1fukui.bbs.view.helper.CreateThreadNavigator;
import com.u1fukui.bbs.viewmodel.InputThreadInfoViewModel;

public class InputThreadInfoFragment extends Fragment {

    private static final String ARG_CATEGORY = "arg.category";

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

        if (getActivity() instanceof CreateThreadActivity) {
            Category category = (Category) getArguments().getSerializable(ARG_CATEGORY);
            ThreadRepository repository = new ThreadRepository();
            CreateThreadNavigator navigator = ((CreateThreadActivity) getActivity()).getNavigator();
            viewModel = new InputThreadInfoViewModel(category, repository, navigator);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentInputThreadInfoBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);

        return binding.getRoot();
    }
}
