package com.person98.commonsessence.scheduler;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

public class EssenceSchedulers {

    private static final int DEFAULT_POOL_SIZE = 4;  // Adjust based on server size or make configurable
    private static ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE;

    private static EssenceSyncScheduler syncScheduler;
    private static EssenceAsyncScheduler asyncScheduler;

    // Initialize schedulers with a plugin reference and customizable thread pool size
    public static void initialize(Plugin plugin, int poolSize) {
        if (SCHEDULED_EXECUTOR_SERVICE == null) {
            SCHEDULED_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(poolSize, new EssenceThreadFactory());
        }
        syncScheduler = new EssenceSyncScheduler(plugin);
        asyncScheduler = new EssenceAsyncScheduler(SCHEDULED_EXECUTOR_SERVICE);
    }

    public static EssenceScheduler sync() {
        return syncScheduler;
    }

    public static EssenceScheduler async() {
        return asyncScheduler;
    }

    public static void shutdown() {
        if (SCHEDULED_EXECUTOR_SERVICE != null && !SCHEDULED_EXECUTOR_SERVICE.isShutdown()) {
            SCHEDULED_EXECUTOR_SERVICE.shutdown();
        }
    }

    private static class EssenceThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setDaemon(true);  // Set to daemon to allow proper shutdown
            thread.setName("EssenceScheduler-Thread");
            return thread;
        }
    }
}
