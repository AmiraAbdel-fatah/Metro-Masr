package com.amira.metro_masr.data.dataSource

import android.content.Context
import android.util.Log
import com.amira.metro_masr.data.model.MetroDto
import com.amira.metro_masr.data.model.StationDto
import com.google.gson.Gson

class MetroJsonDataSource(private val context: Context) : MetroDataSource {

    private val gson = Gson()

    private val dto: MetroDto? by lazy {
        try {
            // Updated to match your actual file in assets
            val jsonString = context.assets.open("cairo_metro_structured.json")
                .bufferedReader()
                .use { it.readText() }

            val parsed = gson.fromJson(jsonString, MetroDto::class.java)
            Log.d("MetroDataSource", "Successfully loaded ${parsed?.stations?.size} stations")
            parsed
        } catch (e: Exception) {
            Log.e("MetroDataSource", "Error loading JSON: ${e.message}")
            e.printStackTrace()
            null
        }
    }

    override fun getTravelTime(): Int = dto?.travel_time_between_stations_minutes ?: 2

    override fun loudStation(): List<StationDto> {
        val stations = dto?.stations ?: emptyList()
        Log.d("MetroDataSource", "loudStation returning ${stations.size} stations")
        return stations
    }
}
