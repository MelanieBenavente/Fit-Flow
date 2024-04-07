package app.fit.fitndflow.viewmodel

import app.cash.turbine.test
import app.fit.fitndflow.domain.model.UserModel
import app.fit.fitndflow.domain.usecase.GetTrainingUseCase
import app.fit.fitndflow.domain.usecase.RegisterUserUseCase
import app.fit.fitndflow.ui.features.home.HomeViewModel
import app.fit.fitndflow.ui.features.home.State
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import net.bytebuddy.matcher.ElementMatchers.returns
import org.junit.After
import org.junit.Test

class HomeViewModelTest : CommonViewModelTest() {

    val registerUserUseCase = mockk<RegisterUserUseCase>()
    val getTrainingUseCase = mockk<GetTrainingUseCase>()
    private val viewModel = HomeViewModel(registerUserUseCase, getTrainingUseCase)

    @After
    fun resetDispatchers() {
        Dispatchers.resetMain()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `given registerUserUseCase() returns flowOf(userModel), when calls requestRegisterEmptyUser() then State is Loading and RegisterCompleted`() {
        //GIVEN
        val userModel = mockk<UserModel>()
        TestScope(StandardTestDispatcher()).runTest {
            every { registerUserUseCase(any()) } returns flowOf(userModel)
            //WHEN
            initDispatchers(this.coroutineContext)
            viewModel.requestRegisterEmptyUser()
            //THEN
            viewModel.state.test {
                assertTrue(awaitItem() is State.Loading)
                assertTrue(awaitItem() is State.RegisterCompleted)
            }
        }
    }
}
// todo!!!!!!!!!!!!!!!!!! pending requestTrainingFromModel test