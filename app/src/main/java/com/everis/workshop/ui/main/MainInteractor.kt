package com.everis.workshop.ui.main

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.everis.workshop.data.network.entities.WsRequestUserProvider
import com.everis.workshop.data.network.model.Result
import com.everis.workshop.ui.base.BaseContracts
import com.everis.workshop.ui.base.BaseInteractor
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainInteractor(output: BaseContracts.InteractorOutput?) : BaseInteractor(output) {

    private lateinit var activity: Activity
    lateinit var locationManager: LocationManager
    private var mLocationRequest: LocationRequest? = null
    private val UPDATE_INTERVAL = (2 * 1000).toLong()  /* 10 secs */
    private val FASTEST_INTERVAL: Long = 2000 /* 2 sec */
    private lateinit var googleApiClient: GoogleApiClient
    private var mLocationManager: LocationManager? = null
    private var locationListener: LocationListener? = null

    fun init(activity: Activity) {
        this.activity = activity
        mLocationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    fun getDataCallBack(onSuccess: (Result) -> Unit, onError: (Throwable?) -> Unit) {

        val wsRequestUser = WsRequestUserProvider.provideRequestUser()
        wsRequestUser.requestUser().enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                onSuccess(response.body()!!)
            }
            override fun onFailure(call: Call<Result>?, t: Throwable?) {
                onError(t)
            }
        })
    }

    fun initCurrentPosicion(locationView: (Location) -> Unit) {
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            ActivityCompat.requestPermissions(activity, permissions,0)
            return
        }

        initUpdateCurreintPosition()

        var fusedLocationProviderClient :
                FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);
        fusedLocationProviderClient .getLastLocation()
            .addOnSuccessListener(activity) { location ->
                if (location != null) {
                    locationView(location)
                }
            }
    }

    private fun initUpdateCurreintPosition() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(UPDATE_INTERVAL)
            .setFastestInterval(FASTEST_INTERVAL)
        // Request location updates
        if (ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,
            mLocationRequest, locationListener)
    }

    fun checkLocation(googleApiClient: GoogleApiClient, locationListener: LocationListener): Boolean {
        this.googleApiClient = googleApiClient
        this.locationListener = locationListener
        if(!isLocationEnabled()) {
            //TODO show message to inform user
        }
        return isLocationEnabled()
    }

    private fun isLocationEnabled(): Boolean {
        locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(
            LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
}