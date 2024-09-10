package com.person98.commonsessence.scheduler;

import java.util.concurrent.Future;

public class CancellableEssenceTask implements EssenceTask {

    private final Future<?> future;

    public CancellableEssenceTask(Future<?> future) {
        this.future = future;
    }

    @Override
    public void cancel() {
        future.cancel(true);
    }

    @Override
    public boolean isCancelled() {
        return future.isCancelled();
    }
}
