package com.compose.baseapp

import BaseActivity
import android.os.Bundle
import com.gyf.immersionbar.ImmersionBar
import com.compose.baseapp.compose.splashCompose
import com.compose.baseapp.databinding.ActivityMainBinding
import com.compose.baseapp.tool.MainViewModel
import com.compose.baseapp.tool.MyNavigationEntry

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun initView(savedInstanceState: Bundle?) {
        ImmersionBar.with(this).titleBar(binding.empty).statusBarDarkFont(false)
            .keyboardEnable(false).init()

        binding.mainCompose.setContent {
            MyNavigationEntry(viewModel) {
                splashCompose(viewModel)
            }
        }
    }
}