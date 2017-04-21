package com.u1fukui.bbs.view.fragment;

import com.u1fukui.bbs.repository.FavoriteThreadListRepository;
import com.u1fukui.bbs.repository.ThreadListRepository;

public class FavoriteThreadListFragment extends BaseThreadListFragment {

    public static final FavoriteThreadListFragment newInstance() {
        return new FavoriteThreadListFragment();
    }

    public FavoriteThreadListFragment() {
    }

    @Override
    ThreadListRepository getRepository() {
        return new FavoriteThreadListRepository();
    }
}
