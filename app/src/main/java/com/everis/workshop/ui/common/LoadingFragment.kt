package com.everis.workshop.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.everis.workshop.ui.base.BaseFragment
import com.everis.workshop.R

class LoadingFragment : BaseFragment() {

    companion object {
        fun newInstance(): LoadingFragment {
            return LoadingFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_loading, container, false)
    }
}