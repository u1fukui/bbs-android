package com.u1fukui.bbs.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.u1fukui.bbs.databinding.FragmentMypageBinding;

public class MyPageFragment extends Fragment {

    public static final String TAG = MyPageFragment.class.getSimpleName();

    private FragmentMypageBinding binding;

    public static MyPageFragment newInstance() {
        return new MyPageFragment();
    }

    public MyPageFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMypageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
