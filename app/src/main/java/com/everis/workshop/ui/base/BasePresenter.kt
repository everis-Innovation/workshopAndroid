package com.everis.workshop.ui.base

import android.app.Activity
import android.os.Bundle

abstract class BasePresenter(var view: BaseContracts.View?) : BaseContracts.Presenter,
    BaseContracts.InteractorOutput {

    lateinit var activity: Activity
    var interactor: BaseContracts.Interactor? =
        BaseInteractor(this)
    lateinit var router: BaseContracts.Router

    //region Lifecycle

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)

        activity = view?.getActivityContext() as? Activity ?: return
        router = BaseRouter(activity)
    }

    override fun onDestroy() {
        view = null
        interactor?.unregister()
        interactor = null
        router?.unregister()
    }
}
