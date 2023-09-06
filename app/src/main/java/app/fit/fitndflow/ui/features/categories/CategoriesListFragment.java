package app.fit.fitndflow.ui.features.categories;

import static app.fit.fitndflow.ui.features.categories.AddCategoryFragment.KEY_CATEGORY;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fit.fitndflow.databinding.FragmentCategoriesListBinding;

import java.util.ArrayList;
import java.util.List;

import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.ui.features.common.CommonFragment;
import app.fit.fitndflow.ui.features.excercises.ExcerciseListFragment;

public class CategoriesListFragment extends CommonFragment implements CategoryAdapterCallback {

    private List<CategoryModel> categoryList = new ArrayList<>();
    private FragmentCategoriesListBinding binding;
    private CategoriesAndExcercisesViewModel categoriesAndExcercisesViewModel;
    private CategoriesAdapter categoriesAdapter;

    @Override
    protected Class getViewModelClass() {
        return CategoriesAndExcercisesViewModel.class;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCategoriesListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        super.onCreateView(inflater, container, savedInstanceState);
        instantiateCategoriesAdapter();
        setViewModelObservers();
        setOnClickListeners();
        categoriesAndExcercisesViewModel.requestCategoriesFromModel(requireContext());
        return view;
    }

    private void setViewModelObservers() {
        categoriesAndExcercisesViewModel = ViewModelProviders.of(getActivity()).get(CategoriesAndExcercisesViewModel.class);

        //observing RazaList
        final Observer<List<CategoryModel>> observer = new Observer<List<CategoryModel>>() {
            @Override
            public void onChanged(List<CategoryModel> categories) {
                printCategories(categories);
            }
        };
        //Observamos al listado del ViewModel y ejecutamos las acciones indicadas antes en el observer
        categoriesAndExcercisesViewModel.getMutableCategory().observe(getActivity(), observer);

        //observing error
        final Observer<Boolean> errorObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if (isError) {
                    printError();
                }
            }
        };
        categoriesAndExcercisesViewModel.getMutableError().observe(getActivity(), errorObserver);

        final Observer<Boolean> observerLoading = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) {
                    binding.loading.setVisibility(View.VISIBLE);
                } else {
                    binding.loading.setVisibility(View.GONE);
                }
            }
        };
        categoriesAndExcercisesViewModel.getIsLoading().observe(getActivity(), observerLoading);
    }

    private void printCategories(List<CategoryModel> listRecived) {
        categoryList = listRecived;
        categoriesAdapter.setCategoryList(categoryList);
        categoriesAdapter.notifyDataSetChanged();
    }

    private void printError() {

        Toast.makeText(this.getContext(), "ERROR", Toast.LENGTH_SHORT).show();

    }

    private void instantiateCategoriesAdapter() {
        categoriesAdapter = new CategoriesAdapter(this);
        binding.recyclerCategories.setHasFixedSize(true);
        binding.recyclerCategories.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.recyclerCategories.setAdapter(categoriesAdapter);
    }

    private void setOnClickListeners() {
        binding.floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextFragment(new AddCategoryFragment());
            }
        });
    }

    @Override
    public void showExcercises(CategoryModel category) {
        categoriesAndExcercisesViewModel.getActualCategory().setValue(category);
        if(category.getExcerciseList() != null && !category.getExcerciseList().isEmpty()){
            nextFragment(new ExcerciseListFragment());
        } else {
            AddCategoryFragment addCategoryFragment = new AddCategoryFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(KEY_CATEGORY, category);
            addCategoryFragment.setArguments(bundle);
            nextFragment(addCategoryFragment);
        }

    }
}
