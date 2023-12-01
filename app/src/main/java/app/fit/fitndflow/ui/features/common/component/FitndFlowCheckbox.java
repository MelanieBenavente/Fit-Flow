package app.fit.fitndflow.ui.features.common.component;



import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.content.res.ResourcesCompat;

import com.fit.fitndflow.R;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class FitndFlowCheckbox extends AppCompatCheckBox {

    private static final int HINT_COLOR = R.color.text_color_normal;
    private static final int FONT = R.font.roboto;
    private static final int SIZE = 13;
    private static Context context;
    private boolean hasError;

    public FitndFlowCheckbox(@NonNull Context context) {
        super(context);
        this.context = context;
        init();
    }

    public FitndFlowCheckbox(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public FitndFlowCheckbox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init(){
        this.setTextColor(getResources().getColor(HINT_COLOR));
        this.setTextSize(SIZE);
        this.setPadding(34,0,0,0);
        try { this.setTypeface(ResourcesCompat.getFont(getContext(), FONT)); } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
        }
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasError){
                    goneError();
                }else{
                    setCheckableDrawable();
                }
            }
        });
    }

    private void goneError(){
        this.setButtonDrawable(getResources().getDrawable(R.drawable.checkbox_check));
        this.setTextColor(getResources().getColor(HINT_COLOR));
        hasError = false;
    }

    private void setCheckableDrawable(){
        this.setButtonDrawable(getResources().getDrawable(R.drawable.component_checkbox_draw));
    }
}
