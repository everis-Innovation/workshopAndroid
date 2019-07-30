package com.everis.workshop.ui.main

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.everis.workshop.data.network.model.Result
import com.everis.workshop.ui.base.BaseFragment
import com.everis.workshop.R
import com.everis.workshop.data.model.map.MapCoordinates
import com.everis.workshop.data.network.model.User
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment(),  GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    private lateinit var googleApiClient: GoogleApiClient
    var location: Location = Location("0,0")

    val SEPARATOR = ":"
    private lateinit var resutlInfo: Result

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.let {  googleApiClient = GoogleApiClient.Builder(activity!!)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()

            (getPresenter() as MainPresenter).checkLocation(googleApiClient, this) }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getInfoView()
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onStart() {
        super.onStart()
        setViewActions()
        googleApiClient.connect()

    }

    override fun onStop() {
        super.onStop();
        if (googleApiClient.isConnected) {
            googleApiClient.disconnect()
        }
    }

    private fun setViewActions() {
        buttonMap.setOnClickListener {
            val user: User = resutlInfo.results[0]
            goMap(lat = user.location.coordinates.latitude.toDouble(),
                long = user.location.coordinates.longitude.toDouble())
        }

        buttonMapMyPosition.setOnClickListener {
            goMap(location.latitude, location.longitude)
        }
    }

    private fun goMap(lat: Double, long: Double){
        var mapCoord = MapCoordinates()
        mapCoord.latitude = lat
        mapCoord.longitude = long
        (getPresenter() as MainPresenter).buttonAction(mapCoord as Object)
    }

    private fun getInfoView() {
        showLoadingDialog()
        (getPresenter() as MainPresenter).getUserData(
            onSuccess = { response: Result ->
                closeLoadingDialog()
                resutlInfo = response
                renderInfo()
                addSeparators()
            },
            onError = { exception ->
                closeLoadingDialog()
                showErrorScreen(exception!!.message!!)
            })
    }

    private fun renderInfo(){
        val user: User = resutlInfo.results[0]
        tvGenderData.text = user.gender
        tvNameData.text = user.name.toString()
        tvLocationAdressData.text = user.location.getAdress()
        tvLocationCoorData.text = user.location.coordinates.toString()
        tvLocationTimeZoneData.text = user.location.timezone.toString()
    }

    private fun addSeparators() {
        tvGender.text = tvGender.text.toString() + SEPARATOR
        tvName.text = tvName.text.toString() + SEPARATOR
        tvEmail.text = tvEmail.text.toString() + SEPARATOR
        tvLocationAdress.text = tvLocationAdress.text.toString() + SEPARATOR
        tvLocationCoor.text = tvLocationCoor.text.toString() + SEPARATOR
        tvLocationTimeZone.text = tvLocationTimeZone.text.toString() + SEPARATOR
    }

    override fun onConnected(bundle: Bundle?) {
        showLoadingDialog()
        (getPresenter() as MainPresenter).initCurrentPosition(
            location = {location: Location ->
            this.location = location
            closeLoadingDialog()
        })
    }

    override fun onConnectionSuspended(p0: Int) {
        googleApiClient.connect()
    }

    override fun onConnectionFailed(conectionResult: ConnectionResult) {
        closeLoadingDialog()
        //TODO do something when fail the connection
    }

    override fun onLocationChanged(location: Location?) {
        //TODO action to when de location changed

    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter().onDestroy()
    }
}