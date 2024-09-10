package com.person98.commonsessence.scheduler;

import java.util.concurrent.TimeUnit;

public interface EssenceScheduler {
    EssenceTask run(Runnable runnable);

    EssenceTask runLater(Runnable runnable, long delay, TimeUnit unit);

    EssenceTask runRepeating(Runnable runnable, long initialDelay, long interval, TimeUnit unit);
}
