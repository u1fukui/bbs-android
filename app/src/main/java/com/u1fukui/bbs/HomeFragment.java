package com.u1fukui.bbs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.u1fukui.bbs.databinding.FragmentHomeBinding;
import com.u1fukui.bbs.model.Category;

import java.util.ArrayList;
import java.util.List;

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
        initViews();

        return binding.getRoot();
    }

    private void initViews() {
        binding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        binding.viewPager.setAdapter(new HomePagerAdapter(getChildFragmentManager(), createCategoryList()));
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    private List<Category> createCategoryList() {
        List<Category> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new Category(i, "カテゴリ" + i));
        }
        return list;
    }

    private static class HomePagerAdapter extends FragmentPagerAdapter {

        private final List<Category> categoryList;

        public HomePagerAdapter(FragmentManager manager, List<Category> categoryList) {
            super(manager);
            this.categoryList = categoryList;
        }

        @Override
        public Fragment getItem(int position) {
            return ThreadListFragment.newInstance(categoryList.get(position));
        }

        @Override
        public int getCount() {
            return categoryList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return categoryList.get(position).getName();
        }
    }
}
