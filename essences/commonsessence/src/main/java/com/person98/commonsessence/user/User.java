package com.person98.commonsessence.user;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.UUID;

public class User {

    private final UUID uuid;
    private boolean isOnline;

    public User(UUID uuid) {
        this.uuid = uuid;
        this.isOnline = false;
    }

    // Mark the user as online
    public void setOnline() {
        this.isOnline = true;
    }

    // Mark the user as offline
    public void setOffline() {
        this.isOnline = false;
    }

    // Check if the user is online
    public boolean isOnline() {
        return this.isOnline;
    }

    // Send a message to the user (if they're online)
    public void sendMessage(String message) {
        Player player = Bukkit.getPlayer(this.uuid);
        if (player != null && player.isOnline()) {
            player.sendMessage(message);
        }
    }

    public UUID getUuid() {
        return uuid;
    }
}
