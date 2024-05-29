package app.fit.fitndflow.ui.features.common.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import app.fit.fitndflow.domain.usecase.SharedPrefsUseCase
import app.fit.fitndflow.ui.databinding.DialogNotificationConfigurationBinding
import app.fit.fitndflow.ui.features.common.BorderType
import app.fit.fitndflow.ui.features.common.CommonDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationConfigurationDialog : CommonDialogFragment() {
    @Inject
     lateinit var sharedPrefsUseCase: SharedPrefsUseCase

    companion object {
        @JvmField
        val TAG = "NotificationConfigurationDialog"
    }

    private lateinit var binding: DialogNotificationConfigurationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        initListeners()
        return view
    }

    private fun initListeners() {
        binding.apply {
            buttonPanel.setOnClickListener {
                MyNotificationManager.launchNotificationSettings(requireContext())
                dismissAllowingStateLoss()
            }
            closeButton.setOnClickListener {
                dismissAllowingStateLoss()
            }
            checkboxDontShowAgain.setOnCheckedChangeListener { buttonView, isChecked ->
                sharedPrefsUseCase.saveDontShowNotification(isChecked)
            }
        }
    }

    override fun getBinding(): ViewBinding {
        binding = DialogNotificationConfigurationBinding.inflate(layoutInflater)
        return binding
    }

    override fun getBorderType(): BorderType {
        return BorderType.NAKED
    }
}