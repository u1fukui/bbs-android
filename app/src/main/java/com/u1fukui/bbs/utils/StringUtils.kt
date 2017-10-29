package com.u1fukui.bbs.utils


object StringUtils {

    fun isBlank(text: String?): Boolean {
        return text?.trim { it <= ' ' }?.isEmpty() ?: true
    }

    fun isLength(text: String?): Int {
        return text?.length ?: 0
    }
}
