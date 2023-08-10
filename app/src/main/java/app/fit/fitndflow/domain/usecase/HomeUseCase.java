package app.fit.fitndflow.domain.usecase;

import java.util.List;

import app.fit.fitndflow.data.dto.RazaApi;
import app.fit.fitndflow.domain.common.model.None;
import app.fit.fitndflow.domain.common.usecase.CommonUseCase;
import app.fit.fitndflow.domain.repository.FitnFlowRepository;
import io.reactivex.Observable;

public class HomeUseCase extends CommonUseCase<None, Observable<List<RazaApi>>, FitnFlowRepository> {

    public HomeUseCase(FitnFlowRepository commonRepo) {
        super(commonRepo);
    }

    @Override
    public Observable<List<RazaApi>> executeUseCase(None input) {
        return repository.requestRazaList();
    }
}
