import com.compose.baseapp.bean.CommonResponse
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * http request helper
 */
suspend fun <T> fetchRequest(
    showLoading: Boolean = false,
    request: suspend ApiClient.() -> CommonResponse<T>
): CommonResponse<T> {
    if (showLoading) {
        withContext(Dispatchers.Main) {
        }
    }
    return try {
        request(ApiClient)
    } catch (e: Throwable) {
        val apiException = getApiException(e)
        CommonResponse(code = 1, msg = apiException.errorMessage)
    } finally {//处理某些特殊情况
        if (showLoading) {
            withContext(Dispatchers.Main) {
            }
        }
    }
}

/**
 * 请求成功的处理
 */
fun <T> CommonResponse<T>.onSuccess(block: (T?) -> Unit): CommonResponse<T> {
    if (this.code == 0) {//TODO 后面统一处理
        block(this.data)
    }
    return this
}

/**
 * 请求失败或异常的处理
 */
fun <T> CommonResponse<T>.onFailure(block: (CommonResponse<T>) -> Unit): CommonResponse<T> {
    if (this.code != 0) {
        this.msg = when (this.code) {
            10010301 -> "未提供token"
            10010302 -> "token格式错误"
            100103 -> "无效的token"
            10010303 -> "token已过期"
            10010304 -> "其他设备登录"
            else -> this.msg
        }
        block(this)
    }
    return this
}

private fun getApiException(e: Throwable): ApiException {
    return when (e) {
        is UnknownHostException -> {
            ApiException("Network error~", -100)
        }
        is JSONException -> {//|| e is JsonParseException
            ApiException("data exception", -100)
        }
        is SocketTimeoutException -> {
            ApiException("time out", -100)
        }
        is ConnectException -> {
            ApiException("connect error", -100)
        }
        is HttpException -> {
            ApiException("http code ${e.code()}", -100)
        }
        is ApiException -> {
            e
        }
        is CancellationException -> {
            ApiException("", -10)
        }
        else -> {
            ApiException("unknown error", -100)
        }
    }
}
