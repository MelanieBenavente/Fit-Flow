package app.fit.fitndflow.ui.features.training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import app.fit.fitndflow.domain.model.ExerciseModel
import app.fit.fitndflow.domain.model.SerieModel
import app.fit.fitndflow.ui.features.categories.ConfirmationDialogFragment
import app.fit.fitndflow.ui.features.categories.DialogCallbackDelete
import app.fit.fitndflow.ui.features.common.CommonFragment
import app.fit.fitndflow.ui.features.common.getTranslatedString
import app.fit.fitndflow.ui.features.common.hideKeyBoard
import com.fit.fitndflow.R
import com.fit.fitndflow.databinding.AddSerieTrainingFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class AddSerieTrainingFragment : CommonFragment(), TrainingCallback, DialogCallbackDelete {

    companion object {
        val INPUT_KG = 2.5
        val INPUT_REPS = 1
        val INPUT_ZERO = 0
        val KEY_EXERCISE = "actualExercise"

        @JvmStatic
        fun newInstance(exercise: ExerciseModel): AddSerieTrainingFragment {
            val addSerieTrainingFragment = AddSerieTrainingFragment()
            val bundle = Bundle()
            bundle.putSerializable(KEY_EXERCISE, exercise)
            addSerieTrainingFragment.arguments = bundle
            return addSerieTrainingFragment
        }
    }

    private lateinit var binding: AddSerieTrainingFragmentBinding
    private val addSerieTrainingViewModel: AddSerieTrainingViewModel by viewModels()
    private val exercise: ExerciseModel by lazy { requireArguments().getSerializable(KEY_EXERCISE) as ExerciseModel }
    private lateinit var seriesAdapter: SeriesAdapter
    private var currentSelectedSerieModel: SerieModel? = null
    private var lastRepsWritten: Int? = null
    private var lastKgWritten: Double? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddSerieTrainingFragmentBinding.inflate(layoutInflater)
        val myView = binding.root
        super.onCreateView(inflater, container, savedInstanceState)
        binding.exerciseNameTitle.setText(exercise.name.getTranslatedString(requireContext()))
        initListeners()
        return myView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachObservers()
        addSerieTrainingViewModel.getSerieListOfExerciseAdded(exercise.id!!)
    }

    private fun attachObservers() {
        addSerieTrainingViewModel.state.onEach(::handleState)
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleState(state: AddSerieTrainingViewModel.State) {
        when (state) {
            is AddSerieTrainingViewModel.State.SeriesChangedInExerciseDetail -> {
                exercise.record = state.serieInfoWrapper.newRecord
                instantiateSeriesAdapter(state.serieInfoWrapper.serieList)
                setScreenEditMode(false)
                showSlideSaved()
                if (state.showLastSerieAdded) {
                    binding.apply{
                        etCounterReps.setText(lastRepsWritten.toString())
                        etCounterKg.setText(lastKgWritten.toString())
                    }
                }
                if(state.serieInfoWrapper.showRecord){
                    showRecordDialog()
                }

                hideLoading()
            }
            is AddSerieTrainingViewModel.State.SerieListRecived -> {
                instantiateSeriesAdapter(state.serieList)
                setScreenEditMode(false)
                printFirstSerieAdded()
                hideLoading()
            }

            AddSerieTrainingViewModel.State.FullScreenError -> {
                hideLoading()
                showBlockError()
            }

            AddSerieTrainingViewModel.State.SlideError -> {
                hideLoading()
                showSlideError()
            }

            AddSerieTrainingViewModel.State.Loading -> {
                showLoading()
            }
        }
    }

    private fun initListeners() {
        binding.apply {
            saveAndUpdateBtn.setOnClickListener {
                val reps: Int = if (etCounterReps.text.toString()
                        .isEmpty()
                ) 0 else etCounterReps.text.toString().toInt()
                lastRepsWritten = reps

                val kg: Double = if (etCounterKg.text.toString()
                        .isEmpty()
                ) 0.0 else etCounterKg.text.toString().toDouble()
                lastKgWritten = kg

                currentSelectedSerieModel?.let {
                    addSerieTrainingViewModel.modifySerie(it.id!!, reps, kg, exercise.record)
                } ?: addSerieTrainingViewModel.addNewSerie(reps, kg, exercise.id!!, exercise.record)
                activity?.hideKeyBoard()
            }

            deleteAndCleanBtn.setOnClickListener {
                if (currentSelectedSerieModel != null) {
                    showDeleteDialog(currentSelectedSerieModel!!.id!!)
                } else {
                    etCounterReps.setText("")
                    etCounterKg.setText("")
                }
            }

            btnIncrement.setOnClickListener {
                val currentValue: Double = if (etCounterKg.text.toString()
                        .isEmpty()
                ) 0.0 else etCounterKg.text.toString().toDouble()
                val newValue = currentValue + INPUT_KG
                etCounterKg.setText(newValue.toString())
            }

            btnDecrement.setOnClickListener {
                val currentValue =
                    if (etCounterKg.text.toString().isEmpty()) 0.0 else etCounterKg.text.toString()
                        .toDouble()
                val newValue = currentValue - INPUT_KG
                if (newValue > INPUT_ZERO) {
                    etCounterKg.setText(newValue.toString())
                } else {
                    etCounterKg.setText(INPUT_ZERO.toString())
                }
            }

            btnIncrement2.setOnClickListener {
                val currentValue = if (etCounterReps.text.toString()
                        .isEmpty()
                ) 0 else etCounterReps.text.toString().toInt()
                val newValue = currentValue + INPUT_REPS
                etCounterReps.setText(newValue.toString())
            }

            btnDecrement2.setOnClickListener {
                val currentValue = if (etCounterReps.text.toString()
                        .isEmpty()
                ) 0 else etCounterReps.text.toString().toInt()
                val newValue = currentValue - INPUT_REPS
                if (newValue > INPUT_ZERO) {
                    etCounterReps.setText(newValue.toString())
                } else {
                    etCounterReps.setText(INPUT_ZERO.toString())
                }
            }
        }
    }

    private fun instantiateSeriesAdapter(serieModelList: List<SerieModel>) {
        seriesAdapter = SeriesAdapter(serieModelList, this)
        binding.apply {
            addSerieLayout.setHasFixedSize(true)
            addSerieLayout.layoutManager = LinearLayoutManager(requireContext())
            addSerieLayout.adapter = seriesAdapter
        }
    }

    private fun setScreenEditMode(isEditMode: Boolean) {
        binding.apply {
            if (isEditMode) {
                etCounterReps.setText(currentSelectedSerieModel!!.reps?.toString() ?: "0")
                etCounterKg.setText(currentSelectedSerieModel!!.kg?.toString() ?: "0.0")
                saveAndUpdateBtn.setText(R.string.update)
                saveAndUpdateBtn.setBackgroundResource(R.drawable.shape_serie_add_btn)
                deleteAndCleanBtn.setText(R.string.delete_btn)
                deleteAndCleanBtn.setBackgroundResource(R.drawable.shape_serie_delete_btn)
            } else {
                currentSelectedSerieModel = null
                etCounterReps.setText("")
                etCounterKg.setText("")
                saveAndUpdateBtn.setText(R.string.add)
                saveAndUpdateBtn.setBackgroundResource(R.drawable.shape_serie_add_and_clean_btns)
                deleteAndCleanBtn.setText(R.string.clean)
                deleteAndCleanBtn.setBackgroundResource(R.drawable.shape_serie_add_and_clean_btns)
            }
        }
    }
    private fun printFirstSerieAdded(){
        exercise.lastFirstSerie?.let {
            lastRepsWritten = if (exercise.lastFirstSerie!!.reps == null) 0 else exercise.lastFirstSerie!!.reps
            lastKgWritten = if (exercise.lastFirstSerie!!.kg == null) 0.0 else exercise.lastFirstSerie!!.kg
            binding.apply {
                etCounterReps.setText(lastRepsWritten.toString())
                etCounterKg.setText(lastKgWritten.toString())
            }
        }
    }

    private fun showRecordDialog(){
        exercise.record?.let {
            RecordSerieDialog.newInstance().show(childFragmentManager, RecordSerieDialog.TAG)
        }
    }

    private fun showDeleteDialog(id: Int) {
        ConfirmationDialogFragment.newInstance(this, ConfirmationDialogFragment.DELETE_SERIE, id)
            .show(childFragmentManager, ConfirmationDialogFragment.TAG)
    }

    override fun onClickAcceptDelete(serieId: Int) {
        addSerieTrainingViewModel.deleteSerie(serieId)
    }

    override fun clickListenerInterfaceAdapter(input: SerieModel?) {
        currentSelectedSerieModel = input
        val isEditMode = input != null
        setScreenEditMode(isEditMode)
    }
}
