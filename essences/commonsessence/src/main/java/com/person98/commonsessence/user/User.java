package com.person98.commonsessence.user;

import com.person98.commonsessence.user.display.IDisplayable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.UUID;

public class User {

    private final UUID uuid;
    private boolean isOnline;
    private Player player;

    public User(UUID uuid) {
        this.uuid = uuid;
        this.isOnline = false;
    }

    // Mark the user as online
    public void setOnline() {
        this.isOnline = true;
        this.player = Bukkit.getPlayer(this.uuid);
    }

    // Mark the user as offline
    public void setOffline() {
        this.isOnline = false;
        this.player = null; // Clear cached player when offline
    }

    // Check if the user is online
    public boolean isOnline() {
        return this.isOnline;
    }

    // Send a message to the user (if they're online)
    public void show(IDisplayable displayable) {
        if (this.isOnline && this.player != null) {
            displayable.display(this);
        }
    }

    // Get the Player object if the user is online
    public Player getPlayer() {
        if (this.player == null || !this.player.isOnline()) {
            this.player = Bukkit.getPlayer(this.uuid);
        }
        return this.player;
    }

    public UUID getUuid() {
        return uuid;
    }
}
