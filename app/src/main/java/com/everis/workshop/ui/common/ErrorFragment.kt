package com.everis.workshop.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.everis.workshop.ui.base.BaseFragment
import com.everis.workshop.R
import kotlinx.android.synthetic.main.fragment_error.*

class ErrorFragment : BaseFragment() {

    companion object {
        fun newInstance(): ErrorFragment {
            return ErrorFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_error, container, false)
    }

    override fun onStart() {
        super.onStart()
        buttonErrorOk.setOnClickListener {
            closeErroScreen()
        }
    }

    fun setMessage(message: String) {
     textviewError.text = message
    }


}