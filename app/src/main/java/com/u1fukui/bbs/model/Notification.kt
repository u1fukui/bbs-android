package com.u1fukui.bbs.model


import java.io.Serializable
import java.util.*

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
