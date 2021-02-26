package com.daniluk.metropolitanmuseum.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




data class Objects(
    @SerializedName("total")
    @Expose
    val total: Int? = null,

    @SerializedName("objectIDs")
    @Expose
    val objectIDs: List<Int>? = null
)
