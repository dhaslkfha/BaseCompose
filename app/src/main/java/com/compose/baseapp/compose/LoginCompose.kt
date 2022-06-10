package com.compose.baseapp.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.compose.baseapp.tool.*
import com.compose.baseapp.tool.MyRouter.Route_AccountPage
import com.compose.baseapp.tool.MyRouter.Route_HomePage
import com.compose.baseapp.tool.MyRouter.Route_RegisterSuccessPage

@Composable
fun loginCompose() {
    Column() {
        Text(text = "登录")
        Text(text = "账号")
        Text(text = "密码")
        Button(onClick = {
            routeTo(MyRouter.Route_RegisterPage)
        }) {
            Text(text = "去注册")
        }
    }
}

@Composable
fun registerPage() {
    Row() {
        Text(text = "账号")
        Text(text = "密码")
        Button(onClick = {
            "注册成功".toast()
            routeToFinish(Route_AccountPage, Route_RegisterSuccessPage)
        }, modifier = Modifier) {
            Text(text = "注册")
        }
    }
}

@Composable
fun registerSuccessPage() {
    Row() {
        Button(onClick = {
            routePopTo(Route_HomePage)
        }, modifier = Modifier) {
            Text(text = "返回")
        }
    }
}