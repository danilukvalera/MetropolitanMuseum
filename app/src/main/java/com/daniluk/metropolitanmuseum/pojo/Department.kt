package com.daniluk.metropolitanmuseum.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




data class Department(
    @SerializedName("departmentId")
    @Expose
    val departmentId: Int? = null,

    @SerializedName("displayName")
    @Expose
    val displayName: String? = null
)
