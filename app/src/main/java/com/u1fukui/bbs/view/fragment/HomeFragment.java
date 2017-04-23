package com.u1fukui.bbs.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.u1fukui.bbs.databinding.FragmentTabBinding;
import com.u1fukui.bbs.model.Category;
import com.u1fukui.bbs.repository.CategoryListRepository;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends Fragment {

    public static final String TAG = HomeFragment.class.getSimpleName();

    private FragmentTabBinding binding;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTabBinding.inflate(inflater, container, false);
        fetchCategoryList();

        return binding.getRoot();
    }

    private void fetchCategoryList() {
        CategoryListRepository repository = new CategoryListRepository();
        repository.fetchCategoryList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Category>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        // nop
                    }

                    @Override
                    public void onSuccess(@NonNull List<Category> categoryList) {
                        initViews(categoryList);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        //TODO: 実装
                    }
                });
    }

    private void initViews(List<Category> categoryList) {
        binding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        binding.viewPager.setAdapter(new HomePagerAdapter(getChildFragmentManager(), categoryList));
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    @Override
    public void onDestroyView() {
        binding.unbind();
        super.onDestroyView();
    }

    private static class HomePagerAdapter extends FragmentPagerAdapter {

        private final List<Category> categoryList;

        public HomePagerAdapter(FragmentManager manager, List<Category> categoryList) {
            super(manager);
            this.categoryList = categoryList;
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
            return categoryList.get(position).name;
        }
    }
}
