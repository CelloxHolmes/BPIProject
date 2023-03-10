package com.bpi.network

import com.bpi.model.BpiResponse
import retrofit2.Call
import retrofit2.http.GET

interface CoinDeskApi {
    @GET("v1/bpi/currentprice.json")
    fun getBpi(): Call<BpiResponse>
}