package com.u1fukui.bbs.model

import java.io.Serializable

data class Category(
        val id: Int,
        val name: String
) : Serializable {

    companion object {

        private const val serialVersionUID = 4312405835923148571L
    }
}