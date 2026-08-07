package com.stellarelite.xingyuzhenlv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stellarelite.xingyuzhenlv.i18n.Language
import com.stellarelite.xingyuzhenlv.i18n.LanguageManager
import com.stellarelite.xingyuzhenlv.i18n.t

@Composable
fun LanguageScreen(onBack: () -> Unit = {}) {
    val currentLang = LanguageManager.currentLang.value

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, "返回") }
            Spacer(Modifier.width(8.dp))
            Text("语言", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(20.dp))

        Language.entries.forEach { lang ->
            val selected = currentLang == lang
            val label = if (lang == Language.SYSTEM) t("language_system") else lang.displayName
            val flag = if (lang == Language.SYSTEM) "🌐" else lang.flag

            Row(
                modifier = Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .then(if (selected) Modifier.background(MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)) else Modifier)
                    .clickable { LanguageManager.setLang(lang) }
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(flag, fontSize = 22.sp)
                Spacer(Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(label, fontSize = 16.sp, fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal)
                }
                if (selected) {
                    Icon(Icons.Filled.Check, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                } else {
                    Icon(Icons.Filled.ChevronRight, null, tint = MaterialTheme.colorScheme.outline, modifier = Modifier.size(18.dp))
                }
            }

            if (lang != Language.entries.last()) {
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
            }
        }
    }
}
