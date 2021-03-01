package com.daniluk.metropolitanmuseum.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




data class ListDepartments(
    @SerializedName("departments")
    @Expose
    val departments: List<Department>? = null
)
