package app.fit.fitndflow.domain.common.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class UseCase<in Params : Any, out T>() {

    abstract fun run(params: Params): Flow<T>

    operator fun invoke(params: Params = Unit as Params) = run(params).flowOn(Dispatchers.IO)

}