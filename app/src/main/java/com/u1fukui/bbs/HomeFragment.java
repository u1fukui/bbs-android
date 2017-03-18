package com.u1fukui.bbs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.u1fukui.bbs.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    public static final String TAG = HomeFragment.class.getSimpleName();

    private FragmentHomeBinding binding;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
