package com.amira.metro_masr.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.amira.metro_masr.domain.MetroRepository
import com.amira.metro_masr.domain.model.RouteResult
import com.amira.metro_masr.domain.model.Station
import com.amira.metro_masr.domain.useCase.GetRoutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val repository: MetroRepository,
    private val getRouteUseCase: GetRoutUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(HomeScreenState())
    val screenState = _screenState.asStateFlow()

    init {
        loadStations()
    }

    private fun loadStations() {
        viewModelScope.launch {
            try {
                val stations = repository.getStations()
                _screenState.update { it.copy(stations = stations) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onStartStationChange(stationName: String) {
        val station = _screenState.value.stations.find {
            it.nameEn == stationName || it.nameAr == stationName
        }
        _screenState.update { it.copy(startStation = station) }
    }

    fun onEndStationChange(stationName: String) {
        val station = _screenState.value.stations.find {
            it.nameEn == stationName || it.nameAr == stationName
        }
        _screenState.update { it.copy(endStation = station) }
    }

    fun findRoute() {
        val state = _screenState.value
        if (state.startStation != null && state.endStation != null) {
            val result = getRouteUseCase(state.startStation.nameEn, state.endStation.nameEn)

            if (result is RouteResult.Success) {
                _screenState.update { currentState ->
                    val newTrip = state.startStation to state.endStation
                    val currentRecent = currentState.recentTrips.toMutableList()

                    // Remove if already exists to move it to the front
                    currentRecent.remove(newTrip)
                    currentRecent.add(0, newTrip)

                    // Limit to last 5 trips
                    val updatedRecent = currentRecent.take(5)
                    currentState.copy(routeResult = result, recentTrips = updatedRecent)
                }
            } else {
                _screenState.update { it.copy(routeResult = result) }
            }
        }
    }

    fun onRecentTripClick(start: Station, end: Station) {
        _screenState.update { it.copy(startStation = start, endStation = end) }
        findRoute()
    }
}

class HomeScreenViewModelFactory(
    private val repository: MetroRepository,
    private val getRouteUseCase: GetRoutUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeScreenViewModel(repository, getRouteUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
