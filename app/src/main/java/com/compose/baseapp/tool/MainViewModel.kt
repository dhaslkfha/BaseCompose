package com.compose.baseapp.tool

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.baseapp.MyApplication
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var selectIndex = 0;//首页选中的item
    suspend fun jumpToMain() {
        delay(500)
        viewModelScope.launch {
            SPUtils.setParam(MyApplication.INSTANT, "firstIn", false)
            routeToFinish(MyRouter.Route_SplashPage, MyRouter.Route_HomePage)
        }
    }
}