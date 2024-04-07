package app.fit.fitndflow.usecase

import app.fit.fitndflow.domain.model.UserModel
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import app.fit.fitndflow.domain.usecase.RegisterUserUseCase
import app.fit.fitndflow.domain.usecase.RegisterUserUseCaseParams
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RegisterUserUseCaseUseCaseTest : CommonUseCaseTest() {

    private lateinit var registerUserUseCase: RegisterUserUseCase
    private lateinit var fitnFlowRepository: FitnFlowRepository

    @Before
    fun setUp() {
        fitnFlowRepository = mockk()
        registerUserUseCase = RegisterUserUseCase(fitnFlowRepository)
    }

    @Test
    fun `given registerUser() from repository returns userModel, when invoke useCase then returns expected result`() {
        //GIVEN
        val userModel: UserModel = mockk()
        coEvery { fitnFlowRepository.registerUser(any(), any(), any()) } answers { userModel }
        //WHEN
        var result: UserModel? = null
        testCoroutineDispatcher.runTest {
            registerUserUseCase(RegisterUserUseCaseParams()).collect { result = it }
        }
        //THEN
        checkNotNull(result)
        assert(result == userModel)
        coVerify(exactly = 1) { fitnFlowRepository.registerUser(any(), any(), any()) }
    }

}