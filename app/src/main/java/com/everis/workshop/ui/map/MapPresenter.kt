package com.everis.workshop.ui.map

import android.os.Bundle
import com.everis.workshop.ui.base.BaseContracts
import com.everis.workshop.ui.base.BasePresenter
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse


class MapPresenter(view: BaseContracts.View?) : BasePresenter(view) {


    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        router = MapRouter(activity)
        interactor = MapInteractor(this)
        (interactor as MapInteractor).init(activity)
    }

    fun findAutocompletePredictions(query: String,
                                    success: (FindAutocompletePredictionsResponse) -> Unit,
                                    error: (Exception) -> Unit) {
        (interactor as MapInteractor).findAutocompletePredictions(query, success, error)
    }

    fun fetchPlace(placeId: String,
                   success: (FetchPlaceResponse) -> Unit,
                   error: (Exception) -> Unit,
                   complete: () -> Unit) {
        (interactor as MapInteractor).fetchPlace(placeId, success, error, complete)
    }
}