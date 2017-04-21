package com.u1fukui.bbs.view.fragment;

import com.u1fukui.bbs.repository.HistoryThreadListRepository;
import com.u1fukui.bbs.repository.ThreadListRepository;

public class HistoryThreadListFragment extends BaseThreadListFragment {

    public static final HistoryThreadListFragment newInstance() {
        return new HistoryThreadListFragment();
    }

    public HistoryThreadListFragment() {
    }

    @Override
    ThreadListRepository getRepository() {
        return new HistoryThreadListRepository();
    }
}
