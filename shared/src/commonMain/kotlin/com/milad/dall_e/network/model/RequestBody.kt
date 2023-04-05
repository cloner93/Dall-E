package com.milad.dall_e.network.model

import kotlinx.serialization.Serializable

@Serializable
data class RequestBody(
    val n: Int,
    val prompt: String,
    val size: String
)