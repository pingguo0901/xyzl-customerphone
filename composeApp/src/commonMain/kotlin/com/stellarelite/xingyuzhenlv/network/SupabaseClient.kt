package com.stellarelite.xingyuzhenlv.network

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

// ===================== 数据模型 =====================

@Serializable
data class UserProfile(
    val username: String = "",
    val email: String = "",
    val whatsapp: String = "",
    val wechat: String = "",
    val referred_by: String? = null,
    val hobbies: String? = null,
    val user_id: String? = null,
    val referral_code: String? = null,
    val avatar_url: String? = null,
    val wallet_balance: String? = null
)

@Serializable
data class OrderTrip(
    val user_id: String = "",
    val whatsapp: String = "",
    val wechat: String = "",
    val adult: Int = 0,
    val child: Int = 0,
    val luggage: Int = 0,
    val trips_date: String = "",
    val vehicle_count: Int = 1,
    val vehicle_type: String = "",
    val departure_state: String = "",
    val departure_address: String = "",
    val destination_state: String = "",
    val destination_address: String? = null,
    val notes: String? = null,
    val departure_address_2: String? = null,
    val departure_address_3: String? = null,
    val departure_address_4: String? = null,
    val departure_address_5: String? = null,
    val departure_address_6: String? = null,
    val departure_address_7: String? = null,
    val departure_address_8: String? = null,
    val departure_address_9: String? = null,
    val departure_address_10: String? = null,
    val destination_address_2: String? = null,
    val destination_address_3: String? = null,
    val destination_address_4: String? = null,
    val destination_address_5: String? = null,
    val destination_address_6: String? = null,
    val destination_address_7: String? = null,
    val destination_address_8: String? = null,
    val destination_address_9: String? = null,
    val destination_address_10: String? = null
)

@Serializable
data class OrderDailyTrip(
    val user_id: String? = null,
    val whatsapp: String? = null,
    val wechat: String? = null,
    val adult: Int = 0,
    val child: Int = 0,
    val luggage: Int = 0,
    val trip_start_date: String = "",
    val trip_end_date: String = "",
    val vehicle_count: Int = 1,
    val vehicle_type: String = "",
    val departure_state: String? = null,
    val departure_address: String? = null,
    val destination_state: String? = null,
    val destination_address: String? = null,
    val notes: String? = null,
    val departure_address_2: String? = null,
    val departure_address_3: String? = null,
    val departure_address_4: String? = null,
    val departure_address_5: String? = null,
    val departure_address_6: String? = null,
    val departure_address_7: String? = null,
    val departure_address_8: String? = null,
    val departure_address_9: String? = null,
    val departure_address_10: String? = null,
    val destination_address_2: String? = null,
    val destination_address_3: String? = null,
    val destination_address_4: String? = null,
    val destination_address_5: String? = null,
    val destination_address_6: String? = null,
    val destination_address_7: String? = null,
    val destination_address_8: String? = null,
    val destination_address_9: String? = null,
    val destination_address_10: String? = null
)

// ===================== 客户端 =====================

object SupabaseClient {
    private val json = Json { ignoreUnknownKeys = true }
    private const val BASE = SupabaseConfig.BASE_URL

    private fun headers() = mapOf(
        "apikey" to SupabaseConfig.ANON_KEY,
        "Authorization" to "Bearer ${SupabaseConfig.ANON_KEY}",
        "Content-Type" to "application/json"
    )

    private fun insertHeaders() = headers() + ("Prefer" to "return=representation")

    // 注册用户 -> user_profile，返回带自动生成 user_id / referral_code 的记录
    suspend fun registerUser(profile: UserProfile): UserProfile? {
        val resp = httpRequest("$BASE/rest/v1/user_profile", "POST", insertHeaders(), json.encodeToString(UserProfile.serializer(), profile))
        return if (resp.status in 200..299) {
            runCatching { json.decodeFromString<List<UserProfile>>(resp.body).firstOrNull() }.getOrNull()
        } else null
    }

    // 读取用户资料
    suspend fun getUserProfile(userId: String): UserProfile? {
        val resp = httpRequest("$BASE/rest/v1/user_profile?user_id=eq.$userId", "GET", headers())
        return if (resp.status in 200..299) {
            runCatching { json.decodeFromString<List<UserProfile>>(resp.body).firstOrNull() }.getOrNull()
        } else null
    }

    // 通过 email 查找用户（登录用）
    suspend fun getUserProfileByEmail(email: String): UserProfile? {
        val resp = httpRequest("$BASE/rest/v1/user_profile?email=eq.$email", "GET", headers())
        return if (resp.status in 200..299) {
            runCatching { json.decodeFromString<List<UserProfile>>(resp.body).firstOrNull() }.getOrNull()
        } else null
    }

    // 单程接送下单 -> order_trips
    suspend fun createOrderTrip(order: OrderTrip): OrderTrip? {
        val resp = httpRequest("$BASE/rest/v1/order_trips", "POST", insertHeaders(), json.encodeToString(OrderTrip.serializer(), order))
        return if (resp.status in 200..299) {
            runCatching { json.decodeFromString<List<OrderTrip>>(resp.body).firstOrNull() }.getOrNull()
        } else null
    }

    // 多日包车下单 -> order_daily_trips
    suspend fun createOrderDailyTrip(order: OrderDailyTrip): OrderDailyTrip? {
        val resp = httpRequest("$BASE/rest/v1/order_daily_trips", "POST", insertHeaders(), json.encodeToString(OrderDailyTrip.serializer(), order))
        return if (resp.status in 200..299) {
            runCatching { json.decodeFromString<List<OrderDailyTrip>>(resp.body).firstOrNull() }.getOrNull()
        } else null
    }
}
