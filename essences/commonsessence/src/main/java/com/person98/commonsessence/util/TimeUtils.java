package com.person98.commonsessence.util;

import java.util.concurrent.TimeUnit;

public class TimeUtils {

    /**
     * Converts the given time to Minecraft ticks.
     * 1 tick = 50 milliseconds in Minecraft.
     *
     * @param time the time value
     * @param unit the time unit of the given time
     * @return the equivalent time in Minecraft ticks
     */
    public static long toTicks(long time, TimeUnit unit) {
        return unit.toMillis(time) / 50;  // Convert to Minecraft ticks
    }
}
