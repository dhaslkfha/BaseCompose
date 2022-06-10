package com.compose.baseapp.bean

//“code” : int类型; 0->成功; 其他->失败
//“key” : String 类型;
//“msg” : String 类型;
//“data” : Object类型; -> 当前业务数据结构;
data class CommonResponse<T>(
    var code: Int,
    var key: String? = "",
    var msg: String? = "",
    var data: T? = null,
)
