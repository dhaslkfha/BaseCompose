package com.compose.baseapp.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter
import com.compose.baseapp.R
import com.compose.baseapp.tool.toast

@Composable
fun comHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(dpAppBarHeight)
            .background(colorWhite)
            .padding(horizontal = dpComHorPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "标题",
            style = textStyleHeader,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun comLine() {
    Spacer(
        modifier = Modifier
            .height(dpLine)
            .fillMaxWidth(1f)
            .background(colorBlue)
    )
}

@Composable
fun comPicker(onClick: (((String) -> Unit) -> Unit)? = null) {
    var text by remember {
        mutableStateOf("内容")
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(dpAppBarHeight)
            .background(colorWhite)
            .padding(horizontal = dpComHorPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "标题：", style = textStyleItem, modifier = Modifier.weight(1f))
        Text(text = text, style = textStyleItem)
        Image(
            painter = rememberImagePainter(data = R.mipmap.ic_launcher),
            contentDescription = "",
            modifier = Modifier
                .size(dpComHorPadding)
                .clickable {
                    onClick?.let {
                        it {
                            text = it
                        }
                    }
                }
        )
    }
}

@Composable
fun inputItem() {
    var text by remember {
        mutableStateOf("")
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .background(colorWhite),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(text = "标题：", style = textStyleItem)
        TextField(
            value = text, onValueChange = {
                text = it
            }, colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorWhite,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
fun comButton() {
    Button(
        shape = RoundedCornerShape(dpCornerSize),
        modifier = Modifier.background(colorWhite),
        onClick = {
            "---".toast()
        }) {
        Text(text = "按钮", modifier = Modifier.fillMaxWidth(1f), textAlign = TextAlign.Center)
    }
}


@Preview(name = "list of preview")
@Composable
fun CommonPreview() {
    Column(Modifier.fillMaxWidth(1f)) {
        comHeader()
        comLine()
        inputItem()
        comLine()
        comButton()
        comLine()
        comPicker()
    }
}
