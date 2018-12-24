package com.u1fukui.bbs.network

class ApiErrorMesageResolver {

    companion object {

        fun getMessage(t: Throwable) = t.localizedMessage
    }
}