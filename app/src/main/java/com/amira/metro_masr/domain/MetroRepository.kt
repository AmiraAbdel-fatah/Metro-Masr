package com.amira.metro_masr.domain

import com.amira.metro_masr.domain.model.Station


interface MetroRepository {
    fun getStations(): List<Station>
    fun getTravelTime(): Int
}
