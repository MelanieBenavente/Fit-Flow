package app.fit.fitndflow.ui.features.common;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;

public class AccessibilityUtils {

    public static View.AccessibilityDelegate createAccesibilityDelegate(String text) {
        View.AccessibilityDelegate accessibilityDelegate = new View.AccessibilityDelegate() {
            @Override
            public void onInitializeAccessibilityNodeInfo(View v, AccessibilityNodeInfo info) {
                super.onInitializeAccessibilityNodeInfo(v, info);
                info.setText(text);
            }
        };

        return accessibilityDelegate;
    }

    public static TextWatcher createTextWatcher(AccessibilityInterface accessibilityInterface){
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                accessibilityInterface.initAccessibility();
            }
        };
        return textWatcher;
    }

}
