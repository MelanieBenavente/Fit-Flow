package app.fit.fitndflow.ui.features.training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.viewbinding.ViewBinding
import app.fit.fitndflow.ui.features.common.BorderType
import app.fit.fitndflow.ui.features.common.CommonDialogFragment
import com.fit.fitndflow.R
import com.fit.fitndflow.databinding.DialogRecordSerieBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecordSerieDialog : CommonDialogFragment() {

    companion object {
        val TAG = "RecordDialog"
        fun newInstance(): RecordSerieDialog {
            return RecordSerieDialog()
        }
    }

    private lateinit var binding: DialogRecordSerieBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = super.onCreateView(inflater, container, savedInstanceState)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ImageDialogComponent(
                    { dismissAllowingStateLoss() },
                    stringResource(R.string.congrats),
                    R.drawable.record_image,
                    stringResource(R.string.new_challenge),
                    stringResource(R.string.dialog_btn_close)
                )
            }
        }
        return mView
    }

    override fun getBorderType(): BorderType {
        return BorderType.NAKED
    }

    override fun getBinding(): ViewBinding {
        binding = DialogRecordSerieBinding.inflate(layoutInflater)
        return binding
    }
}