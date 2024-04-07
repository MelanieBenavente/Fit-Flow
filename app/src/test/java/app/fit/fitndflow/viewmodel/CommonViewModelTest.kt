package app.fit.fitndflow.viewmodel

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.setMain
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

abstract class CommonViewModelTest {
    fun initDispatchers(coroutineContext: CoroutineContext){
        Dispatchers.setMain(coroutineContext[ContinuationInterceptor] as CoroutineDispatcher)
    }
}