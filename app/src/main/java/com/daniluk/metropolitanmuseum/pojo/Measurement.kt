package com.daniluk.metropolitanmuseum.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




data class Measurement(
    @SerializedName("elementName")
    @Expose
    val elementName: String? = null,

    @SerializedName("elementDescription")
    @Expose
    val elementDescription: String? = null,

    @SerializedName("elementMeasurements")
    @Expose
    val elementMeasurements: ElementMeasurements? = null
)
