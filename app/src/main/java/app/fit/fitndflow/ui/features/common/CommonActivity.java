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
    private RelativeLayout savedContainer;
    private RelativeLayout errorContainer;
    private FrameLayout loadingLottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResLayout());
        bindViews();
    }

    private void bindViews() {
        savedContainer = findViewById(R.id.savedContainer);
        errorContainer = findViewById(R.id.errorContainer);
        loadingLottie = findViewById(R.id.lottieContainer);
    }

    public abstract @LayoutRes int getResLayout();

    public void showBlockError(){
        addFragment(new BlockErrorFragment());
    }
    public void showSavedSlideContainer(){
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
                savedContainer.setVisibility(View.VISIBLE);
                savedContainer.startAnimation(slideDown);

                // Ocultar el contenedor después de 3 segundos
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        savedContainer.startAnimation(slideUp);
                        savedContainer.setVisibility(View.INVISIBLE);
                    }
                }, 2000);
            }
        }, 1000); // Esperar 1 segundo antes de mostrar el contenedor
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
                errorContainer.setVisibility(View.VISIBLE);
                errorContainer.startAnimation(slideDown);

                // Ocultar el contenedor después de 3 segundos
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        errorContainer.startAnimation(slideUp);
                        errorContainer.setVisibility(View.INVISIBLE);
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

    public void addFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.MainActContainer, fragment)
                .addToBackStack(fragment.getTag())
                .commit();
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.MainActContainer, fragment)
                .commit();
    }

}
