package com.person98.commonsessence.event;

import com.person98.craftessence.core.Essence;
import org.bukkit.event.Event;

import java.util.HashMap;
import java.util.Map;

public class EventHolder {

    private final Map<Class<? extends Event>, Essence> registeredEvents = new HashMap<>();

    // Add an event to the holder
    public <T extends Event> void addEvent(Class<T> eventClass, Essence essence) {
        registeredEvents.put(eventClass, essence);
    }

    // Get the Essence handling a particular event
    public <T extends Event> Essence getHandlerForEvent(Class<T> eventClass) {
        return registeredEvents.get(eventClass);
    }

    // Get all registered events
    public Map<Class<? extends Event>, Essence> getRegisteredEvents() {
        return registeredEvents;
    }
}
