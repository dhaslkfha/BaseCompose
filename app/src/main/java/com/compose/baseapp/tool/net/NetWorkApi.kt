import com.compose.baseapp.bean.CommonResponse
import okhttp3.RequestBody
import retrofit2.http.*

interface NetWorkApi {
    /**
     * 获取用户信息
     */
    @GET("/user/getUserInfo")
    suspend fun getUserInfo(@Query("userId") userId: String): CommonResponse<Boolean>


    /**
     */
    @POST("/hit/ascribeRecordReqs")
    suspend fun ascribeRecordReqs(@Body requestBody: RequestBody): CommonResponse<Boolean>

}