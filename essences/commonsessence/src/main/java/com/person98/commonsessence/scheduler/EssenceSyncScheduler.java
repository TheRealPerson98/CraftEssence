package com.person98.commonsessence.scheduler;

import com.person98.commonsessence.util.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.TimeUnit;

public class EssenceSyncScheduler implements EssenceScheduler {
    private final Plugin plugin;

    public EssenceSyncScheduler(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run(Runnable runnable) {
        Bukkit.getScheduler().runTask(plugin, runnable);
    }

    @Override
    public void runLater(Runnable runnable, long delay, TimeUnit unit) {
        long delayTicks = TimeUtils.toTicks(delay, unit);
        Bukkit.getScheduler().runTaskLater(plugin, runnable, delayTicks);
    }

    @Override
    public void runRepeating(Runnable runnable, long initialDelay, long interval, TimeUnit unit) {
        long initialDelayTicks = TimeUtils.toTicks(initialDelay, unit);
        long intervalTicks = TimeUtils.toTicks(interval, unit);
        Bukkit.getScheduler().runTaskTimer(plugin, runnable, initialDelayTicks, intervalTicks);
    }
}
