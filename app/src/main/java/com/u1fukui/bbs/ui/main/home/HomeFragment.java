package com.u1fukui.bbs.ui.main.home;

import android.databinding.ObservableList;
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

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class HomeFragment extends DaggerFragment {

    public static final String TAG = HomeFragment.class.getSimpleName();

    @Inject
    HomeViewModel viewModel;

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
        binding.setViewModel(viewModel);
        initViews();

        viewModel.start();

        return binding.getRoot();
    }

    private void initViews() {
        binding.viewPager.setAdapter(new HomePagerAdapter(getChildFragmentManager(), viewModel.getCategoryList()));
        binding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    @Override
    public void onDestroyView() {
        binding.unbind();
        super.onDestroyView();
    }

    private static class HomePagerAdapter extends FragmentPagerAdapter {

        private final ObservableList<Category> categoryList;

        public HomePagerAdapter(FragmentManager manager, ObservableList<Category> categoryList) {
            super(manager);
            this.categoryList = categoryList;
            this.categoryList.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Category>>() {
                @Override
                public void onChanged(ObservableList<Category> sender) {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeChanged(ObservableList<Category> sender, int positionStart, int itemCount) {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeInserted(ObservableList<Category> sender, int positionStart, int itemCount) {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeMoved(ObservableList<Category> sender, int fromPosition, int toPosition, int itemCount) {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeRemoved(ObservableList<Category> sender, int positionStart, int itemCount) {
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public Fragment getItem(int position) {
            return CategoryThreadListFragment.newInstance(categoryList.get(position));
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
