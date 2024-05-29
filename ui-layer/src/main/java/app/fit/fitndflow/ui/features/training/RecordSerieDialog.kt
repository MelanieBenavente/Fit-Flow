package app.fit.fitndflow.ui.features.training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.viewbinding.ViewBinding
import app.fit.fitndflow.ui.R
import app.fit.fitndflow.ui.databinding.DialogRecordSerieBinding
import app.fit.fitndflow.ui.features.common.BorderType
import app.fit.fitndflow.ui.features.common.CommonDialogFragment

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
                    dismissDialog = { dismissAllowingStateLoss() },
                    dialogTitle = stringResource(R.string.congrats),
                    dialogImage = R.drawable.record_image,
                    dialogText = stringResource(R.string.new_challenge),
                    dialogCloseBtn = stringResource(R.string.dialog_btn_close),
                    animationResource = R.raw.party_animation
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