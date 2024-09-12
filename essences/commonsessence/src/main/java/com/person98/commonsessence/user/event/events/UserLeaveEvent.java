package com.person98.commonsessence.user.event.events;

import com.person98.commonsessence.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UserLeaveEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final User user;

    public UserLeaveEvent(User user) {
        this.user = user;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public User getUser() {
        return user;
    }

    public Player getPlayer() {
        return user.getPlayer();
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
