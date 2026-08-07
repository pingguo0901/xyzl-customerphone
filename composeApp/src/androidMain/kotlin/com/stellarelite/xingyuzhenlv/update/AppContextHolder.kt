package com.stellarelite.xingyuzhenlv.update

import android.content.Context

object AppContextHolder {
    var context: Context? = null
        private set

    fun init(ctx: Context) {
        context = ctx.applicationContext
        
        // 保存 Activity 引用用于设置状态栏
        if (ctx is android.app.Activity) {
            _activity = ctx
        }
    }
    
    private var _activity: android.app.Activity? = null
    val activity: android.app.Activity? get() = _activity
}
