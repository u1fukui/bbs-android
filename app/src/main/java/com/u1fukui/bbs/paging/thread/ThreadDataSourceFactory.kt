package com.u1fukui.bbs.paging.thread

import androidx.paging.DataSource
import com.u1fukui.bbs.model.BbsThread
import com.u1fukui.bbs.repository.thread_list.ThreadListRepository
import kotlinx.coroutines.CoroutineScope

class ThreadDataSourceFactory(
    repository: ThreadListRepository,
    coroutineScope: CoroutineScope
) : DataSource.Factory<Long, BbsThread>() {

    val source = PageKeyedThreadDataSource(repository, coroutineScope)

    override fun create(): DataSource<Long, BbsThread> = source
}