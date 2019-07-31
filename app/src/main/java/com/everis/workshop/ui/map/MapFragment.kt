package com.everis.workshop.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.everis.workshop.ui.base.BaseFragment
import com.everis.workshop.data.model.map.MapCoordinates
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import android.widget.EditText
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.everis.workshop.R
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.model.AutocompletePrediction

import kotlinx.android.synthetic.main.fragment_map.*


class MapFragment : BaseFragment(), OnMapReadyCallback {

    companion object {
        val COORDINATES_MAPS = "coordMaps"

        fun newInstance(): MapFragment {
            return MapFragment()
        }
    }
    private val MAP_ZOOM = 5f //from 2.0f until 21.0f
    private val MAP_ZOOM_SEARCH = 10f
    private val COUNT_TO_SEARCH = 4
    private lateinit var googleMap: GoogleMap
    private lateinit var coordinates: MapCoordinates

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coordinates = activity?.intent?.extras?.get(COORDINATES_MAPS) as MapCoordinates
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.mapView.getMapAsync(this)
        mapView.run { onCreate(savedInstanceState) }
        searchTextListener()
    }

    override fun onResume() {
        super.onResume()
        mapView.run { onResume() }
    }

    override fun onPause() {
        super.onPause()
        mapView.run { onPause() }
    }

    override fun onStart() {
        super.onStart()
        mapView.run { onStart() }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.run { onLowMemory() }
    }

    override fun onStop() {
        super.onStop()
        mapView.run { onStop() }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        var myLocation = LatLng(23.3252,25.12314)
        googleMap.addMarker(
            MarkerOptions()
                .position(myLocation)
                .title(getString(R.string.user_place))
                .snippet("2º linea de info")

        )
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, MAP_ZOOM))
    }

    /** Google Places */
    private fun addPlacesSuggest(place: String, placeId: String) {
        val suggestText = TextView(activity)
        val padding: Int = resources.getDimension(R.dimen.map_suggest_padding).toInt()
        suggestText.text = place
        suggestText.setPadding(padding, padding, padding, padding)
        if (placeId.isNotEmpty()) {
            suggestText.setOnClickListener {
                actionSuggest(placeId)
            }
        }
        llSuggest.addView(suggestText)
    }

    private fun actionSuggest(placeId: String) {
        dismissKeyboard(editTextPlace)
        showLoadingDialog()
        (getPresenter() as MapPresenter).fetchPlace(placeId,
            success = { response ->
                response.place.latLng?.let {coor ->
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coor, MAP_ZOOM_SEARCH))
                    googleMap.clear()
                    googleMap.addMarker(MarkerOptions()
                        .position(coor)
                        .title(response.place.name)
                        .snippet(response.place.address)
                    )
                }
            },
            error = { exception ->
                if (exception is ApiException) {
                    showErrorScreen(exception.message.toString())
                }
            },
            complete = {
                hidePlacesSuggest()
                closeLoadingDialog()
            })
    }

    private fun cleanPlacesSuggest() {
        llSuggest.removeAllViews()
    }

    private fun hidePlacesSuggest() {
        if (llSuggest.visibility == View.VISIBLE) {
            llSuggest.visibility = View.GONE
        }
    }

    private fun showPlacesSuggest() {
        if (llSuggest.visibility == View.GONE) {
            llSuggest.visibility = View.VISIBLE
        }
    }

    private fun dismissKeyboard(focusedEditText: EditText) {
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.hideSoftInputFromWindow(focusedEditText.windowToken, 0)
    }

    private fun searchTextListener() {
        editTextPlace.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                //Not Needed
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (count > COUNT_TO_SEARCH) {
                    (getPresenter() as MapPresenter).findAutocompletePredictions(editTextPlace.text.toString(),
                        success = { places ->
                            cleanPlacesSuggest()
                            for (place: AutocompletePrediction in places.autocompletePredictions) {
                                addPlacesSuggest(place.a(), place.placeId)
                            }
                            showPlacesSuggest()
                        },
                        error = { exception ->
                            exception.printStackTrace()
                            cleanPlacesSuggest()
                            addPlacesSuggest(exception.message.toString(), "")
                        })
                } else {
                    cleanPlacesSuggest()
                }
            }

            override fun afterTextChanged(s: Editable) {
                //Not Needed
            }
        })
    }
}
