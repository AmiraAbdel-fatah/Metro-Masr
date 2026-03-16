package domain.useCase

import com.amira.metro_masr.domain.MetroRepository
import com.amira.metro_masr.domain.model.MetroLine


class GetFirstStationUseCase(val repo: MetroRepository) {
    fun execute(line: MetroLine): String {
        return repo.getStations()
            .filter { it.line == line }
            .minBy { it.order }
            .nameEn
    }
}