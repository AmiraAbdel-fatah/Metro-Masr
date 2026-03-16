package com.amira.metro_masr.domain.useCase

import com.amira.metro_masr.domain.MetroRepository
import com.amira.metro_masr.domain.model.RouteResult
import domain.useCase.CalcFareUseCase
import domain.useCase.CalcTimeUseCase

class GetRoutUseCase(
    val repo: MetroRepository,
    val calcFareUseCase: CalcFareUseCase,
    val calcTimeUseCase: CalcTimeUseCase,
    val bfsUseCase: BFSUseCase
) {

    operator fun invoke(startName: String, endName: String): RouteResult {
        val stations = repo.getStations()

        // Search in both English and Arabic names to find the station
        val start = stations.find {
            it.nameEn.equals(startName.trim(), true) || it.nameAr.equals(startName.trim(), true)
        } ?: return RouteResult.Error("Start station not found")

        val end = stations.find {
            it.nameEn.equals(endName.trim(), true) || it.nameAr.equals(endName.trim(), true)
        } ?: return RouteResult.Error("End station not found")

        val path = bfsUseCase(start, end, stations)
            ?: return RouteResult.Error("Path not found")

        val fare = calcFareUseCase(path.size - 1)
        val time = calcTimeUseCase(path.size - 1)

        return RouteResult.Success(
            stations = path,
            fare = fare,
            time = time
        )
    }
}
