package com.everis.workshop.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.everis.workshop.data.model.map.MapCoordinates
import com.everis.workshop.ui.base.BaseRouter
import com.everis.workshop.ui.map.MapActivity
import com.everis.workshop.ui.map.MapFragment

class MainRouter(activity: Activity?) : BaseRouter(activity) {

    fun goMapView(coor: MapCoordinates) {
        val intent = Intent(activity, MapActivity::class.java)
        var bundle = Bundle()
        bundle.putParcelable(MapFragment.COORDINATES_MAPS, coor)
        intent.putExtras(bundle)
        activity?.startActivity(intent)
    }
}