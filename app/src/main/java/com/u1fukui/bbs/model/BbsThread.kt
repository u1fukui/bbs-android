package com.u1fukui.bbs.model

import se.ansman.kotshi.JsonSerializable
import java.io.Serializable
import java.util.Date

@JsonSerializable
data class BbsThread(
    val id: Long,
    val title: String,
    val author: User,
    val commentCount: Int,
    val createdAt: Date,
    val updatedAt: Date
) : Serializable {

    companion object {

        private const val serialVersionUID = -4762213969235749270L
    }
}
