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

public class MyPageFragment extends Fragment {

    public static final String TAG = MyPageFragment.class.getSimpleName();

    private FragmentTabBinding binding;

    public static MyPageFragment newInstance() {
        return new MyPageFragment();
    }

    public MyPageFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTabBinding.inflate(inflater, container, false);
        initViews();

        return binding.getRoot();
    }

    private void initViews() {
        binding.tabLayout.setTabMode(TabLayout.MODE_FIXED);
        binding.viewPager.setAdapter(new MyPagePagerAdapter(getChildFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    @Override
    public void onDestroyView() {
        binding.unbind();
        super.onDestroyView();
    }

    private static class MyPagePagerAdapter extends FragmentPagerAdapter {

        enum Tab {
            FAVORITE {
                @Override
                Fragment createInsatance() {
                    return FavoriteThreadListFragment.newInstance();
                }

                @Override
                String getTitle() {
                    //TODO: Apply resource string
                    return "Favorite";
                }
            },
            HISTORY {
                @Override
                Fragment createInsatance() {
                    return HistoryThreadListFragment.newInstance();
                }

                @Override
                String getTitle() {
                    //TODO: Apply resource string
                    return "History";
                }
            };

            abstract Fragment createInsatance();

            abstract String getTitle();
        }

        public MyPagePagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            Tab tab = Tab.values()[position];
            return tab.createInsatance();
        }

        @Override
        public int getCount() {
            return Tab.values().length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Tab tab = Tab.values()[position];
            return tab.getTitle();
        }
    }
}
