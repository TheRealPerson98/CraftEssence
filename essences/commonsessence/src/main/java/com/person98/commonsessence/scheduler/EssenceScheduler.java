package com.person98.commonsessence.scheduler;

import java.util.concurrent.TimeUnit;

public interface EssenceScheduler {
    void run(Runnable runnable);

    void runLater(Runnable runnable, long delay, TimeUnit unit);

    void runRepeating(Runnable runnable, long initialDelay, long interval, TimeUnit unit);
}