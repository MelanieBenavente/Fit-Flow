package app.fit.fitndflow.domain.common.usecase;


import app.fit.fitndflow.domain.common.arq.ParamsUsecase;
import app.fit.fitndflow.domain.common.executor.JobScheduler;
import app.fit.fitndflow.domain.common.executor.JobThread;
import app.fit.fitndflow.domain.common.executor.UIScheduler;
import app.fit.fitndflow.domain.common.executor.UIThread;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;


public abstract class FitUseCase<INPUT, OUTPUT> {
    protected INPUT inputParams;
    private final UIScheduler uiScheduler;
    private final JobScheduler jobScheduler;
    private ParamsUsecase params;
    public FitUseCase(INPUT inputParams) {
        this.uiScheduler = new UIThread();
        this.jobScheduler = new JobThread();
        this.inputParams = inputParams;
    }

    public FitUseCase() {
        this.uiScheduler = new UIThread();
        this.jobScheduler = new JobThread();
    }

    public abstract Single<OUTPUT> buildUseCaseObservable();

    public Disposable execute(DisposableSingleObserver<OUTPUT> observer) {
        final Single<OUTPUT> observable = this.buildUseCaseObservable()
                .observeOn(uiScheduler.getScheduler())
                .subscribeOn(jobScheduler.getScheduler());
        return observable.subscribeWith(observer);
    }

}
