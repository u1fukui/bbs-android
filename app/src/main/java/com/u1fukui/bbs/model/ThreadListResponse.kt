package com.u1fukui.bbs.model

data class ThreadListResponse(
        override val status: Int,
        val threadList: List<BbsThread>,
        val isCompleted: Boolean
) : ApiResponse
