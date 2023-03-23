package com.milad.dall_e.network.model

data class RequestBody(
    val n: Int,
    val prompt: String,
    val size: String
)