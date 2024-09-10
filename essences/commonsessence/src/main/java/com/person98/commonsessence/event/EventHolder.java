package com.person98.commonsessence.event;

import com.person98.craftessence.core.Essence;
import org.bukkit.event.Event;

import java.util.HashMap;
import java.util.Map;

/**
 * EventHolder class is used to store and manage registered events and their corresponding handlers.
 */
public class EventHolder {

    /**
     * This variable stores a mapping of event classes to their corresponding essence objects.
     * The registeredEvents map is used by the EventHolder class to store and manage registered events and their corresponding handlers.
     * The keys of the map are event classes, which are subclasses of the Event class.
     * The values of the map are essence objects, which represent the handlers for the corresponding events.
     */
    private final Map<Class<? extends Event>, Essence> registeredEvents = new HashMap<>();

    /**
     * Adds an event to the registered events map.
     *
     * @param <T>        The type of event to add.
     * @param eventClass The class of the event to add.
     * @param essence    The essence associated with the event.
     */
    public <T extends Event> void addEvent(Class<T> eventClass, Essence essence) {
        registeredEvents.put(eventClass, essence);
    }

    /**
     * Retrieves the handler for the specified event class.
     *
     * @param eventClass the class of the event for which the handler is retrieved
     * @param <T>        the type of event
     * @return the handler for the specified event class, or null if none is registered
     */
    public <T extends Event> Essence getHandlerForEvent(Class<T> eventClass) {
        return registeredEvents.get(eventClass);
    }

    /**
     * Returns a map of registered events and their corresponding handlers.
     *
     * @return A map containing registered events and their handlers. The keys are classes extending Event, and the values are instances of Essence.
     */
    public Map<Class<? extends Event>, Essence> getRegisteredEvents() {
        return registeredEvents;
    }
}
