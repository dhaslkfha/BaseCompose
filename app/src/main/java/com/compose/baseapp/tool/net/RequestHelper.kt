import android.util.Log
import com.alibaba.fastjson.JSON
import com.compose.baseapp.tool.Mconstant
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody


fun Map<String, Any>.body(): RequestBody {
    if (Mconstant.isDebug)
        Log.d("OkHttp--body----------", JSON.toJSONString(this))
    return JSON.toJSON(this).toString()
        .toRequestBody("application/json;charset=utf-8".toMediaType())
}
