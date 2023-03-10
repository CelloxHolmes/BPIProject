package com.bpi.network

import com.bpi.model.BpiResponse
import retrofit2.Call
import retrofit2.Retrofit

class CoinDeskApiImpl(private val retrofit: Retrofit) : CoinDeskApi {

    override fun getBpi(): Call<BpiResponse> {
        return retrofit.create(CoinDeskApi::class.java).getBpi()
    }
}
