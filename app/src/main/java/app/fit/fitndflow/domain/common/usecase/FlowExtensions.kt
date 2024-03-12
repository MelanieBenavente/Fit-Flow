package app.fit.fitndflow.domain.common.usecase

import app.fit.fitndflow.domain.common.model.CommonFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch

fun <T> Flow<T>.catchFailure(action: suspend FlowCollector<T>.(cause: Throwable) -> Unit): Flow<T> =
    this.catch {
        if (it is CommonFailure) throw it else action(it) }