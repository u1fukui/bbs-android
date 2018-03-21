package com.u1fukui.bbs.model

import se.ansman.kotshi.JsonSerializable
import java.io.Serializable

@JsonSerializable
data class User(
        var id: Long?,
        var name: String?
) : Serializable {

    companion object {

        private const val serialVersionUID = 8679411212262196713L
    }
}
