package com.stellarelite.xingyuzhenlv.permission

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import com.stellarelite.xingyuzhenlv.update.AppContextHolder

fun PermissionType.toAndroidPermissions(): Array<String> = when (this) {
    PermissionType.NOTIFICATION -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        arrayOf(Manifest.permission.POST_NOTIFICATIONS) else arrayOf()
    PermissionType.MICROPHONE -> arrayOf(Manifest.permission.RECORD_AUDIO)
    PermissionType.CAMERA -> arrayOf(Manifest.permission.CAMERA)
    PermissionType.PHOTO_GALLERY -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        arrayOf(Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO)
    else arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    PermissionType.LOCATION -> arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    PermissionType.CONTACTS -> arrayOf(Manifest.permission.READ_CONTACTS)
}

@Composable
actual fun rememberPermissionRequester(
    onAllGranted: () -> Unit,
    onPartialGranted: (List<PermissionType>) -> Unit
): () -> Unit {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { results ->
        val denied = mutableListOf<PermissionType>()
        for (type in PermissionType.entries) {
            val perms = type.toAndroidPermissions()
            if (perms.isEmpty()) continue
            val allOk = perms.all { results[it] == true }
            if (!allOk) denied.add(type)
        }
        if (denied.isEmpty()) onAllGranted()
        else onPartialGranted(denied)
    }

    return {
        val context = AppContextHolder.context
        if (context != null) {
            val needed = PermissionType.entries.flatMap { type ->
                type.toAndroidPermissions().filter { perm ->
                    ContextCompat.checkSelfPermission(context, perm) != PackageManager.PERMISSION_GRANTED
                }
            }.toTypedArray()

            if (needed.isEmpty()) {
                onAllGranted()
            } else {
                launcher.launch(needed)
            }
        }
    }
}
