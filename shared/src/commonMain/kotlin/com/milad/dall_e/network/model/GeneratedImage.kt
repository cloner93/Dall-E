package com.milad.dall_e.network.model

import kotlinx.serialization.Serializable

@Serializable
data class GeneratedImage(
    val created: Int,
    val `data`: List<Data>
)
