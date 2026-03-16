package com.amira.metro_masr.domain.model

data class Station(
    val id: Int,
    val nameEn: String, // Renamed from name
    val nameAr: String,
    val line: MetroLine,
    val order: Int,
    val isTransfer: Boolean,
    val transferLines: List<MetroLine>
) {
    // get the name based on the current language
    fun getName(isArabic: Boolean): String = if (isArabic) nameAr else nameEn
}
