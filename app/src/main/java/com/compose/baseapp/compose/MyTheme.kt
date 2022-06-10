package com.compose.baseapp.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme() {
        content()
    }

}

//dp
val dpAppBarHeight = 50.dp
val dpComHorPadding = 20.dp
val dpHeadSize = 60.dp
val dpCornerSize = 5.dp
val dpLine = 1.dp


//color
val colorWhite = Color.White
val colorBlue = Color.Blue

//textStyle
val textStyleHeader = TextStyle(
    color = Color.Black,
    fontSize = 20.sp,
    fontWeight = FontWeight.Bold,
)
val textStyleItem = TextStyle(
    color = Color.Black,
    fontSize = 16.sp,
    fontWeight = FontWeight.Normal,
)