package com.amira.metro_masr.domain.useCase

import com.amira.metro_masr.domain.model.MetroLine
import com.amira.metro_masr.domain.model.Station
import java.util.LinkedList
import java.util.Queue

class BFSUseCase {
    operator fun invoke(
        startStation: Station,
        endStation: Station,
        stations: List<Station>
    ): List<Station>? {

        if (startStation.nameEn == endStation.nameEn) return listOf(startStation)

        val queue: Queue<Station> = LinkedList()
        val visited = mutableSetOf<Station>()
        val parent = mutableMapOf<Station, Station?>()

        queue.add(startStation)
        visited.add(startStation)
        parent[startStation] = null

        val stationByLine = stations.groupBy { it.line }
        val stationsByName = stations.groupBy { it.nameEn }

        while (queue.isNotEmpty()) {
            val current = queue.poll() ?: continue

            if (current.nameEn == endStation.nameEn) {
                return buildPath(current, parent)
            }

            val neighbors = getNeighbors(current, stationByLine, stationsByName)
            for (neighbor in neighbors) {
                if (neighbor !in visited) {
                    visited.add(neighbor)
                    queue.add(neighbor)
                    parent[neighbor] = current
                }
            }
        }

        return null
    }

    private fun getNeighbors(
        station: Station,
        stationByLine: Map<MetroLine, List<Station>>,
        stationByName: Map<String, List<Station>>
    ): List<Station> {
        val sameLine = stationByLine[station.line]
            ?.filter { it.order == station.order + 1 || it.order == station.order - 1 }
            ?: emptyList()

        val transfers = if (station.isTransfer) {
            stationByName[station.nameEn]
                ?.filter { it.line != station.line } ?: emptyList()
        } else {
            emptyList()
        }

        return sameLine + transfers
    }

    private fun buildPath(
        endStation: Station,
        parent: Map<Station, Station?>
    ): List<Station> {
        val path = mutableListOf<Station>()
        var current: Station? = endStation

        while (current != null) {
            path.add(current)
            current = parent[current]
        }
        return path.reversed()
    }
}
