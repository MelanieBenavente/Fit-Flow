package app.fit.fitndflow.usecase

import app.fit.fitndflow.domain.model.CategoryModel
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import app.fit.fitndflow.domain.usecase.GetCategoriesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

//todo!!!!!!!!! pending to finish test
//class GetCategoriesUseCaseTest {
//
//    private lateinit var getCategoriesUseCase: GetCategoriesUseCase
//    private lateinit var fitnFlowRepository: FitnFlowRepository
//
//    @Before
//    fun setUp(){
//        fitnFlowRepository = mockk()
//        getCategoriesUseCase = GetCategoriesUseCase(fitnFlowRepository)
//    }
//
//    @Test
//    fun `given__when__then` (){
//        //GIVEN
//        val categoryList: List<CategoryModel> = mockk()
//        coEvery { fitnFlowRepository.categoryList }
//        //WHEN
//
//        //THEN
//    }
//}