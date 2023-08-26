package app.fit.fitndflow.ui.features.categories;

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

import app.fit.fitndflow.domain.model.ItemModel;
import app.fit.fitndflow.ui.features.common.CommonFragment;

public class CategoriesListFragment extends CommonFragment {

    private List<ItemModel> categoryList = new ArrayList<>();
    private FragmentCategoriesListBinding binding;
    private CategoriesViewModel categoriesViewModel;
    private CategoriesAdapter categoriesAdapter;

    @Override
    protected Class getViewModelClass() {
        return CategoriesViewModel.class;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCategoriesListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        super.onCreateView(inflater, container, savedInstanceState);
        instantiateAdapter();
        setViewModelObservers();
        setOnClickListeners();
        categoriesViewModel.requestCategoriesFromModel(requireContext());
        return view;
    }

    private void setViewModelObservers() {
        categoriesViewModel = ViewModelProviders.of(getActivity()).get(CategoriesViewModel.class);

        //observing RazaList
        final Observer<List<ItemModel>> observer = new Observer<List<ItemModel>>() {
            @Override
            public void onChanged(List<ItemModel> categories) {
                printCategories(categories);
            }
        };
        //Observamos al listado del ViewModel y ejecutamos las acciones indicadas antes en el observer
        categoriesViewModel.getMutableCategory().observe(getActivity(), observer);

        //observing error
        final Observer<Boolean> errorObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if(isError) {
                    printError();
                }
            }
        };
        categoriesViewModel.getMutableError().observe(getActivity(), errorObserver);

        final Observer<Boolean> observerLoading = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if(isLoading) {
                    binding.loading.setVisibility(View.VISIBLE);
                } else {
                    binding.loading.setVisibility(View.GONE);
                }
            }
        };
        categoriesViewModel.getIsLoading().observe(getActivity(), observerLoading);
    }

    private void printCategories(List<ItemModel> listRecived){
        categoryList = listRecived;
        categoriesAdapter.setCategoryList(categoryList);
        categoriesAdapter.notifyDataSetChanged();
    }
    private void printError(){

        Toast.makeText(this.getContext(), "ERROR", Toast.LENGTH_SHORT).show();

    }

    private void instantiateAdapter(){
        categoriesAdapter = new CategoriesAdapter();
        binding.recyclerCategories.setHasFixedSize(true);
        binding.recyclerCategories.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.recyclerCategories.setAdapter(categoriesAdapter);
    }

    private void setOnClickListeners(){
        binding.floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextFragment(new AddCategoryFragment());
            }
        });
    }
}
