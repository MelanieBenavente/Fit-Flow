package app.fit.fitndflow.ui.features.categories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.fit.fitndflow.data.repository.FitnFlowRepositoryImpl
import app.fit.fitndflow.domain.model.CategoryModel
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import app.fit.fitndflow.domain.usecase.AddCategoryUseCase
import app.fit.fitndflow.domain.usecase.AddCategoryUseCaseParams
import app.fit.fitndflow.domain.usecase.CategoryModelInLanguages
import app.fit.fitndflow.domain.usecase.DeleteCategoryUseCase
import app.fit.fitndflow.domain.usecase.GetCategoriesUseCase
import app.fit.fitndflow.domain.usecase.GetCategoryToDeleteParams
import app.fit.fitndflow.domain.usecase.ModifyCategoryUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch


class CategoriesViewModel : ViewModel() {
    private val _state = MutableSharedFlow<State>()
    val state = _state.asSharedFlow()
    var lastName: String? = null
    private val fitnFlowRepository: FitnFlowRepository = FitnFlowRepositoryImpl.getInstance()

    fun requestCategoriesFromModel(context: Context) {
        val getCategoriesUseCase = GetCategoriesUseCase(context, fitnFlowRepository)
        viewModelScope.launch {
            getCategoriesUseCase()
                .onStart { _state.emit(State.Loading) }
                .catch { _state.emit(State.FullScreenError) }
                .collect { _state.emit(State.CategoryListRecived(it, false)) }

        }
    }

        fun addNewCategory(context: Context, language: String, nameCategory: String) {
            val addCategoryUseCase = AddCategoryUseCase( fitnFlowRepository, context)
            val params = AddCategoryUseCaseParams(nameCategory, language)
            viewModelScope.launch {
                addCategoryUseCase(params)
                    .onStart { _state.emit(State.Loading) }
                    .catch {
                        lastName = nameCategory
                        _state.emit(State.SlideError)
                    }
                    .collect {
                        lastName = null
                        _state.emit(State.CategoryListRecived(it, true))
                    }
            }
        }

        fun modifyCategory(context: Context, language: String, categoryName: String, categoryId: Int) {
            val modifyCategoryUseCase = ModifyCategoryUseCase(fitnFlowRepository, context)
            val params = CategoryModelInLanguages(categoryId, categoryName, language, "")
            viewModelScope.launch{
                modifyCategoryUseCase(params)
                    .onStart { _state.emit(State.Loading) }
                    .catch { _state.emit(State.SlideError) }
                    .collect{ _state.emit(State.CategoryListRecived(it, true)) }
            }
        }

    fun deleteCategory(categoryId: Int, context: Context) {
        val deleteCategoryUseCase = DeleteCategoryUseCase(fitnFlowRepository, context)
        val params = GetCategoryToDeleteParams(categoryId)
        viewModelScope.launch {
            deleteCategoryUseCase(params)
                .onStart { _state.emit(State.Loading) }
                .catch { _state.emit(State.SlideError) }
                .collect{ _state.emit(State.CategoryListRecived(it, true)) }
        }
    }


    sealed class State {
        object Loading : State()
        object FullScreenError : State()
        object SlideError : State()
        data class CategoryListRecived(
            val categoryList: List<CategoryModel>,
            val showSlideSuccess: Boolean
        ) : State()
    }
}