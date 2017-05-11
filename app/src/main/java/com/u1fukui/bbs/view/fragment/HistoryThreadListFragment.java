package com.u1fukui.bbs.view.fragment;

import android.content.Context;

import com.u1fukui.bbs.repository.HistoryThreadListRepository;
import com.u1fukui.bbs.repository.ThreadListRepository;
import com.u1fukui.bbs.view.activity.MainActivity;
import com.u1fukui.bbs.view.helper.MainNavigator;
import com.u1fukui.bbs.view.helper.ThreadListNavigator;

public class HistoryThreadListFragment extends BaseThreadListFragment {

    private MainNavigator navigator;

    public static final HistoryThreadListFragment newInstance() {
        return new HistoryThreadListFragment();
    }

    public HistoryThreadListFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            navigator = ((MainActivity) getActivity()).getNavigator();
        }
    }

    @Override
    ThreadListRepository getRepository() {
        return new HistoryThreadListRepository();
    }

    @Override
    ThreadListNavigator getNavigator() {
        return navigator;
    }
}
