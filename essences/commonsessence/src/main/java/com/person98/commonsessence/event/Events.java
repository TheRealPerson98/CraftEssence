package com.person98.commonsessence.event;

import com.person98.craftessence.core.Essence;
import com.person98.craftessence.util.logging.EssenceLogger;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Events {
    private static final Map<Class<? extends Event>, EventListenerWrapper<? extends Event>> eventHooks = new HashMap<>();
    private static final EventHolder eventHolder = new EventHolder();

    // Generic method to hook an event to a class that implements Essence
    public static <T extends Event> EventListenerWrapper<T> hook(Class<T> eventClass, Consumer<T> handler) {
        return hookPriorityToEvent(eventClass, EventPriority.NORMAL, handler);
    }

    // Hook event with a specified priority
    public static <T extends Event> EventListenerWrapper<T> hookPriorityToEvent(Class<T> eventClass, EventPriority priority, Consumer<T> handler) {
        EventListenerWrapper<T> wrapper = new EventListenerWrapper<>(eventClass, handler, priority);
        eventHooks.put(eventClass, wrapper);
        return wrapper;
    }

    // Call an event programmatically
    public static <T extends Event> T call(T event) {
        Bukkit.getPluginManager().callEvent(event);
        return event;
    }

    // Store event hooks
    public static class EventListenerWrapper<T extends Event> {
        private final Class<T> eventClass;
        private final Consumer<T> handler;
        private final EventPriority priority;

        public EventListenerWrapper(Class<T> eventClass, Consumer<T> handler, EventPriority priority) {
            this.eventClass = eventClass;
            this.handler = handler;
            this.priority = priority;
        }

        public void bindTo(Essence essence) {
            if (essence != null) {
                Bukkit.getPluginManager().registerEvent(
                        eventClass,
                        new Listener() {},  // Empty listener as we use EventExecutor
                        priority,
                        new EventExecutor() {
                            @Override
                            public void execute(Listener listener, Event event) {
                                if (eventClass.isInstance(event)) {
                                    handler.accept(eventClass.cast(event));
                                }
                            }
                        },
                        Bukkit.getPluginManager().getPlugin("CraftEssence"),  // Your plugin's instance
                        false // Ignore cancelled events by default
                );

                // Store the event registration in EventHolder for management
                eventHolder.addEvent(eventClass, essence);
            } else {
                EssenceLogger.Info("Failed to bind event to Essence, Essence is null.");
            }
        }
    }
}
