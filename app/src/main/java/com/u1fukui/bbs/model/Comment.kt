package com.u1fukui.bbs.model

import se.ansman.kotshi.JsonSerializable
import java.io.Serializable
import java.util.*

@JsonSerializable
data class Comment(
        val id: Long,
        val threadId: Long,
        val displayNumber: Int,
        val description: String,
        val author: User,
        val likeCount: Int,
        val isLiked: Boolean,
        val createdAt: Date
) : Serializable {

    companion object {

        private const val serialVersionUID = -2300031079350686170L
    }
}
