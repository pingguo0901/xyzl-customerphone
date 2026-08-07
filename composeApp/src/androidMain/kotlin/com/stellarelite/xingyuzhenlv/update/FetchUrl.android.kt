package com.stellarelite.xingyuzhenlv.update

import java.net.URL

actual suspend fun fetchUrl(url: String): String {
    return URL(url).openStream().bufferedReader().use { it.readText() }
}
