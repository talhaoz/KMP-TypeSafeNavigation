package com.talhaoz.kmptypesafenavigationexample

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform