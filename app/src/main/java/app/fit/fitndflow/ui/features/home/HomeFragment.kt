package app.fit.fitndflow.ui.features.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import app.fit.fitndflow.data.common.SharedPrefs
import app.fit.fitndflow.domain.Utils
import app.fit.fitndflow.domain.model.CategoryModel
import app.fit.fitndflow.domain.model.ExerciseModel
import app.fit.fitndflow.ui.features.categories.CategoriesListFragment
import app.fit.fitndflow.ui.features.common.CommonFragment
import app.fit.fitndflow.ui.features.common.notification.MyNotificationManager
import app.fit.fitndflow.ui.features.common.notification.MyNotificationManager.scheduleNotification
import app.fit.fitndflow.ui.features.training.AddSerieTrainingFragment
import com.fit.fitndflow.R
import com.fit.fitndflow.databinding.MainListFragmentBinding
import java.util.Calendar
import java.util.Calendar.DAY_OF_YEAR
import java.util.Calendar.HOUR_OF_DAY
import java.util.Date

class HomeFragment : CommonFragment(), ExerciseClickCallback {

    private lateinit var binding: MainListFragmentBinding
    private val homeViewModel: HomeViewModel by activityViewModels()
    private var isShownNotificationConfiguration: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainListFragmentBinding.inflate(layoutInflater)
        val myView = binding.root
        super.onCreateView(inflater, container, savedInstanceState)
        if (!isShownNotificationConfiguration) {
            MyNotificationManager.askForPermissions(this)
            isShownNotificationConfiguration = true
        }
        val calendar: Calendar = Calendar.getInstance()
        calendar.add(DAY_OF_YEAR, 1)
        calendar.add(HOUR_OF_DAY, -1)
        val tomorrowInMillis: Long = calendar.timeInMillis
        scheduleNotification(
            requireActivity(),
            tomorrowInMillis,
            MyNotificationManager.TRAINING_TYPE
        )

        return myView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (SharedPrefs.getApikeyFromSharedPRefs(requireContext()) == null) {
            homeViewModel.requestRegisterEmptyUser(requireContext())
        }
        setViewModelObservers()
        setClickListeners()
    }

    private fun printExercises(categoryModelList: List<CategoryModel>) {
        binding.apply {
            emptyTxt.visibility = GONE
            parentContainer.removeAllViews()
            for (category: CategoryModel in categoryModelList) {
                val categoryView = CategoryCustomView(context, category, this@HomeFragment)
                parentContainer.addView(categoryView)
            }
        }
    }

    private fun printEmptyView() {
        binding.apply {
            emptyTxt.visibility = VISIBLE
            parentContainer.removeAllViews()
        }
    }

    private fun setClickListeners() {
        binding.apply {
            btnLeft.setOnClickListener {
                if (homeViewModel.isLoading.value != true) {
                    homeViewModel.dayBefore()
                }
            }
            btnRight.setOnClickListener {
                if (homeViewModel.isLoading.value != true) {
                    homeViewModel.dayAfter()
                }
            }
            buttonPanel.setOnClickListener {
                if (homeViewModel.isLoading.value != true) {
                    addFragment(CategoriesListFragment())
                }
            }
        }
    }

    private fun setViewModelObservers() {
        val observerCategory = Observer<List<CategoryModel>> { categories ->
            if (categories == null || categories.isEmpty()) {
                binding.buttonPanel.setText(R.string.add_training)
                printEmptyView()
            } else {
                binding.buttonPanel.setText(R.string.add_exercise)
                printExercises(categories)
            }
        }
        homeViewModel.dailyTrainingMutableList.observe(viewLifecycleOwner, observerCategory)

        val observerLoading = Observer<Boolean> { isLoading ->
            try {
                if (isLoading) {
                    showLoading()
                } else {
                    hideLoading()
                }
            } catch (exception: Exception) {
                Log.e("Error", "show loading")
            }
        }
        homeViewModel.isLoading.observe(viewLifecycleOwner, observerLoading)

        val actualDateObserver = Observer<Date> { date ->
            homeViewModel.requestTrainingFromModel(requireContext())
            binding.apply {
                if (Utils.isYesterday(date)) {
                    dateName.setText(R.string.yesterday_date_selector)
                    dayOfWeek.visibility = GONE
                } else if (Utils.isToday(date)) {
                    dateName.setText(R.string.today_date_selector)
                    dayOfWeek.visibility = GONE
                } else if (Utils.isTomorrow(date)) {
                    dateName.setText(R.string.tomorrow_date_selector)
                    dayOfWeek.visibility = GONE
                } else {
                    dateName.setText(Utils.getCalendarFormatDate(date))
                    dayOfWeek.setText(Utils.dayOfWeek(date))
                    dayOfWeek.visibility = VISIBLE
                }
            }
        }
        homeViewModel.mutableActualDate.observe(viewLifecycleOwner, actualDateObserver)

        val errorFullScreenOvserver = Observer<Boolean> { isError ->
            if (isError) {
                showBlockError()
                homeViewModel.mutableFullScreenError.value = false
            }
        }
        homeViewModel.mutableFullScreenError.observe(viewLifecycleOwner, errorFullScreenOvserver)
    }

    override fun showExerciseTrainingDetail(exercise: ExerciseModel) {
        addFragment(AddSerieTrainingFragment.newInstance(exercise))
    }
}