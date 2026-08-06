package com.stellarelite.xingyuzhenlv

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.stellarelite.xingyuzhenlv.ui.navigation.MainScreen
import com.stellarelite.xingyuzhenlv.ui.theme.XingyuZhenLvTheme

@Composable
fun App() {
    XingyuZhenLvTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MainScreen()
        }
    }
}
