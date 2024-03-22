package app.fit.fitndflow.ui.features.categories

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import app.fit.fitndflow.domain.model.CategoryModel
import app.fit.fitndflow.domain.model.ExerciseModel
import app.fit.fitndflow.ui.features.categories.CreationOrModifyInputDialog.Companion.TYPE_CATEGORY
import app.fit.fitndflow.ui.features.common.AccessibilityInterface
import app.fit.fitndflow.ui.features.common.AccessibilityUtils
import app.fit.fitndflow.ui.features.common.CommonFragment
import app.fit.fitndflow.ui.features.exercises.ExerciseListFragment
import app.fit.fitndflow.ui.features.exercises.ExercisesAdapter
import app.fit.fitndflow.ui.features.training.AddSerieTrainingFragment
import app.fit.fitndflow.ui.features.training.SerieAdapterCallback
import com.fit.fitndflow.R
import com.fit.fitndflow.databinding.FragmentCategoriesListBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CategoriesListFragment : CommonFragment(), CategoryAdapterCallback, AccessibilityInterface,
    SerieAdapterCallback, DialogCallbackDelete {

    private lateinit var binding: FragmentCategoriesListBinding
    private var categoryList = listOf<CategoryModel>()
    private val categoriesViewModel: CategoriesViewModel by activityViewModels()
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var exercisesAdapter: ExercisesAdapter
    private var isEditMode: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesListBinding.inflate(layoutInflater)
        val myView = binding.root
        super.onCreateView(inflater, container, savedInstanceState)
        instantiateCategoriesAdapter()
        setOnClickListeners()
        initAccessibility()
        binding.txtSearch.addTextChangedListener(AccessibilityUtils.createTextWatcher(this))
        addTextWatcher()
        return myView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachObservers() //todo este será substituido por attachObservers()
        categoriesViewModel.requestCategoriesFromModel(requireContext())
    }

    private fun attachObservers(){
        categoriesViewModel.state.onEach(::handleState).launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleState(state: CategoriesViewModel.State){
        when(state){
            is CategoriesViewModel.State.CategoryListRecived -> {
                printCategories(state.categoryList)
                if(state.showSlideSuccess){
                    showSlideSaved()
                }
                hideLoading()
            }
            CategoriesViewModel.State.FullScreenError -> {
                hideLoading()
                showBlockError()
            }
            CategoriesViewModel.State.SlideError -> {
                hideLoading()
                showSlideError()
            }
            CategoriesViewModel.State.Loading -> {
                showLoading()
            }
        }
    }
    private fun printCategories(listRecived: List<CategoryModel>){
        categoryList = listRecived
        categoriesAdapter.setCategoryList(categoryList)
    }
    private fun addTextWatcher(){
        binding.txtSearch.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Nothing to do
            }
            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                charSequence?.let { searchText ->
                    val filteredList = mutableListOf<ExerciseModel>()
                    if(searchText.isBlank()){
                        instantiateCategoriesAdapter()
                        categoriesAdapter.setCategoryList(categoryList)
                    } else {
                        categoryList.forEach{ category ->
                            category.exerciseList?.forEach { exercise ->
                                if(exercise.name.contains(searchText, ignoreCase = true)) {
                                    filteredList.add(exercise)
                                }
                            }
                        }
                        instantiateExercisesAdapter(filteredList)
                        exercisesAdapter.setExerciseModelList(filteredList)
                    }
                }
            }
            override fun afterTextChanged(charSequence: Editable?) {
                //Nothing to do
            }
        })
    }

    private fun instantiateCategoriesAdapter(){
        categoriesAdapter = CategoriesAdapter(this)
        binding.recyclerCategories.setHasFixedSize(true)
        binding.recyclerCategories.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerCategories.adapter = categoriesAdapter
    }

    private fun instantiateExercisesAdapter(filteredList: MutableList<ExerciseModel>){
        exercisesAdapter = ExercisesAdapter(filteredList, this)
        binding.recyclerCategories.setHasFixedSize(true)
        binding.recyclerCategories.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerCategories.adapter = exercisesAdapter
    }

    private fun setOnClickListeners() {
        binding.apply {
            floatingBtn.setOnClickListener{
                isEditMode = !isEditMode
                if(isEditMode){
                    floatingBtn.setImageResource(R.drawable.svg_check)
                } else {
                    floatingBtn.setImageResource(R.drawable.svg_pencil)
                }
                categoriesAdapter.setEditMode(isEditMode)
            }
        }
    }

    override fun showExercises(category: CategoryModel) {
        addFragment(ExerciseListFragment.newInstance(category))
    }

    override fun showSeries(exercise: ExerciseModel) {
        if(exercise.id != 0){
            addFragment(AddSerieTrainingFragment.newInstance(exercise))
        } else {
            showBlockError()
        }
    }

    override fun showCreationDialog() {
        CreationOrModifyInputDialog.newInstance(TYPE_CATEGORY).show(childFragmentManager, CreationOrModifyInputDialog.TAG)
    }

    override fun showDeleteDialog(id: Int) {
        ConfirmationDialogFragment.newInstance(this, ConfirmationDialogFragment.DELETE_CATEGORY, id).show(childFragmentManager, ConfirmationDialogFragment.TAG)
    }

    override fun showModifyDialog(id: Int, name: String?) {
        CreationOrModifyInputDialog.newInstance(TYPE_CATEGORY, id, name!!).show(childFragmentManager, CreationOrModifyInputDialog.TAG);
    }

    override fun onClickAcceptDelete(id: Int) {
        categoriesViewModel.deleteCategory(id, requireContext())
    }

    override fun initAccessibility() {
        val searchExercise: String = requireContext().getString(R.string.search_exercise)
        binding.txtSearch.accessibilityDelegate = AccessibilityUtils.createAccesibilityDelegate(searchExercise + binding.txtSearch.text.toString())
    }
}