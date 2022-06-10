import android.os.Build
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.compose.baseapp.BuildConfig
import com.compose.baseapp.tool.Mconstant.BASE_URL
import com.compose.baseapp.tool.Mconstant.isDebug
import com.compose.baseapp.tool.getDeviceId
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * network request
 */
object ApiClient {
    private const val TIME_OUT = 60L
    private val gson: Gson by lazy {
        GsonBuilder().setLenient().create()
    }
    private val client: OkHttpClient by lazy {
        OkHttpClient
            .Builder()
            .addNetworkInterceptor { chain ->
                chain.proceed(
                    chain
                        .request()
                        .newBuilder().apply {
                            header("ver", BuildConfig.VERSION_NAME)
                            header("device-id", getDeviceId()) //设备id or uuid
                            header("model", Build.MODEL)
                            header("lang", "chinese")
                            header("is_anchor", "false")
                            header("pkg", BuildConfig.APPLICATION_ID)//包名
                            header("platform", "Android") //Android or iOS
                        }.build()

                )
            }
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .apply {
                if (isDebug) {
                    this.addInterceptor(HttpLoggingInterceptor().apply {
                        this.level = HttpLoggingInterceptor.Level.BODY
                    })
                }
            }.build()

    }
    private val retrofit: Retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val apiService: NetWorkApi by lazy {
        retrofit.create(NetWorkApi::class.java)
    }
}