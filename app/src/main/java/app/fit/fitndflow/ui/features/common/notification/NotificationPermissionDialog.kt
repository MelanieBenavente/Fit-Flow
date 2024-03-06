package app.fit.fitndflow.ui.features.common.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import app.fit.fitndflow.ui.features.common.BorderType
import app.fit.fitndflow.ui.features.common.CommonDialogFragment
import com.fit.fitndflow.databinding.DialogNotificationPermissionBinding

class NotificationPermissionDialog : CommonDialogFragment() {
    companion object {
        @JvmField
        val TAG = "NotificationDialog"
    }
    private lateinit var binding: DialogNotificationPermissionBinding

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
                MyNotificationManager.requestPermission(requireActivity())
                dismissAllowingStateLoss()
            }
            closeButton.setOnClickListener { dismissAllowingStateLoss() }
        }
    }

    override fun getBorderType(): BorderType {
        return BorderType.NAKED
    }

    override fun getBinding(): ViewBinding {
        binding = DialogNotificationPermissionBinding.inflate(layoutInflater)
        return binding
    }
}