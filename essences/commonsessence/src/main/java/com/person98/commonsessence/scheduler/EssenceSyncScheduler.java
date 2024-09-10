package com.person98.commonsessence.scheduler;

import com.person98.commonsessence.util.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.TimeUnit;

public class EssenceSyncScheduler implements EssenceScheduler {

    private final Plugin plugin;

    public EssenceSyncScheduler(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public EssenceTask run(Runnable runnable) {
        BukkitTask task = Bukkit.getScheduler().runTask(plugin, runnable);
        return new SyncEssenceTask(task);
    }

    @Override
    public EssenceTask runLater(Runnable runnable, long delay, TimeUnit unit) {
        long delayTicks = TimeUtils.toTicks(delay, unit);
        BukkitTask task = Bukkit.getScheduler().runTaskLater(plugin, runnable, delayTicks);
        return new SyncEssenceTask(task);
    }

    @Override
    public EssenceTask runRepeating(Runnable runnable, long initialDelay, long interval, TimeUnit unit) {
        long initialDelayTicks = TimeUtils.toTicks(initialDelay, unit);
        long intervalTicks = TimeUtils.toTicks(interval, unit);
        BukkitTask task = Bukkit.getScheduler().runTaskTimer(plugin, runnable, initialDelayTicks, intervalTicks);
        return new SyncEssenceTask(task);
    }
}
