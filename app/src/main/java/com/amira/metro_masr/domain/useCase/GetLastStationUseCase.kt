package domain.useCase

import com.amira.metro_masr.domain.MetroRepository
import com.amira.metro_masr.domain.model.MetroLine

class GetLastStationUseCase(val repo: MetroRepository) {
    operator fun invoke(line: MetroLine): String {
        return repo.getStations()
            .filter { it.line == line }
            .maxBy { it.order }
            .nameEn
    }
}