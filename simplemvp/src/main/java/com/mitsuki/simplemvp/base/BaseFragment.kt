package com.mitsuki.simplemvp.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mitsuki.utilspack.utils.AppManager
import com.mitsuki.utilspack.utils.toastShort
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.kcontext


abstract class BaseFragment<T : BasePresenter<*, *>> : Fragment(), IView, KodeinAware,
        IBaseFragment {
    val TAG = this.javaClass.simpleName

    abstract val mPresenter: T?
    abstract val kodeinModule: Kodein.Module

    private val parentKodein by closestKodein()
    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(kodeinModule)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = initView(inflater, container, savedInstanceState)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData(savedInstanceState)
    }


    override fun onDestroy() {
        mPresenter?.onDestroy()
        super.onDestroy()
    }

    /*****************************************************************************************************************/
    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showMessage(message: String) {
        context?.let { it.toastShort { message } }
    }


    override fun launchActivity(intent: Intent) = AppManager.startActivity(intent)

    override fun killMyself() {
        activity?.finish()
    }
}
