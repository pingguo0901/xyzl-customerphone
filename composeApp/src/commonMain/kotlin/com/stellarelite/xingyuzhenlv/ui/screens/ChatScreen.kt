package com.stellarelite.xingyuzhenlv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stellarelite.xingyuzhenlv.i18n.t

data class ChatContact(
    val id: String,
    val name: String,
    val role: String,
    val lastMessage: String,
    val time: String,
    val unread: Int = 0,
    val avatar: String = "",
    val isPinned: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(onNotification: () -> Unit = {}) {
    var searchQuery by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    val contacts = remember {
        listOf(
            ChatContact("cs1", "星域臻旅 客服", "专属管家", "您好！有什么可以帮您的？", "10:30", 2, isPinned = true),
            ChatContact("d1", "陈师傅", "司机", "已到达上车地点", "昨天", 0, isPinned = false),
            ChatContact("d2", "李师傅", "司机", "好的，明天见", "昨天", 1, isPinned = false),
            ChatContact("cj1", "林管家", "行程管家", "您的行程已确认", "周一", 0, isPinned = false),
            ChatContact("cj2", "黄管家", "行程管家", "新加坡入境卡需要提前填写", "上周五", 0, isPinned = false),
        )
    }

    val filtered = if (searchQuery.isBlank()) contacts
        else contacts.filter { it.name.contains(searchQuery, ignoreCase = true) || it.lastMessage.contains(searchQuery, ignoreCase = true) }

    Column(modifier = Modifier.fillMaxSize().padding(top = 8.dp)) {
        // 顶部标题栏 + 铃铛
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(t("tab_chat"), fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            IconButton(onClick = onNotification) {
                Icon(Icons.Filled.Notifications, "通知", tint = MaterialTheme.colorScheme.primary)
            }
        }

        Spacer(Modifier.height(8.dp))

        // 搜索输入框
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("搜索聊天对象...", fontSize = 14.sp) },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).height(56.dp),
            shape = RoundedCornerShape(24.dp),
            singleLine = true,
            leadingIcon = { Icon(Icons.Filled.Search, null, modifier = Modifier.size(20.dp)) },
            trailingIcon = {
                if (searchQuery.isNotBlank()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Filled.Close, null, modifier = Modifier.size(18.dp))
                    }
                }
            }
        )

        Spacer(Modifier.height(8.dp))

        // 聊天对象列表
        Column(modifier = Modifier.verticalScroll(scrollState)) {
            filtered.forEach { contact ->
                ChatContactRow(contact)
                HorizontalDivider(modifier = Modifier.padding(start = 76.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
            }
        }
    }
}

@Composable
private fun ChatContactRow(contact: ChatContact) {
    val bgColor = if (contact.isPinned) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                  else MaterialTheme.colorScheme.background

    Row(
        modifier = Modifier.fillMaxWidth().background(bgColor).clickable { }.padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 头像
        Box(
            modifier = Modifier.size(52.dp).clip(CircleShape)
                .background(if (contact.isPinned) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Text(
                contact.name.take(1),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = if (contact.isPinned) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(contact.name, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                if (contact.isPinned) {
                    Spacer(Modifier.width(6.dp))
                    Icon(Icons.Filled.PushPin, null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.primary)
                }
            }
            Spacer(Modifier.height(2.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    contact.role,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(horizontal = 6.dp, vertical = 1.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    contact.lastMessage,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    maxLines = 1
                )
            }
        }

        // 时间和未读数
        Column(horizontalAlignment = Alignment.End) {
            Text(contact.time, fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
            if (contact.unread > 0) {
                Spacer(Modifier.height(4.dp))
                Box(
                    modifier = Modifier.size(20.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text("${contact.unread}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
