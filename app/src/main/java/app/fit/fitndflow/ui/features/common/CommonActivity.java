package app.fit.fitndflow.ui.features.common;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.fit.fitndflow.R;


public abstract class CommonActivity extends AppCompatActivity {
    private RelativeLayout container;
    private FrameLayout loadingLottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResLayout());
        bindViews();
    }

    private void bindViews() {

        container = findViewById(R.id.errorContainer);
        loadingLottie = findViewById(R.id.lottieContainer);
    }

    public abstract @LayoutRes int getResLayout();

    public void showBlockError(){
        nextFragment(new BlockErrorFragment());
    }

    public void showErrorSlideContainer() {
        final Animation slideDown = new TranslateAnimation(0, 0, -1000, 0);
        slideDown.setDuration(1000); // Duración de la animación en milisegundos
        slideDown.setFillAfter(true);

        final Animation slideUp = new TranslateAnimation(0, 0, 0, -1000);
        slideUp.setDuration(1000); // Duración de la animación en milisegundos
        slideUp.setFillAfter(true);

        final Handler handler = new Handler();

        // Mostrar el contenedor después de 1 segundo
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                container.setVisibility(View.VISIBLE);
                container.startAnimation(slideDown);

                // Ocultar el contenedor después de 3 segundos
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        container.startAnimation(slideUp);
                        container.setVisibility(View.INVISIBLE);
                    }
                }, 2000);
            }
        }, 1000); // Esperar 1 segundo antes de mostrar el contenedor
    }

    public void showLoadingFromActivity() {
        loadingLottie.setVisibility(View.VISIBLE);
    }

    public void hideLoadingFromActivity() {
        loadingLottie.setVisibility(View.INVISIBLE);
    }

    public void nextFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.MainActContainer, fragment)
                .addToBackStack(fragment.getTag())
                .commit();
    }

}
