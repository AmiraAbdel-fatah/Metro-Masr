package com.amira.metro_masr.presentation.home

import com.amira.metro_masr.domain.model.RouteResult
import com.amira.metro_masr.domain.model.Station

data class HomeScreenState(
    val startStation: Station? = null,
    val endStation: Station? = null,
    val stations: List<Station> = emptyList(),
    val routeResult: RouteResult? = null,
    val isLoading: Boolean = false,
    val recentTrips: List<Pair<Station, Station>> = emptyList()
)
