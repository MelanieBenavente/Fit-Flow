package app.fit.fitndflow.ui.features.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import app.fit.fitndflow.ui.features.common.AccessibilityUtils
import app.fit.fitndflow.ui.features.common.BorderType
import app.fit.fitndflow.ui.features.common.CommonDialogFragment
import com.fit.fitndflow.R
import com.fit.fitndflow.databinding.DialogCreationInputBinding

class CreationOrModifyInputDialog : CommonDialogFragment() {

    companion object {
        @JvmField
        val TYPE_CATEGORY = 1

        @JvmField
        val TYPE_EXERCISE = 2

        @JvmField
        val TAG = "CreationDialog"
        private val KEY_TYPE = "createType"
        private val KEY_ID = "keyId"
        private val KEY_NAME = "keyName"

        @JvmStatic
        fun newInstance(createType: Int, id: Int, name: String): CreationOrModifyInputDialog {
            val modifyInputDialog = CreationOrModifyInputDialog()
            val bundle = Bundle()
            bundle.putInt(KEY_TYPE, createType)
            bundle.putInt(KEY_ID, id)
            bundle.putString(KEY_NAME, name)
            modifyInputDialog.arguments = bundle
            return modifyInputDialog
        }

        @JvmStatic
        fun newInstance(createType: Int): CreationOrModifyInputDialog {
            val creationOrModifyInputDialog = CreationOrModifyInputDialog()
            val bundle = Bundle()
            bundle.putInt(KEY_TYPE, createType)
            creationOrModifyInputDialog.arguments = bundle
            return creationOrModifyInputDialog
        }
    }

    private val categoriesViewModel: CategoriesViewModel by activityViewModels()
    private val categoriesAndExercisesViewModelJava: CategoriesAndExercisesViewModelJava by activityViewModels()
    private lateinit var binding: DialogCreationInputBinding
    private val createType: Int by lazy { requireArguments().getSerializable(KEY_TYPE) as Int }
    private val itemId: Int? by lazy { requireArguments().getSerializable(KEY_ID) as? Int? }
    private val itemName: String? by lazy { requireArguments().getSerializable(KEY_NAME) as? String? }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        initListeners()
        return view
    }
    private fun printNameIfExists() {
        if (itemId != null) {
            binding.newCategoryTxt.setText(itemName)
        } else if (categoriesViewModel.lastName != null) {
            binding.newCategoryTxt.setText(categoriesViewModel.lastName)
        }
    }
    private fun initListeners() {
        binding.apply {
            printNameIfExists()
            when (createType) {
                TYPE_CATEGORY -> {
                    creationDialogTitle.setText(R.string.choose_category_name)
                    newCategoryTxt.setHint(getString(R.string.espalda))
                    creationDialogAddBtn.setOnClickListener {
                        val categoryName: String = newCategoryTxt.text.toString()
                        val language: String = requireContext().getString(R.string.language)
                        newCategoryTxt.accessibilityDelegate =
                            AccessibilityUtils.createAccesibilityDelegate(language + newCategoryTxt.text.toString())
                        if (!newCategoryTxt.text.toString().isEmpty()) {
                            if (itemId != null) {
                                categoriesViewModel.modifyCategory(
                                    requireContext(),
                                    language,
                                    categoryName,
                                    itemId!!
                                )
                            } else {
                                categoriesViewModel.addNewCategory(
                                    requireContext(),
                                    language,
                                    categoryName
                                )
                            }
                        }
                        dismissAllowingStateLoss()
                    }
                }
                else -> {
                    creationDialogTitle.setText(R.string.choose_exercise_name)
                    newCategoryTxt.setHint(getString(R.string.mancuernas))
                    creationDialogAddBtn.setOnClickListener {
                        val exerciseName: String = newCategoryTxt.text.toString()
                        val language: String = requireContext().getString(R.string.language)
                        newCategoryTxt.accessibilityDelegate =
                            AccessibilityUtils.createAccesibilityDelegate(language + newCategoryTxt.text.toString())
                        if(!newCategoryTxt.text.toString().isEmpty()) {
                            if (itemId != null) {
                                categoriesAndExercisesViewModelJava.modifyExercise(requireContext(), itemId!!, language, exerciseName)
                            } else {
                                categoriesAndExercisesViewModelJava.addNewExercise(requireContext(), language, exerciseName)
                            }
                        }
                        dismissAllowingStateLoss()
                    }
                }
            }
            creationDialogCancelBtn.setOnClickListener { dismissAllowingStateLoss() }
        }
    }


    override fun getBorderType(): BorderType {
        return BorderType.BORDERLINE
    }

    override fun getBinding(): ViewBinding {
        binding = DialogCreationInputBinding.inflate(layoutInflater)
        return binding
    }
}