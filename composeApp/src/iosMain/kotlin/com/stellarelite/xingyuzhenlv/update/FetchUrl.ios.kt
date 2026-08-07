package com.stellarelite.xingyuzhenlv.update

actual suspend fun fetchUrl(url: String): String {
    return try {
        val nsUrl = platform.Foundation.NSURL(string = url)
        val data = platform.Foundation.NSData.dataWithContentsOfURL(nsUrl)
        data?.let { platform.Foundation.NSString.create(it, platform.Foundation.NSUTF8StringEncoding) as? String } ?: ""
    } catch (_: Exception) {
        ""
    }
}
