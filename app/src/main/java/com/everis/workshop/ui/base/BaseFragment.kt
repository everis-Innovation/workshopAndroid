package com.everis.workshop.ui.base
import android.content.Context
import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment(), BaseContracts.View {

    fun getBaseActivity(): BaseActivity = (activity as BaseActivity)

    fun getPresenter(): BaseContracts.Presenter {
        return getBaseActivity().presenter
    }

    override fun getActivityContext(): Context? {
        return getBaseActivity()
    }

    override fun showLoadingDialog() {
        getBaseActivity().showLoadingDialog()
    }

    override fun closeLoadingDialog() {
        getBaseActivity().closeLoadingDialog()
    }

    override fun showErrorScreen(message: String) {
        getBaseActivity().showErrorScreen(message)
    }

    override fun closeErroScreen() {
        getBaseActivity().closeErroScreen()
    }
}