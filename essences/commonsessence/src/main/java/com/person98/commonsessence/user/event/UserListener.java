package com.person98.commonsessence.user.event;

import com.person98.commonsessence.CommonsEssence;
import com.person98.commonsessence.event.Events;
import com.person98.commonsessence.user.UserManager;
import com.person98.craftessence.util.logging.EssenceLogger;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class UserListener {
    private final CommonsEssence essence;
    
    
    public UserListener(CommonsEssence essence) {
        this.essence = essence;
        
        this.userEvents();
    }

    private void userEvents() {
        Events.hook(PlayerJoinEvent.class, event -> {
            UserManager.markUserOnline(event.getPlayer().getUniqueId());
            EssenceLogger.Info("Player " + event.getPlayer().getName() + " is marked as online.");
        }).bindTo(essence);

        // Hook into PlayerQuitEvent to mark the player as offline
        Events.hook(PlayerQuitEvent.class, event -> {
            UserManager.markUserOffline(event.getPlayer().getUniqueId());
            EssenceLogger.Info("Player " + event.getPlayer().getName() + " is marked as offline.");
        }).bindTo(essence);
    }
}
