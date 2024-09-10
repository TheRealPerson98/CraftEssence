package com.person98.commonsessence.scheduler;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.*;

public class EssenceAsyncScheduler implements EssenceScheduler {

    private final ScheduledExecutorService executorService;

    public EssenceAsyncScheduler(ScheduledExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void run(Runnable runnable) {
        CompletableFuture.runAsync(() -> {
            try {
                runnable.run();
            } catch (Exception e) {
                // Log the error with plugin's logger (could use your PLogger utility)
                Bukkit.getLogger().severe("Exception in async task: " + e.getMessage());
            }
        }, executorService);
    }

    @Override
    public void runLater(Runnable runnable, long delay, TimeUnit unit) {
        executorService.schedule(() -> {
            try {
                runnable.run();
            } catch (Exception e) {
                Bukkit.getLogger().severe("Exception in scheduled task: " + e.getMessage());
            }
        }, delay, unit);
    }

    @Override
    public void runRepeating(Runnable runnable, long initialDelay, long interval, TimeUnit unit) {
        executorService.scheduleAtFixedRate(() -> {
            try {
                runnable.run();
            } catch (Exception e) {
                Bukkit.getLogger().severe("Exception in repeating task: " + e.getMessage());
            }
        }, initialDelay, interval, unit);
    }

    public void shutdown() {
        executorService.shutdown();  // Ensure graceful shutdown when stopping the plugin
    }
}
