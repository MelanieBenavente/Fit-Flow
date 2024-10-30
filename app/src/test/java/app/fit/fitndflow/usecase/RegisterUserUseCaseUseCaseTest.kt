package app.fit.fitndflow.usecase

import com.fit.fitndflow.app.domain.user.model.UserModel
import com.fit.fitndflow.app.domain.user.repository.RegisterUserRepository
import app.fit.fitndflow.domain.usecase.userUseCases.RegisterUserUseCase
import com.fit.fitndflow.app.domain.user.userUseCases.RegisterUserUseCaseParams
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
    private lateinit var registerUserRepository: RegisterUserRepository

    @Before
    fun setUp() {
        registerUserRepository = mockk()
        registerUserUseCase = RegisterUserUseCase(registerUserRepository)
    }

    @Test
    fun `given registerUser() from repository returns userModel, when invoke useCase then returns expected result`() {
        //GIVEN
        val userModel: UserModel = mockk()
        coEvery { registerUserRepository.registerUser(any(), any(), any()) } answers { userModel }
        //WHEN
        var result: UserModel? = null
        testCoroutineDispatcher.runTest {
            registerUserUseCase(RegisterUserUseCaseParams()).collect { result = it }
        }
        //THEN
        checkNotNull(result)
        assert(result == userModel)
        coVerify(exactly = 1) { registerUserRepository.registerUser(any(), any(), any()) }
    }

}