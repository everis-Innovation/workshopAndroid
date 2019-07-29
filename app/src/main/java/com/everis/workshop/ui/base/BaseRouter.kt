package com.everis.workshop.ui.base

import android.app.Activity

open class BaseRouter(var activity: Activity?) : BaseContracts.Router {

    override fun unregister() {
        activity = null
    }
}
