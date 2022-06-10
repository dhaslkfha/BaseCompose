import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ImmersionBar
import com.compose.baseapp.MyApplication.Companion.curActivity
import com.compose.baseapp.MyApplication.Companion.currentViewModelScope
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<B : ViewBinding, M : ViewModel> : AppCompatActivity(),
    BaseInterface {

    lateinit var binding: B
    lateinit var viewModel: M

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        curActivity = this
        binding = bindings
        setContentView(binding.root)
        initViewModel()
        initView(savedInstanceState)
        //status bar color
        ImmersionBar.with(this).statusBarDarkFont(true).init()
        initListener()
        initData()
        observe()
    }

    override fun onResume() {
        super.onResume()
        curActivity = this
        try {
            currentViewModelScope = (curActivity as BaseActivity<*, *>).viewModel.viewModelScope
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    open fun initData() {}

    open fun observe() {}

    open fun initListener() {}

    private val bindings: B by lazy {
        val type = javaClass.genericSuperclass as ParameterizedType
        val aClass = type.actualTypeArguments[0] as Class<*>
        val method = aClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        method.invoke(null, layoutInflater) as B
    }

    private fun initViewModel() {
        var vmClass =
            (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<M>
        viewModel = ViewModelProvider(this).get(vmClass)
    }
}

interface BaseInterface {
    fun initView(savedInstanceState: Bundle?)
}