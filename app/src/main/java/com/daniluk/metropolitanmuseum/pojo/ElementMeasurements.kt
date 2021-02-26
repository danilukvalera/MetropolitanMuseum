package com.daniluk.metropolitanmuseum.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ElementMeasurements(
    @SerializedName("Diameter")
    @Expose
    val diameter: Double? = null
)
