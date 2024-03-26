package app.fit.fitndflow.ui.features.exercises

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
import app.fit.fitndflow.ui.features.categories.ConfirmationDialogFragment
import app.fit.fitndflow.ui.features.categories.CreationOrModifyInputDialog
import app.fit.fitndflow.ui.features.categories.CreationOrModifyInputDialog.Companion.TYPE_EXERCISE
import app.fit.fitndflow.ui.features.categories.DialogCallbackDelete
import app.fit.fitndflow.ui.features.common.CommonFragment
import app.fit.fitndflow.ui.features.training.AddSerieTrainingFragment
import app.fit.fitndflow.ui.features.training.SerieAdapterCallback
import com.fit.fitndflow.R
import com.fit.fitndflow.databinding.FragmentExercisesListBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ExerciseListFragment : CommonFragment(), SerieAdapterCallback, DialogCallbackDelete {

    companion object {
        val KEY_CATEGORY = "actualCategory"
        fun newInstance(category: CategoryModel): ExerciseListFragment {
            val exerciseListFragment = ExerciseListFragment()
            val bundle = Bundle()
            bundle.putSerializable(KEY_CATEGORY, category)
            exerciseListFragment.arguments = bundle
            return exerciseListFragment
        }
    }

    private lateinit var binding: FragmentExercisesListBinding
    private lateinit var exercisesAdapter: ExercisesAdapter
    private val exercisesViewModel: ExercisesViewModel by activityViewModels()
    private val category: CategoryModel by lazy { requireArguments().getSerializable(KEY_CATEGORY) as CategoryModel }
    private var isEditMode: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExercisesListBinding.inflate(layoutInflater)
        val myView = binding.root
        super.onCreateView(inflater, container, savedInstanceState)
        setOnClickListeners()
        addTextWatcher()
        return myView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachObservers()
        binding.categoryTitle.setText(category.name)
        category.exerciseList?.let { instantiateExercisesAdapter(it) }
    }

    private fun attachObservers() {
        exercisesViewModel.state.onEach(::handleState).launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleState(state: ExercisesViewModel.State) {
        when (state) {
            is ExercisesViewModel.State.ExerciseListRecived -> {
                printExercises(state.exerciseList.toMutableList())
                showSlideSaved()
                hideLoading()
            }

            ExercisesViewModel.State.SlideError -> {
                hideLoading()
                showSlideError()
            }

            ExercisesViewModel.State.Loading -> {
                showLoading()
            }
        }

//        exercisesViewModel.mutableSlideError.value = false
//        exercisesViewModel.isLoading.value = false
//
//        val categoryObserver = Observer<CategoryModel> { category ->
//            exercisesAdapter.setExerciseModelList(category.exerciseList)
//        }
//        exercisesViewModel.actualCategory.observe(viewLifecycleOwner, categoryObserver)
//
//        val slideErrorObserver = Observer<Boolean> { isError ->
//            if (isError) {
//                showSlideError()
//                exercisesViewModel.mutableSlideError.value = false
//            }
//        }
//        exercisesViewModel.mutableSlideError.observe(
//            viewLifecycleOwner,
//            slideErrorObserver
//        )
//
//        val fullScreenErrorObserver = Observer<Boolean> { isError ->
//            if (isError) {
//                showBlockError()
//                exercisesViewModel.mutableFullScreenError.value = false
//            }
//        }
//        exercisesViewModel.mutableFullScreenError.observe(
//            viewLifecycleOwner,
//            fullScreenErrorObserver
//        )
//
//        val savedSuccessOberver = Observer<Boolean> { isSaved ->
//            if (isSaved) {
//                showSlideSaved()
//                exercisesViewModel.isSaveSuccess.value = false
//            }
//        }
//        exercisesViewModel.isSaveSuccess.observe(
//            viewLifecycleOwner,
//            savedSuccessOberver
//        )
//
//        val observerLoading = Observer<Boolean> { isLoading ->
//            if (isLoading) {
//                showLoading()
//            } else {
//                hideLoading()
//            }
//        }
//        exercisesViewModel.isLoading.observe(viewLifecycleOwner, observerLoading)
    }

    private fun printExercises(exerciseListRecived: MutableList<ExerciseModel>){
       category.exerciseList = exerciseListRecived
       exercisesAdapter.setExerciseModelList(category.exerciseList)
    }

    private fun instantiateExercisesAdapter(exerciseListRecived: List<ExerciseModel>) {
        exercisesAdapter = ExercisesAdapter(exerciseListRecived, this)
        binding.recyclerCategories.setHasFixedSize(true)
        binding.recyclerCategories.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerCategories.adapter = exercisesAdapter
    }

    private fun addTextWatcher() {
        binding.txtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Nothing to do
            }

            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                charSequence?.let { searchText ->
                    val filteredList = mutableListOf<ExerciseModel>()
                    val exerciseList: List<ExerciseModel> = category.exerciseList!!
                    exerciseList.forEach { exercise ->
                        if (exercise.name.contains(searchText, ignoreCase = true)) {
                            filteredList.add(exercise)
                        }
                    }
                    exercisesAdapter.setExerciseModelList(filteredList)
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                //Nothing to do
            }
        })
    }

    private fun setOnClickListeners() {
        binding.apply {
            floatingBtn.setOnClickListener {
                isEditMode = !isEditMode
                if (isEditMode) {
                    floatingBtn.setImageResource(R.drawable.svg_check)
                } else {
                    floatingBtn.setImageResource(R.drawable.svg_pencil)
                }
                exercisesAdapter.setEditMode(isEditMode)
            }
        }
    }

    override fun onClickAcceptDelete(exerciseId: Int) {         //todo!!!!!!!!!!!!!!!!!!!!
       // exercisesViewModel.deleteExercise(exerciseId, requireContext())
    }

    override fun showSeries(exercise: ExerciseModel) {
        if (exercise.id != 0) {
            addFragment(AddSerieTrainingFragment.newInstance(exercise))
        } else {
            showBlockError()
        }
    }

    override fun showCreationDialog() {
        CreationOrModifyInputDialog.newInstance(TYPE_EXERCISE, category.id)
            .show(childFragmentManager, CreationOrModifyInputDialog.TAG)
    }

    override fun showDeleteDialog(id: Int) {
        ConfirmationDialogFragment.newInstance(this, ConfirmationDialogFragment.DELETE_EXERCISE, id)
            .show(childFragmentManager, ConfirmationDialogFragment.TAG)
    }

    override fun showModifyDialog(exerciseId: Int, exerciseName: String) {
        CreationOrModifyInputDialog.newInstance(TYPE_EXERCISE, exerciseId, exerciseName, category.id)
            .show(childFragmentManager, CreationOrModifyInputDialog.TAG)
    }
}