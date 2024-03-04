package app.fit.fitndflow.ui.features.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import app.fit.fitndflow.ui.features.common.CommonDialogFragment
import com.fit.fitndflow.R
import com.fit.fitndflow.databinding.DialogConfirmationFragmentBinding

class ConfirmationDialogFragment : CommonDialogFragment() {

    companion object {
        @JvmField
        val DELETE_CATEGORY = 1
        @JvmField
        val DELETE_EXERCISE = 2
        @JvmField
        val DELETE_SERIE = 3
        @JvmField
        val TAG = "ConfirmationDialog"
        private val KEY_CALLBACK = "dialogCallback"
        private val KEY_TYPE = "deleteType"
        private val KEY_ID = "id"

        @JvmStatic
        fun newInstance(
            dialogCallbackDelete: DialogCallbackDelete,
            deleteType: Int,
            id: Int
        ): ConfirmationDialogFragment {
            val confirmationDialogFragment = ConfirmationDialogFragment()
            val bundle = Bundle()
            bundle.putSerializable(KEY_CALLBACK, dialogCallbackDelete)
            bundle.putInt(KEY_TYPE, deleteType)
            bundle.putInt(KEY_ID, id)
            confirmationDialogFragment.arguments = bundle
            return confirmationDialogFragment
        }
    }

    private lateinit var binding: DialogConfirmationFragmentBinding
    private val dialogCallbackDelete: DialogCallbackDelete by lazy {
        requireArguments().getSerializable(KEY_CALLBACK) as DialogCallbackDelete
    }
    private val deleteType: Int by lazy { requireArguments().getSerializable(KEY_TYPE) as Int }
    private val itemId: Int by lazy { requireArguments().getSerializable(KEY_ID) as Int }
    override fun getBorderType(): Int {
        return BORDERLINE
    }

    override fun getBinding(): ViewBinding {
        binding = DialogConfirmationFragmentBinding.inflate(layoutInflater)
        return binding
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        initListeners()
        return view
    }

    private fun initListeners() {
        binding.apply {
            when (deleteType) {
                DELETE_CATEGORY -> {
                    confirmationDialogTitle.setText(R.string.dialog_category_delete)
                    confirmationDialogAcceptBtn.setOnClickListener {
                        dialogCallbackDelete.onClickAcceptDelete(itemId)
                        dismissAllowingStateLoss()
                    }
                }

                DELETE_EXERCISE -> {
                    confirmationDialogTitle.setText(R.string.dialog_exercise_delete)
                    confirmationDialogAcceptBtn.setOnClickListener {
                        dialogCallbackDelete.onClickAcceptDelete(itemId)
                        dismissAllowingStateLoss()
                    }
                }

                else -> {
                    confirmationDialogTitle.setText(R.string.dialog_serie_delete)
                    confirmationDialogAcceptBtn.setOnClickListener {
                        dialogCallbackDelete.onClickAcceptDelete(itemId)
                        dismissAllowingStateLoss()
                    }
                }
            }
            confirmationDialogCancelBtn.setOnClickListener {
                dismissAllowingStateLoss()
            }
        }
    }
}