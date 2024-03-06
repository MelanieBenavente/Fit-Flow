package app.fit.fitndflow.ui.features.common.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import app.fit.fitndflow.data.common.SharedPrefs
import app.fit.fitndflow.ui.features.common.CommonDialogFragment
import com.fit.fitndflow.databinding.DialogNotificationConfigurationBinding

class NotificationConfigurationDialog : CommonDialogFragment() {

    companion object {
        @JvmField
        val TAG = "NotificationConfigurationDialog"
    }
    private lateinit var binding: DialogNotificationConfigurationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        initListeners()
        return view
    }

    private fun initListeners(){
        binding.apply {
            buttonPanel.setOnClickListener {
                MyNotificationManager.launchNotificationSettings(requireContext())
                dismissAllowingStateLoss()
            }
            closeButton.setOnClickListener { dismissAllowingStateLoss()
            }
            checkboxDontShowAgain.setOnCheckedChangeListener { buttonView, isChecked ->
                SharedPrefs.get(requireContext()).saveDontShowNotification(isChecked)
            }
        }
    }
    override fun getBinding(): ViewBinding {
        binding = DialogNotificationConfigurationBinding.inflate(layoutInflater)
        return binding
    }
    override fun getBorderType(): Int {
        return NAKED
    }
}