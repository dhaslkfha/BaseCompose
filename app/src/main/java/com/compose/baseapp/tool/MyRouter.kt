package com.compose.baseapp.tool

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.compose.baseapp.MyApplication
import com.compose.baseapp.compose.*

object MyRouter {
    const val Route_SplashPage = "splash"
    const val Route_HomePage = "home"
    const val Route_DetailPage = "detail"
    const val Route_AccountPage = "account"
    const val Route_LoginPage = "login"
    const val Route_RegisterPage = "register"
    const val Route_RegisterSuccessPage = "register_success"
}

@Composable
fun MyNavigationEntry(
    viewModel: MainViewModel,
    content: @Composable () -> Unit
) {
    MyApplication.navigationController = rememberNavController()
    Scaffold() { paddingValues ->
        NavHost(
            navController = MyApplication.navigationController!!,
            startDestination = MyRouter.Route_SplashPage,
            modifier = Modifier.padding(paddingValues)
        ) {
            //启动
            composable(route = MyRouter.Route_SplashPage, content = {
                content()
            })
            //首页
            composable(route = MyRouter.Route_HomePage, content = {
                HomeCompose(viewModel)
            })
            //详情
            composable(
                route = "${MyRouter.Route_DetailPage}/{name}",
                arguments = listOf(navArgument("name") {
                    type = NavType.StringType
                }),
                deepLinks = listOf(navDeepLink {
                    uriPattern = "money://${MyRouter.Route_DetailPage}/{name}"
                }),
                content = {
                    Column() {
                        comHeader()
                        Text(text = "Detail Page,我拿到了你的数据：${it.arguments?.get("name")}")
                    }
                })
            //嵌套导航结构
            navigation(
                startDestination = MyRouter.Route_LoginPage,
                route = MyRouter.Route_AccountPage
            ) {

                composable(route = MyRouter.Route_LoginPage) {
                    loginCompose()
                }
                composable(route = MyRouter.Route_RegisterPage) {
                    registerPage()
                }
                composable(route = MyRouter.Route_RegisterSuccessPage) {
                    registerSuccessPage()
                }
            }
        }
    }

}


//参数传递
fun routeToDetail(name: String) {
    routeTo("${MyRouter.Route_DetailPage}/$name")
}

//跳转
fun routeTo(path: String, option: (NavOptionsBuilder.() -> Unit)? = null) {
    MyApplication.navigationController?.navigate(path) {
        option
    }
}

//关闭之前的页面
fun routeToFinish(oldPath: String, newPath: String) {
    MyApplication.navigationController?.navigate(
        newPath
    ) {
        popUpTo(oldPath) {
            inclusive = true
        }
    }
}

//跳回
fun routePopTo(path: String) {
    MyApplication.navigationController?.navigate(
        path
    ) {
        popUpTo(path)
        launchSingleTop = true
    }
}