package com.compose.baseapp.tool

import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import com.compose.baseapp.MyApplication

fun String?.toast(context: Context? = MyApplication.INSTANT) {
    if (!this.isNullOrEmpty()) {
        var toast = Toast.makeText(context, this, Toast.LENGTH_SHORT)
        var textViewid = Resources.getSystem().getIdentifier("message", "id", "android")
        val textView: TextView? = toast.view?.findViewById<TextView>(textViewid)
        textView?.gravity = Gravity.CENTER
        toast.show()
    }
}