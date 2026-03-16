package com.amira.metro_masr.data.repo

import com.amira.metro_masr.data.dataSource.MetroDataSource
import com.amira.metro_masr.data.mapper.MetroMapper
import com.amira.metro_masr.domain.MetroRepository
import com.amira.metro_masr.domain.model.Station

class MetroRepositoryImpl(private val dataSource: MetroDataSource) : MetroRepository {
    override fun getStations(): List<Station> {
        return dataSource.loudStation().map { MetroMapper.toDomain(it) }
    }

    override fun getTravelTime(): Int = dataSource.getTravelTime()
}