package com.compose.baseapp

import android.app.Activity
import androidx.multidex.MultiDexApplication
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope

class MyApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        INSTANT = this
    }

    companion object {
        var navigationController: NavHostController? = null
        lateinit var INSTANT: MyApplication
        lateinit var curActivity: Activity
        lateinit var currentViewModelScope: CoroutineScope
    }
}