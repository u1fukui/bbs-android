package com.u1fukui.bbs.paging.comment

import androidx.paging.DataSource
import com.u1fukui.bbs.model.Comment
import com.u1fukui.bbs.repository.ThreadRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

class CommentDataSourceFactory(
    repository: ThreadRepository,
    threadId: Long,
    coroutineScope: CoroutineScope
) : DataSource.Factory<Long, Comment>() {

    val source = PageKeyedCommentDataSource(repository, threadId, coroutineScope)

    override fun create(): DataSource<Long, Comment> = source
}