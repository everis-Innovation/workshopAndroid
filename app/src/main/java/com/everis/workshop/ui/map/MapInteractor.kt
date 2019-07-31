package com.everis.workshop.ui.map

import android.app.Activity
import com.everis.workshop.R
import com.everis.workshop.ui.base.BaseContracts
import com.everis.workshop.ui.base.BaseInteractor
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.*


import java.util.*

class MapInteractor(output: BaseContracts.InteractorOutput?) : BaseInteractor(output) {

    private lateinit var placesClient: PlacesClient
    private lateinit var places: FindAutocompletePredictionsResponse
    private lateinit var activity: Activity
    var token: AutocompleteSessionToken = AutocompleteSessionToken.newInstance()

    fun init(activity: Activity) {
        this.activity = activity
        // Initialize Places.
        Places.initialize(activity, activity.getString(R.string.placeKey))
        // Create a new Places client instance.
        placesClient = Places.createClient(activity)
    }

    fun findAutocompletePredictions(
        query: String,
        success: (FindAutocompletePredictionsResponse) -> Unit,
        error: (Exception) -> Unit
    ) {

        var requestBuilder: FindAutocompletePredictionsRequest =
            FindAutocompletePredictionsRequest.builder()
                .setCountry(activity.getString(R.string.autocomplete_predictions_country))
                .setTypeFilter(TypeFilter.ESTABLISHMENT)
                .setSessionToken(token)
                .setQuery(query)
                .build()

        placesClient.findAutocompletePredictions(requestBuilder)
            .addOnSuccessListener { response ->
                places = response
                success(response)
            }
            .addOnFailureListener { exception -> error(exception) }
    }

    fun fetchPlace(
        placeId: String,
        success: (FetchPlaceResponse) -> Unit,
        error: (Exception) -> Unit,
        complete: () -> Unit
    ) {

        if (places.autocompletePredictions.isEmpty()) {
            return
        }
        var placeFields: List<Place.Field> = Arrays.asList(
            Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG,
            Place.Field.ADDRESS
        )
        var request: FetchPlaceRequest = FetchPlaceRequest.builder(placeId, placeFields).build()

        placesClient.fetchPlace(request)
            .addOnSuccessListener { response ->
                success(response)
            }
            .addOnFailureListener { exception ->
                error(exception)
            }
            .addOnCompleteListener { complete() }
    }



}