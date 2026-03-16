package com.amira.metro_masr.data.model

import com.google.gson.annotations.SerializedName

data class StationDto(
    val id: Int,
    @SerializedName("name")
    val nameEn: String,
    @SerializedName("name_ar")
    val nameAr: String? = null, // Make nullable to handle existing JSON
    val line: String,
    val order: Int,
    @SerializedName("is_transfer")
    val isTransfer: Boolean,
    @SerializedName("transfer_lines")
    val transferLines: List<String>
)
