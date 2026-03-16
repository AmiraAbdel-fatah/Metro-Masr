package domain.useCase


class CalcFareUseCase {

    operator fun invoke(count: Int): Int {
        return when {
            count <= 9 -> 10
            count <= 20 -> 15
            else -> 20
        }
    }
}