package com.u1fukui.bbs.paging.comment

import android.arch.paging.DataSource
import com.u1fukui.bbs.model.Comment
import com.u1fukui.bbs.repository.ThreadRepository
import kotlinx.coroutines.experimental.Job

class CommentDataSourceFactory(
    repository: ThreadRepository,
    threadId: Long,
    job: Job
) : DataSource.Factory<Long, Comment>() {

    val source = PageKeyedCommentDataSource(repository, threadId, job)

    override fun create(): DataSource<Long, Comment> = source
}