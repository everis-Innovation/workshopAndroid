package com.everis.workshop.ui.base

import android.content.Context
import android.os.Bundle

interface BaseContracts {

    interface View {
        fun getActivityContext(): Context?
        fun showLoadingDialog()
        fun closeLoadingDialog()
        fun showErrorScreen(message: String)
        fun closeErroScreen()
    }

    interface Presenter {
        fun onCreate(bundle: Bundle? = null) {}
        fun onResume() {}
        fun onPause() {}
        fun onDestroy()
    }

    interface Interactor {
        fun unregister()
    }

    interface InteractorOutput //does nothing for now

    interface Router {
        fun unregister()
    }

}