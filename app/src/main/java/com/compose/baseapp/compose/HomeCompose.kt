@file:OptIn(ExperimentalPagerApi::class)

package com.compose.baseapp.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.compose.baseapp.tool.MainViewModel
import com.compose.baseapp.tool.MyRouter
import com.compose.baseapp.tool.routeTo
import com.compose.baseapp.tool.routeToDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.compose.baseapp.R

@Preview
@Composable
fun splashCompose(viewModel: MainViewModel? = null) {
    LaunchedEffect(key1 = null, block = {
        viewModel?.jumpToMain()
    })
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize(1f)
            .background(Color.Gray)
    ) {
        var (icon, txt) = createRefs()
        Image(
            painter = painterResource(id = R.mipmap.ic_nia_notification),
            modifier = Modifier
                .size(50.dp)
                .constrainAs(icon) {
                    bottom.linkTo(parent.bottom, 30.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            contentDescription = ""
        )
    }
}

@Preview
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeCompose(viewModel: MainViewModel? = null) {
    val listItem = listOf("首页", "发现", "我的")
    val icons = listOf(
        R.mipmap.ic_nia_notification,
        R.mipmap.ic_nia_notification,
        R.mipmap.ic_nia_notification
    )
    // tab选中图标
    var selectIndex by remember {
        mutableStateOf(viewModel?.selectIndex ?: 0)
    }
    val pagerState = rememberPagerState(
        pageCount = listItem.size,
        initialPage = selectIndex,
        initialOffscreenLimit = listItem.size
    )
    MyTheme() {
        Column(modifier = Modifier) {
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                state = pagerState, dragEnabled = true
            ) { page ->
                selectIndex = page
                when (page) {
                    0 -> firstPage()
                    1 -> secondPage()
                    2 -> thirdPage()
                }
            }
            BottomNavigation(
                backgroundColor = Color.Gray,
                contentColor = Color.Red,
                elevation = 3.dp
            ) {
                listItem.forEachIndexed { index, s ->
                    BottomNavigationItem(
                        selectedContentColor = Color.Green,
                        unselectedContentColor = Color.Blue,
                        modifier = Modifier,
                        selected = pagerState.currentPage == index,
                        onClick = {
                            CoroutineScope(Dispatchers.Main).launch {
                                pagerState.scrollToPage(index)
                                selectIndex = index
                                viewModel?.selectIndex = index
                            }
                        }, icon = {
                            Image(
                                modifier = Modifier.size(30.dp),
                                painter = painterResource(id = icons[index]),
                                contentDescription = "${listItem[index]}"
                            )
                        }, label = {
                            val textColor = if (pagerState.currentPage == index) {
                                Color.Red
                            } else {
                                Color.Black
                            }
                            Text(
                                modifier = Modifier,
                                text = listItem[index],
                                style = TextStyle(color = textColor)
                            )
                        }

                    )
                }
            }
        }
    }
}

@Composable
fun firstPage() {
    Column(
        modifier = Modifier
            .fillMaxSize(1f)
            .verticalScroll(rememberScrollState())
    ) {
        repeat(100) {
            Text(text = "Hello,world!1", color = Color.Blue)
            Text(text = "Hello,Old friend!")
        }
    }
}

@Composable
fun secondPage() {
    Column(modifier = Modifier.fillMaxSize(1f)) {
        repeat(100) {
            Text(text = "Hello,world!2", color = Color.Blue)
            Text(text = "Hello,Old friend!")
            Button(onClick = {
                routeToDetail("小周")
            }) {
                Text(text = "Button")
            }
        }
    }
}

@Composable
fun thirdPage() {
    Column(
        modifier = Modifier.fillMaxSize(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = rememberImagePainter(data = "https://bkimg.cdn.bcebos.com/pic/7af40ad162d9f2d3ea2b4b92a6ec8a136327cc91?x-bce-process=image/watermark,image_d2F0ZXIvYmFpa2UxNTA=,g_7,xp_5,yp_5/format,f_auto") {
                transformations(CircleCropTransformation())
            },
            modifier = Modifier.size(dpHeadSize),
            contentDescription = "",
        )
        Text(text = "Hello,world!3", color = Color.Blue)
        Text(text = "Hello,Old friend!")
        Button(onClick = {
            routeTo(MyRouter.Route_AccountPage)
        }) {
            Text(text = "去登录")
        }
        Button(onClick = {
            routeTo(MyRouter.Route_AccountPage)
        }) {
            Text(text = "去登录")
        }
    }
}