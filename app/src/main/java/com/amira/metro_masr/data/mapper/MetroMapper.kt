package com.amira.metro_masr.data.mapper

import com.amira.metro_masr.data.model.StationDto
import com.amira.metro_masr.domain.model.MetroLine
import com.amira.metro_masr.domain.model.Station

object MetroMapper {

    fun toDomain(dto: StationDto): Station {
        return Station(
            id = dto.id,
            nameEn = dto.nameEn,
            nameAr = dto.nameAr ?: dto.nameEn,
            line = dto.line.toMetroLine(),
            order = dto.order,
            isTransfer = dto.isTransfer,
            transferLines = dto.transferLines.map { it.toMetroLine() },
        )
    }

    private fun String.toMetroLine(): MetroLine =
        when (this.trim().uppercase()) {
            "LINE_1", "FIRST LINE", "1", "الخط الأول" -> MetroLine.LINE_1
            "LINE_2", "SECOND LINE", "2", "الخط الثاني" -> MetroLine.LINE_2
            "LINE_3", "THIRD LINE", "3", "الخط الثالث" -> MetroLine.LINE_3
            else -> throw IllegalArgumentException("Unknown line: $this")
        }
}
