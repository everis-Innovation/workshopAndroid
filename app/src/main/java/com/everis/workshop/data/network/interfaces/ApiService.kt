package com.everis.workshop.data.network.interfaces

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface ApiService {

    companion object Factory {
        private val URL_BASE = "https://randomuser.me/"

        fun create(): ApiInterface {
            val retrofit = Retrofit.Builder().apply {
                baseUrl(URL_BASE)
                addConverterFactory(GsonConverterFactory.create())
            }
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }
}

