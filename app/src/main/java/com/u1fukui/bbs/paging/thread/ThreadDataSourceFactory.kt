package com.u1fukui.bbs.paging.thread

import android.arch.paging.DataSource
import com.u1fukui.bbs.model.BbsThread
import com.u1fukui.bbs.repository.thread_list.ThreadListRepository
import kotlinx.coroutines.experimental.Job

class ThreadDataSourceFactory(
    repository: ThreadListRepository,
    job: Job
) : DataSource.Factory<Long, BbsThread>() {

    val source = PageKeyedThreadDataSource(repository, job)

    override fun create(): DataSource<Long, BbsThread> = source
}