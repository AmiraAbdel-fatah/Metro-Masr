package domain.useCase

import com.amira.metro_masr.domain.MetroRepository


class CalcTimeUseCase(val repo: MetroRepository) {

    operator fun invoke(count: Int) = count * repo.getTravelTime()
}