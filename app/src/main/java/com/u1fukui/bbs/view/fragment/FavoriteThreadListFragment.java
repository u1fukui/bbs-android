package com.u1fukui.bbs.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.u1fukui.bbs.databinding.FragmentSampleBinding;

public class FavoriteThreadListFragment extends Fragment {

    private FragmentSampleBinding binding;

    public static final FavoriteThreadListFragment newInstance() {
        return new FavoriteThreadListFragment();
    }

    public FavoriteThreadListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSampleBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
