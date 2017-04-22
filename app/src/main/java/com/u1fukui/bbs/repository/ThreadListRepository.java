package com.u1fukui.bbs.repository;


import com.u1fukui.bbs.model.BbsThread;

import java.util.List;

import io.reactivex.Single;

public interface ThreadListRepository {

    Single<List<BbsThread>> fetchThreadList();
}
