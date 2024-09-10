package com.person98.commonsessence.scheduler;

import org.bukkit.scheduler.BukkitTask;

public class SyncEssenceTask implements EssenceTask {

    private final BukkitTask task;

    public SyncEssenceTask(BukkitTask task) {
        this.task = task;
    }

    @Override
    public void cancel() {
        if (!task.isCancelled()) {
            task.cancel();
        }
    }

    @Override
    public boolean isCancelled() {
        return task.isCancelled();
    }
}
