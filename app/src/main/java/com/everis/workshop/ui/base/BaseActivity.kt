package com.everis.workshop.ui.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.everis.workshop.R
import com.everis.workshop.ui.common.ErrorFragment
import com.everis.workshop.ui.common.LoadingFragment
import kotlinx.android.synthetic.main.activity_common.*

abstract class BaseActivity: AppCompatActivity(), BaseContracts.View {

    val LOADING_FRAGMENT_ID = "loadingFragment"
    val ERROR_FRAGMENT_ID = "errorFragment"
    val errorFragment: ErrorFragment = ErrorFragment.newInstance()
    lateinit var presenter: BasePresenter

    override fun getActivityContext(): Context? {
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.loading_container, LoadingFragment.newInstance(), LOADING_FRAGMENT_ID)
            .commit()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.error_container, errorFragment, ERROR_FRAGMENT_ID)
            .commit()

    }

    override fun showLoadingDialog() {
        loading_container.visibility = View.VISIBLE
    }

    override fun closeLoadingDialog() {
        loading_container.visibility = View.GONE
    }

    override fun showErrorScreen(message: String) {
        errorFragment.setMessage(message)
        error_container.visibility = View.VISIBLE
    }

    override fun closeErroScreen() {
        errorFragment.setMessage(getString(R.string.generic_error))
        error_container.visibility = View.GONE
    }

    fun loadFragment(fragment: BaseFragment, idFragment: String) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, fragment, idFragment)
            .commit()

    }
}