package app.fit.fitndflow.domain.usecase;

import static app.fit.fitndflow.domain.Utils.convertToStringInLanguages;

import android.content.Context;

import java.util.List;

import app.fit.fitndflow.data.common.SharedPrefs;
import app.fit.fitndflow.domain.common.usecase.FitUseCase;
import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.model.CategoryModelInLanguages;
import app.fit.fitndflow.domain.repository.FitnFlowRepository;
import io.reactivex.Single;

public class ModifyCategoryUseCase extends FitUseCase<CategoryModelInLanguages, List<CategoryModel>> {
    public static final String SPANISH = "es";
    private FitnFlowRepository fitnFlowRepository;
    private Context context;

    public ModifyCategoryUseCase(Context context, String language, String nameCategory, int id, FitnFlowRepository fitnFlowRepository) {
        super(convertToCategoryModelInLanguages(language, nameCategory, id));
        this.context = context;
        this.fitnFlowRepository = fitnFlowRepository;
    }

    @Override
    public Single<List<CategoryModel>> buildUseCaseObservable() {
        return Single.create(emitter -> {
            try {
                String apiKey = SharedPrefs.getApikeyFromSharedPRefs(context);
                List<CategoryModel> response = fitnFlowRepository.modifyCategory(inputParams, apiKey);
                emitter.onSuccess(response);
            } catch (Exception exception) {
                emitter.onError(exception);
            }
        });
    }
    private static CategoryModelInLanguages convertToCategoryModelInLanguages(String language, String nameCategory, int id){
        CategoryModelInLanguages categoryModelInLanguages = new CategoryModelInLanguages(id, convertToStringInLanguages(language, nameCategory), "");
        return  categoryModelInLanguages;
    }
}
