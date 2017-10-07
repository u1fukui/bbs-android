package com.u1fukui.bbs.ui.main.mypage;

import com.u1fukui.bbs.repository.FavoriteThreadListRepository;
import com.u1fukui.bbs.repository.ThreadListRepository;
import com.u1fukui.bbs.ui.main.BaseThreadListFragment;

public class FavoriteThreadListFragment extends BaseThreadListFragment {

    public static final FavoriteThreadListFragment newInstance() {
        return new FavoriteThreadListFragment();
    }

    public FavoriteThreadListFragment() {
    }

    @Override
    protected ThreadListRepository getRepository() {
        return new FavoriteThreadListRepository();
    }

}
