package app.fit.fitndflow.domain.common.usecase;


import app.fit.fitndflow.domain.common.arq.ParamsUsecase;
import app.fit.fitndflow.domain.common.executor.JobScheduler;
import app.fit.fitndflow.domain.common.executor.JobThread;
import app.fit.fitndflow.domain.common.executor.UIScheduler;
import app.fit.fitndflow.domain.common.executor.UIThread;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;


public abstract class FitUseCase<INPUT, Observer> {
    protected INPUT inputParams;
    private final UIScheduler uiScheduler;
    private final JobScheduler jobScheduler;
    private ParamsUsecase params;
    public FitUseCase(INPUT inputParams) {
        this.uiScheduler = new UIThread();
        this.jobScheduler = new JobThread();
        this.inputParams = inputParams;
    }

    public abstract Single<Observer> buildUseCaseObservable();

    public Disposable execute(DisposableSingleObserver<Observer> observer) {
        final Single<Observer> observable = this.buildUseCaseObservable()
                .observeOn(uiScheduler.getScheduler())
                .subscribeOn(jobScheduler.getScheduler());
        return observable.subscribeWith(observer);
    }

}
