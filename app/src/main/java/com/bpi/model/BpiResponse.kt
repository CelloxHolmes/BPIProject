package com.bpi.model

data class BpiResponse(
    val time: Time,
    val disclaimer: String,
    val chartName: String,
    val bpi: Bpi
)



