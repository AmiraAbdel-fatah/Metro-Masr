package com.amira.metro_masr.domain.useCase

import com.amira.metro_masr.domain.MetroRepository
import com.amira.metro_masr.domain.model.Station


class GetAllStationsUseCase(
    val repository: MetroRepository
) {

    operator fun invoke(): List<Station> {
        return repository.getStations()
    }
}