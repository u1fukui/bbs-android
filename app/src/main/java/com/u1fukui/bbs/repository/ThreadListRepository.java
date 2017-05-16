package com.u1fukui.bbs.repository;


import com.u1fukui.bbs.model.ThreadListResponse;

import io.reactivex.Single;

public interface ThreadListRepository {

    Single<ThreadListResponse> fetchThreadList(long lastId);
}
