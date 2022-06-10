import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<B : ViewBinding, V : ViewModel> : Fragment(), BaseInterface {

    lateinit var binding: B
    lateinit var viewModel: V
    var isNavigationViewInit = false
    lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val type: ParameterizedType = javaClass.genericSuperclass as ParameterizedType
        try {
            val inflate: Method = (type.actualTypeArguments[0] as Class<*>).getDeclaredMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.javaPrimitiveType
            )
            binding = inflate.invoke(null, inflater, container, false) as B
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
        if (!isNavigationViewInit) {
            initViewModel()
            rootView = binding.root
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }
        if (!isNavigationViewInit) {
            initView(savedInstanceState)
            isNavigationViewInit = true
        }
        initListener()
        initData()
        observe()
    }

    private fun initViewModel() {
        var vmClass =
            (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<V>
        viewModel = ViewModelProvider(this).get(vmClass)
    }

    open fun initData() {}

    open fun observe() {}

    open fun initListener() {}
}