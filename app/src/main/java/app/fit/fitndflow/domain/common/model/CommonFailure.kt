package app.fit.fitndflow.domain.common.model

abstract class CommonFailure : Throwable()

sealed class GlobalFailure : CommonFailure() {
    data class GlobalError(val throwable: Throwable) : GlobalFailure()
}