package com.stellarelite.xingyuzhenlv.network

import kotlin.random.Random

/**
 * 当前登录用户会话（内存态，重启后需重新登录）。
 */
object UserSession {
    var currentUser: UserProfile? = null
        private set

    fun setUser(profile: UserProfile?) {
        currentUser = profile
    }

    fun clear() {
        currentUser = null
    }

    /** 当前用户 id；未登录时返回 null */
    val userId: String?
        get() = currentUser?.user_id

    /** 未登录游客下单时生成临时 user_id（10 + 13位数字，与后端格式一致） */
    fun generateGuestId(): String =
        "10" + Random.nextLong(0, 9999999999999L).toString().padStart(13, '0')
}
