package app.fit.fitndflow.ui.features.common

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.view.inputmethod.InputMethodManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import app.fit.fitndflow.ui.R
import com.fit.fitndflow.app.domain.common.models.StringInLanguagesModel
import com.fit.fitndflow.app.domain.common.utils.Utils.SPANISH

fun Activity.hideKeyBoard() {
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
}

fun StringInLanguagesModel.getTranslatedString(context: Context) =
    if ((context.getString(R.string.language) == SPANISH || this.english.isEmpty()) && this.spanish.isNotEmpty()) {
        this.spanish
    } else {
        this.english
    }

fun View.applyStatusBarPadding() {
    val initialPaddingTop = paddingTop
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, windowInsets ->
        val topInset = windowInsets.getInsets(
            WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.displayCutout()
        ).top
        view.setPadding(
            view.paddingLeft,
            initialPaddingTop + topInset,
            view.paddingRight,
            view.paddingBottom
        )
        windowInsets
    }
    ViewCompat.requestApplyInsets(this)
}

fun View.applyStatusBarMargin() {
    val initialLayoutParams = layoutParams as? MarginLayoutParams ?: return
    val initialTopMargin = initialLayoutParams.topMargin
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, windowInsets ->
        val topInset = windowInsets.getInsets(
            WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.displayCutout()
        ).top
        val params = view.layoutParams as? MarginLayoutParams
        params?.topMargin = initialTopMargin + topInset
        if (params != null) {
            view.layoutParams = params
        }
        windowInsets
    }
    ViewCompat.requestApplyInsets(this)
}

