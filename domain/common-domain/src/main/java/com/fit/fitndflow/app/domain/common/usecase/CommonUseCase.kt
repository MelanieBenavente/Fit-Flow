package com.fit.fitndflow.app.domain.common.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class UseCase<in Params : Any, out OUTPUT>() {

    abstract fun run(params: Params): Flow<OUTPUT>

    operator fun invoke(params: Params = Unit as Params) = run(params).flowOn(Dispatchers.IO)

}