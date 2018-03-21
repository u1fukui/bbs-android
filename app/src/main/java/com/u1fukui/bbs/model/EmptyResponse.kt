package com.u1fukui.bbs.model

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class EmptyResponse(override val status: Int) : ApiResponse