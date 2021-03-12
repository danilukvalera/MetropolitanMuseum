package com.daniluk.metropolitanmuseum.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




data class Showpiece(
    @SerializedName("objectID")
    @Expose
    val objectID: Int? = null,

    @SerializedName("isHighlight")
    @Expose
    val isHighlight: Boolean? = null,

    @SerializedName("accessionNumber")
    @Expose
    val accessionNumber: String? = null,

    @SerializedName("accessionYear")
    @Expose
    val accessionYear: String? = null,

    @SerializedName("isPublicDomain")
    @Expose
    val isPublicDomain: Boolean? = null,

    @SerializedName("primaryImage")
    @Expose
    val primaryImage: String? = null,

    @SerializedName("primaryImageSmall")
    @Expose
    val primaryImageSmall: String? = null,

    @SerializedName("additionalImages")
    @Expose
    val additionalImages: List<String?>? = null,

    @SerializedName("constituents")
    @Expose
    val constituents: Any? = null,

    @SerializedName("department")
    @Expose
    val department: String? = null,

    @SerializedName("objectName")
    @Expose
    val objectName: String? = null,

    @SerializedName("title")
    @Expose
    val title: String? = null,

    @SerializedName("culture")
    @Expose
    val culture: String? = null,

    @SerializedName("period")
    @Expose
    val period: String? = null,

    @SerializedName("dynasty")
    @Expose
    val dynasty: String? = null,

    @SerializedName("reign")
    @Expose
    val reign: String? = null,

    @SerializedName("portfolio")
    @Expose
    val portfolio: String? = null,

    @SerializedName("artistRole")
    @Expose
    val artistRole: String? = null,

    @SerializedName("artistPrefix")
    @Expose
    val artistPrefix: String? = null,

    @SerializedName("artistDisplayName")
    @Expose
    val artistDisplayName: String? = null,

    @SerializedName("artistDisplayBio")
    @Expose
    val artistDisplayBio: String? = null,

    @SerializedName("artistSuffix")
    @Expose
    val artistSuffix: String? = null,

    @SerializedName("artistAlphaSort")
    @Expose
    val artistAlphaSort: String? = null,

    @SerializedName("artistNationality")
    @Expose
    val artistNationality: String? = null,

    @SerializedName("artistBeginDate")
    @Expose
    val artistBeginDate: String? = null,

    @SerializedName("artistEndDate")
    @Expose
    val artistEndDate: String? = null,

    @SerializedName("artistGender")
    @Expose
    val artistGender: String? = null,

    @SerializedName("artistWikidata_URL")
    @Expose
    val artistWikidataURL: String? = null,

    @SerializedName("artistULAN_URL")
    @Expose
    val artistULANURL: String? = null,

    @SerializedName("objectDate")
    @Expose
    val objectDate: String? = null,

    @SerializedName("objectBeginDate")
    @Expose
    val objectBeginDate: Int? = null,

    @SerializedName("objectEndDate")
    @Expose
    val objectEndDate: Int? = null,

    @SerializedName("medium")
    @Expose
    val medium: String? = null,

    @SerializedName("dimensions")
    @Expose
    val dimensions: String? = null,

    @SerializedName("measurements")
    @Expose
    val measurements: List<Measurement>? = null,

    @SerializedName("creditLine")
    @Expose
    val creditLine: String? = null,

    @SerializedName("geographyType")
    @Expose
    val geographyType: String? = null,

    @SerializedName("city")
    @Expose
    val city: String? = null,

    @SerializedName("state")
    @Expose
    val state: String? = null,

    @SerializedName("county")
    @Expose
    val county: String? = null,

    @SerializedName("country")
    @Expose
    val country: String? = null,

    @SerializedName("region")
    @Expose
    val region: String? = null,

    @SerializedName("subregion")
    @Expose
    val subregion: String? = null,

    @SerializedName("locale")
    @Expose
    val locale: String? = null,

    @SerializedName("locus")
    @Expose
    val locus: String? = null,

    @SerializedName("excavation")
    @Expose
    val excavation: String? = null,

    @SerializedName("river")
    @Expose
    val river: String? = null,

    @SerializedName("classification")
    @Expose
    val classification: String? = null,

    @SerializedName("rightsAndReproduction")
    @Expose
    val rightsAndReproduction: String? = null,

    @SerializedName("linkResource")
    @Expose
    val linkResource: String? = null,

    @SerializedName("metadataDate")
    @Expose
    val metadataDate: String? = null,

    @SerializedName("repository")
    @Expose
    val repository: String? = null,

    @SerializedName("objectURL")
    @Expose
    val objectURL: String? = null,

    @SerializedName("tags")
    @Expose
    val tags: List<Tag>? = null,

    @SerializedName("objectWikidata_URL")
    @Expose
    val objectWikidataURL: String? = null,

    @SerializedName("isTimelineWork")
    @Expose
    val isTimelineWork: Boolean? = null,

    @SerializedName("GalleryNumber")
    @Expose
    val galleryNumber: String? = null
)
