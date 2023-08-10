package app.fit.fitndflow.domain.common.executor;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * MainThread (UI Thread) implementation based on a {@link Scheduler}
 * which will execute actions on the Android UI thread.
 */
public class UIThread implements UIScheduler {

  public UIThread() {}

  @Override
  public Scheduler getScheduler() {
    return AndroidSchedulers.mainThread();
  }

}
