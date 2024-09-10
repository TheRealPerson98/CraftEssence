package com.person98.commonsessence.scheduler;

import org.bukkit.Bukkit;

import java.util.concurrent.*;

public class EssenceAsyncScheduler implements EssenceScheduler {

    private final ScheduledExecutorService executorService;

    public EssenceAsyncScheduler(ScheduledExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public EssenceTask run(Runnable runnable) {
        Future<?> future = CompletableFuture.runAsync(() -> {
            try {
                runnable.run();
            } catch (Exception e) {
                Bukkit.getLogger().severe("Exception in async task: " + e.getMessage());
            }
        }, executorService);
        return new CancellableEssenceTask(future);
    }

    @Override
    public EssenceTask runLater(Runnable runnable, long delay, TimeUnit unit) {
        ScheduledFuture<?> future = executorService.schedule(() -> {
            try {
                runnable.run();
            } catch (Exception e) {
                Bukkit.getLogger().severe("Exception in scheduled task: " + e.getMessage());
            }
        }, delay, unit);
        return new CancellableEssenceTask(future);
    }

    @Override
    public EssenceTask runRepeating(Runnable runnable, long initialDelay, long interval, TimeUnit unit) {
        ScheduledFuture<?> future = executorService.scheduleAtFixedRate(() -> {
            try {
                runnable.run();
            } catch (Exception e) {
                Bukkit.getLogger().severe("Exception in repeating task: " + e.getMessage());
            }
        }, initialDelay, interval, unit);
        return new CancellableEssenceTask(future);
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
