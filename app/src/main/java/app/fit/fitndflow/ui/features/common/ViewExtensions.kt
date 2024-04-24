package app.fit.fitndflow.ui.features.common

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import app.fit.fitndflow.domain.Utils.SPANISH
import app.fit.fitndflow.domain.model.StringInLanguagesModel
import com.fit.fitndflow.R

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

