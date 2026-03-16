package domain.useCase

import com.amira.metro_masr.domain.model.Station


class GetDirectionUseCase(
    private val getFirstStationUseCase: GetFirstStationUseCase,
    private val getLastStationUseCase: GetLastStationUseCase
) {
    operator fun invoke(
        currentStation: Station,
        nextStation: Station,
    ): String {
        val first = getFirstStationUseCase.execute(currentStation.line)
        val last = getLastStationUseCase.invoke(currentStation.line)

        return if (nextStation.order > currentStation.order) {
            last
        } else {
            first
        }
    }
}