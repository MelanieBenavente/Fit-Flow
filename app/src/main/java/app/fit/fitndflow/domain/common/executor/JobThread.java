package app.fit.fitndflow.domain.common.executor;


import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * JobScheduler implementation based on a {@link Scheduler}
 * which will execute tasks in "an unbounded thread pool".
 */
public class JobThread implements JobScheduler {

    public JobThread() {}

    @Override
    public Scheduler getScheduler() {
        return Schedulers.io();
    }

}
