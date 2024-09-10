package com.person98.commonsessence.scheduler;

import org.bukkit.plugin.Plugin;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

public class EssenceSchedulers {

    private static ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE;

    private static EssenceSyncScheduler syncScheduler;
    private static EssenceAsyncScheduler asyncScheduler;

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
            thread.setDaemon(true);
            thread.setName("EssenceScheduler-Thread");
            return thread;
        }
    }
}
