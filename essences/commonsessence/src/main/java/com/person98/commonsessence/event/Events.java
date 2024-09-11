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

/**
 * The Events class provides methods to hook into events, bind event listeners to an Essence object, and call events.
 */
public class Events {
    /**
     * The eventHooks variable is a map that stores event hooks.
     * The keys of the map are subclasses of the Event class.
     * The values of the map are instances of the EventListenerWrapper class.
     * The eventHooks map is used to hook into events, bind event listeners to an Essence object, and call events.
     */
    private static final Map<Class<? extends Event>, EventListenerWrapper<? extends Event>> eventHooks = new HashMap<>();
    /**
     * The eventHolder variable is an instance of the EventHolder class.
     * It is used to store and manage registered events and their corresponding handlers.
     * The registeredEvents map stored in the EventHolder class maps event classes to their corresponding essence objects.
     * The eventHolder variable is declared as private static final, which means it is a constant and cannot be changed or reassigned.
     */
    private static final EventHolder eventHolder = new EventHolder();

    /**
     * Hook into an event class with a custom event handler.
     *
     * @param <T>         The type of event to hook into.
     * @param eventClass  The class of the event to hook into.
     * @param handler     The event handler to be called when the event occurs.
     * @return An instance of EventListenerWrapper, which allows for further customization and binding to an Essence object.
     */
    public static <T extends Event> EventListenerWrapper<T> hook(Class<T> eventClass, Consumer<T> handler) {
        return hookPriorityToEvent(eventClass, EventPriority.NORMAL, handler);
    }

    /**
     * Creates an event listener wrapper and associates it with the provided event class, priority, and event handler.
     *
     * @param <T>         The type of event.
     * @param eventClass  The class of the event.
     * @param priority    The priority of the event listener.
     * @param handler     The event handler.
     * @return The created EventListenerWrapper object.
     */
    public static <T extends Event> EventListenerWrapper<T> hookPriorityToEvent(Class<T> eventClass, EventPriority priority, Consumer<T> handler) {
        EventListenerWrapper<T> wrapper = new EventListenerWrapper<>(eventClass, handler, priority);
        eventHooks.put(eventClass, wrapper);
        return wrapper;
    }

    /**
     * Calls the provided event and triggers any registered event handlers.
     *
     * @param event the event to be called
     * @param <T>   the type of event
     * @return the same event instance after calling it
     */
    public static <T extends Event> T call(T event) {
        Bukkit.getPluginManager().callEvent(event);
        return event;
    }

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
                        false
                );

                // Store the event registration in EventHolder for management
                eventHolder.addEvent(eventClass, essence);
            } else {
                EssenceLogger.Info("Failed to bind event to Essence, Essence is null.");
            }
        }
    }
}
