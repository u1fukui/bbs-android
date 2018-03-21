package com.u1fukui.bbs.model

import se.ansman.kotshi.JsonSerializable
import java.io.Serializable

@JsonSerializable
data class Category(
        val id: Int,
        val name: String
) : Serializable {

    companion object {

        private const val serialVersionUID = 4312405835923148571L
    }
}