package com.amira.metro_masr.data.model

import com.google.gson.annotations.SerializedName

data class MetroDto(
    val stations: List<StationDto>,
    @SerializedName("travel_time_between_stations_minutes")
    val travel_time_between_stations_minutes: Int
)
