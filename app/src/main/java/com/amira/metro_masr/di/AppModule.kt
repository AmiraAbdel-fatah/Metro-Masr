package com.amira.metro_masr.di

import android.content.Context
import com.amira.metro_masr.data.dataSource.MetroJsonDataSource
import com.amira.metro_masr.data.repo.MetroRepositoryImpl
import com.amira.metro_masr.domain.useCase.BFSUseCase
import com.amira.metro_masr.domain.useCase.GetAllStationsUseCase
import com.amira.metro_masr.domain.useCase.GetRoutUseCase
import domain.useCase.CalcFareUseCase
import domain.useCase.CalcTimeUseCase
import domain.useCase.GetDirectionUseCase
import domain.useCase.GetFirstStationUseCase
import domain.useCase.GetLastStationUseCase

object AppModule {

    fun provideFindRoute(context: Context): GetRoutUseCase {
        val dataSource = MetroJsonDataSource(context)
        val repository = MetroRepositoryImpl(dataSource)

        val fare = CalcFareUseCase()
        val time = CalcTimeUseCase(repository)
        val bfsUseCase = BFSUseCase()

        return GetRoutUseCase(
            repository,
            fare,
            time,
            bfsUseCase
        )
    }

    fun provideDirection(context: Context): GetDirectionUseCase {
        val dataSource = MetroJsonDataSource(context)
        val repository = MetroRepositoryImpl(dataSource)

        val getFirstStationUseCase = GetFirstStationUseCase(repository)
        val getLastStationUseCase = GetLastStationUseCase(repository)

        return GetDirectionUseCase(
            getFirstStationUseCase,
            getLastStationUseCase
        )
    }

    fun provideGetStations(context: Context): GetAllStationsUseCase {
        val dataSource = MetroJsonDataSource(context)
        val repository = MetroRepositoryImpl(dataSource)
        return GetAllStationsUseCase(repository)
    }
}
