package com.everis.workshop.ui.base

open class BaseInteractor(var output: BaseContracts.InteractorOutput?) :
    BaseContracts.Interactor {

    override fun unregister() {
        output = null
    }
}
