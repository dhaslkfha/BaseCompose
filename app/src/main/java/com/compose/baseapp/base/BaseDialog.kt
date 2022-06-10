import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import androidx.viewbinding.ViewBinding
import com.compose.baseapp.R
import java.lang.reflect.ParameterizedType


abstract class BaseDialog<VB : ViewBinding>(
    context: Context,
    @StyleRes style: Int = R.style.DialogStyle
) :
    AlertDialog(context, style),
    BaseInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setGravity(Gravity.BOTTOM)
        setContentView(binding.root)
        setCanceledOnTouchOutside(true)
        initView(savedInstanceState)
    }

    fun middle() {
        window?.setGravity(Gravity.CENTER)
    }

    fun left() {
        window?.setGravity(Gravity.LEFT)
    }

    fun trans() {
        val layoutParams: WindowManager.LayoutParams? = window?.attributes
        layoutParams?.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        window?.attributes = layoutParams
    }

    fun fullWidth(left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0) {
        window?.decorView?.setPadding(left, top, right, bottom)
        val layoutParams: WindowManager.LayoutParams? = window?.attributes
        layoutParams?.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams?.horizontalMargin = 0f
        window?.attributes = layoutParams
    }

    fun wrapWidth(left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0) {
        window?.decorView?.setPadding(left, top, right, bottom)
        val layoutParams: WindowManager.LayoutParams? = window?.attributes
        layoutParams?.width = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams?.horizontalMargin = 0f
        window?.attributes = layoutParams
    }

    val binding: VB by lazy {
        val type = javaClass.genericSuperclass as ParameterizedType
        val clazz = type.actualTypeArguments[0] as Class<*>
        val method = clazz.getDeclaredMethod("inflate", LayoutInflater::class.java)
        method.invoke(null, layoutInflater) as VB
    }
}