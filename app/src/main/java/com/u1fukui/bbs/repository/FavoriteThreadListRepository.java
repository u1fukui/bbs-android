package com.u1fukui.bbs.repository;


import com.u1fukui.bbs.model.BbsThread;
import com.u1fukui.bbs.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FavoriteThreadListRepository implements ThreadListRepository {

    @Override
    public List<BbsThread> fetchThreadList() {
        List<BbsThread> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            User author = new User(i, "作者" + i);
            list.add(new BbsThread(i, "お気に入りスレッド" + i, author, i, new Date(), new Date()));
        }
        return list;
    }
}
