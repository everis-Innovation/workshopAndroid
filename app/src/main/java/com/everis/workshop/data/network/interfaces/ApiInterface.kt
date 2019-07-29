package com.everis.workshop.data.network.interfaces

import com.everis.workshop.data.network.model.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiInterface {

    @Headers("dataType: \"json\"")
    @GET("api/")
    fun requestUser(): Call<Result>

}