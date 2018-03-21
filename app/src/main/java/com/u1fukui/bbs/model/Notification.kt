package com.u1fukui.bbs.model


import se.ansman.kotshi.JsonSerializable
import java.io.Serializable
import java.util.*

@JsonSerializable
data class Notification(
        val id: Long,
        val url: String,
        val message: String,
        val createdAt: Date
) : Serializable {

    companion object {

        private const val serialVersionUID = -6826333503595117337L
    }
}
