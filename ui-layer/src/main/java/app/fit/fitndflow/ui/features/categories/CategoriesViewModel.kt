package app.fit.fitndflow.ui.features.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.fit.fitndflow.domain.model.CategoryModel
import app.fit.fitndflow.domain.usecase.AddCategoryUseCase
import app.fit.fitndflow.domain.usecase.AddCategoryUseCaseParams
import app.fit.fitndflow.domain.usecase.CategoryModelInLanguages
import app.fit.fitndflow.domain.usecase.DeleteCategoryUseCase
import app.fit.fitndflow.domain.usecase.GetCategoriesUseCase
import app.fit.fitndflow.domain.usecase.GetCategoryToDeleteParams
import app.fit.fitndflow.domain.usecase.ModifyCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val addCategoryUseCase: AddCategoryUseCase,
    private val modifyCategoryUseCase: ModifyCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase

) : ViewModel() {
    private val _state = MutableSharedFlow<State>()
    val state = _state.asSharedFlow()
    var lastName: String? = null

    fun requestCategoriesFromModel() {
        viewModelScope.launch {
            getCategoriesUseCase()
                .onStart { _state.emit(State.Loading) }
                .catch { _state.emit(State.FullScreenError) }
                .collect { _state.emit(State.CategoryListRecived(it, false)) }

        }
    }

        fun addNewCategory(language: String, nameCategory: String) {
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

        fun modifyCategory(language: String, categoryName: String, categoryId: Int) {
            val params = CategoryModelInLanguages(categoryId, categoryName, language, "")
            viewModelScope.launch{
                modifyCategoryUseCase(params)
                    .onStart { _state.emit(State.Loading) }
                    .catch { _state.emit(State.SlideError) }
                    .collect{ _state.emit(State.CategoryListRecived(it, true)) }
            }
        }

    fun deleteCategory(categoryId: Int) {
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