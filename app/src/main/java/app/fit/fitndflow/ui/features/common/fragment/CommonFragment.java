package app.fit.fitndflow.ui.features.common.fragment;

import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import app.fit.fitndflow.ui.features.common.activity.CommonActivity;

public abstract class CommonFragment extends Fragment {

    public void nextFragment(Fragment fragment) {
        ((CommonActivity) getActivity()).nextFragment(fragment);
    }


}
