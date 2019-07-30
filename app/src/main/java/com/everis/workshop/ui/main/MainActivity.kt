package com.everis.workshop.ui.main

import android.os.Bundle
import com.everis.workshop.R
import com.everis.workshop.ui.base.BaseActivity
import com.everis.workshop.utils.UtilsPush

class MainActivity : BaseActivity() {

    val FRAGMENT_ID = "mainFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)
        presenter = MainPresenter(this)
        (presenter as MainPresenter).onCreate(savedInstanceState)

        if (savedInstanceState == null) {
           loadFragment(MainFragment.newInstance(),  FRAGMENT_ID)
        }
        UtilsPush.firebasePushRegister(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        (presenter as MainPresenter).onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
