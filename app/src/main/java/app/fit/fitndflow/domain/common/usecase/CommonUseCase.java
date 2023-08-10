package app.fit.fitndflow.domain.common.usecase;


import app.fit.fitndflow.domain.common.repository.CommonRepository;

public abstract class CommonUseCase<IN, OUT, REPO extends CommonRepository>{
    public REPO repository;

    public CommonUseCase(REPO commonRepo) {
        this.repository = commonRepo;
    }

    public abstract OUT executeUseCase(IN input);
}
