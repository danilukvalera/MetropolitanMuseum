package com.daniluk.metropolitanmuseum.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




data class Tag(
    @SerializedName("term")
    @Expose
    val term: String? = null,

    @SerializedName("AAT_URL")
    @Expose
    val aATURL: String? = null,

    @SerializedName("Wikidata_URL")
    @Expose
    val wikidataURL: String? = null
)