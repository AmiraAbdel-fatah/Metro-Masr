package com.amira.metro_masr.data.dataSource

import com.amira.metro_masr.data.model.StationDto

interface MetroDataSource {
    fun loudStation(): List<StationDto>
    fun getTravelTime(): Int
}
