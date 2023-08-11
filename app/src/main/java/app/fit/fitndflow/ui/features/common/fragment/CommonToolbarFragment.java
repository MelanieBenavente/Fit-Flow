package app.fit.fitndflow.ui.features.common.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModel;

import com.fit.fitndflow.R;





public abstract class CommonToolbarFragment<V extends ViewModel> extends CommonFragment<V> {

    protected abstract Toolbar getToolbar();

    protected abstract int rMenu();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).setSupportActionBar(getToolbar());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(rMenu(), menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.option1:
                Toast.makeText(getContext(), "Menu", Toast.LENGTH_SHORT).show();
                break;
            case R.id.option2:
                Toast.makeText(getContext(), "Añadir entrenamiento", Toast.LENGTH_SHORT).show();
                break;
            case R.id.option3:
                Toast.makeText(getContext(), "Calendario", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
