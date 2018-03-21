package com.u1fukui.bbs.model

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ThreadListResponse(
        override val status: Int,
        val threadList: List<BbsThread>,
        val isCompleted: Boolean
) : ApiResponse
