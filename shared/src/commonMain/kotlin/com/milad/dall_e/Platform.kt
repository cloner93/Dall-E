package com.milad.dall_e

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform