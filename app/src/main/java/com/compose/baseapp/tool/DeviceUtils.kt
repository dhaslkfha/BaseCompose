package com.compose.baseapp.tool

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import androidx.core.content.ContextCompat
import com.compose.baseapp.MyApplication
import java.util.*


/**
 * 获取应用程序的IMEI、DeviceId、UUID
 */
fun getDeviceId(): String {
    var imei = ""
    if (ContextCompat.checkSelfPermission(
            MyApplication.INSTANT,
            Manifest.permission.READ_PHONE_STATE
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        try {
            imei = if (Build.VERSION.SDK_INT >= 29) {  //安卓10系统获取AndroidID
                Settings.System.getString(
                    MyApplication.INSTANT.contentResolver,
                    Settings.Secure.ANDROID_ID
                )
            } else {
                val tm =
                    MyApplication.INSTANT.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                val method = tm.javaClass.getMethod("getImei")
                method.invoke(tm) as String
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return imei
}

fun getClientSessionId(): String {
    return UUID.randomUUID().toString()
}

fun getVersionName(): String {
    return MyApplication.INSTANT.packageManager.getPackageInfo(
        MyApplication.INSTANT.packageName,
        0
    ).versionName
}