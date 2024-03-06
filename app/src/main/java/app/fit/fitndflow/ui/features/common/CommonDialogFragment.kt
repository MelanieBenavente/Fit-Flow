package app.fit.fitndflow.ui.features.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.fit.fitndflow.R

abstract class CommonDialogFragment : DialogFragment() {
    protected abstract fun getBorderType(): BorderType

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(dialog != null && dialog!!.window != null){
            when(getBorderType()){
                BorderType.NAKED -> dialog!!.window!!.setBackgroundDrawable(resources.getDrawable(R.drawable.shape_white_rounded_corners))

                BorderType.BORDERLINE -> dialog!!.window!!.setBackgroundDrawable(resources.getDrawable(R.drawable.shape_purple_rounded_corners))
            }
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }

        super.onCreateView(inflater, container, savedInstanceState)
        return getBinding().root
    }

    abstract fun getBinding(): ViewBinding
}

enum class BorderType {
    NAKED,
    BORDERLINE
}