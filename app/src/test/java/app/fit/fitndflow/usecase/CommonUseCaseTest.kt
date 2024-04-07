package app.fit.fitndflow.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope

sealed class CommonUseCaseTest {
    protected val testCoroutineDispatcher = TestScope(StandardTestDispatcher())
}