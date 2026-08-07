package com.stellarelite.xingyuzhenlv.update

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual suspend fun fetchUrl(url: String): String {
    return NSURL(string = url)?.let {
        NSString.stringWithContentsOfURL(it, encoding = NSUTF8StringEncoding, error = null)
    } ?: throw Exception("Failed to fetch URL")
}

actual fun downloadApk(url: String) {
    NSURL(string = url)?.let {
        UIApplication.sharedApplication.openURL(it)
    }
}
