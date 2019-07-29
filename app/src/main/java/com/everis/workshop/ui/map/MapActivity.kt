package com.everis.workshop.ui.map

import android.os.Bundle
import com.everis.workshop.ui.base.BaseActivity
import android.view.View
import android.view.WindowManager
import com.everis.workshop.R

class MapActivity : BaseActivity() {

    val FRAGMENT_ID = "mapFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)
        presenter = MapPresenter(this)
        (presenter as MapPresenter).onCreate(savedInstanceState)

        if (savedInstanceState == null) {
           loadFragment(MapFragment.newInstance(),  FRAGMENT_ID)
        }
        hideStatusBar()
    }

    private fun hideStatusBar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}
