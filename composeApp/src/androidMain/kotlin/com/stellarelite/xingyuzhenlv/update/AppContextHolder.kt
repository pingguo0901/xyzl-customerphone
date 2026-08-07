package com.stellarelite.xingyuzhenlv.update

import android.content.Context

object AppContextHolder {
    var context: Context? = null

    fun init(context: Context) {
        this.context = context.applicationContext
    }
}
