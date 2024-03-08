package app.fit.fitndflow.domain.common.arq;

import io.reactivex.observers.DisposableSingleObserver;

public abstract class FitRxObserver<T> extends DisposableSingleObserver<T> {

    @Override
    public abstract void onSuccess(T t);

    @Override
    public abstract void onError(Throwable e);

}