package com.everis.workshop.data.network.entities

import com.everis.workshop.data.network.interfaces.ApiService

object WsRequestUserProvider {

    fun provideRequestUser(): WsRequestUser = WsRequestUser(ApiService.create())

}