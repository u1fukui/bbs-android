package com.u1fukui.bbs.repository;


import com.u1fukui.bbs.model.BbsThread;

import java.util.List;

public interface ThreadListRepository {

    //TODO: return Single
    abstract List<BbsThread> fetchThreadList();
}
