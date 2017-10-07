package com.u1fukui.bbs.ui.main.mypage;

import com.u1fukui.bbs.repository.HistoryThreadListRepository;
import com.u1fukui.bbs.repository.ThreadListRepository;
import com.u1fukui.bbs.ui.main.BaseThreadListFragment;

public class HistoryThreadListFragment extends BaseThreadListFragment {

    public static final HistoryThreadListFragment newInstance() {
        return new HistoryThreadListFragment();
    }

    public HistoryThreadListFragment() {
    }

    @Override
    protected ThreadListRepository getRepository() {
        return new HistoryThreadListRepository();
    }
}
