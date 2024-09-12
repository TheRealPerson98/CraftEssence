package com.person98.commonsessence.user;

import com.person98.commonsessence.user.display.IDisplayable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Represents a user in the system.
 *
 * <p>
 * The User class contains information about a user, such as their unique identifier (UUID),
 * online status, and associated player object. This class provides methods to set the user's
 * online status, retrieve their player object, and check their online status. The user can
 * also be displayed through the show method if they are online and their player object exists.
 * </p>
 *
 * @see IDisplayable
 */
public class User {

    /**
     * Represents a universally unique identifier (UUID) for a user.
     *
     * <p>
     * The UUID class represents a UUID, which is a 128-bit value. A UUID can be used
     * to identify users in a system or to uniquely identify objects or entities in a
     * distributed system. The UUIDs are guaranteed to be unique across all systems
     * and all time, even if the UUIDs are generated on different machines at
     * different times.
     * </p>
     *
     * @see User
     * @see IDisplayable
     */
    private final UUID uuid;
    /**
     * Represents the online status of a user.
     * <p>
     * The value of this variable indicates whether the user is currently online or not.
     * <p>
     * When the user goes online, the value of this variable is set to `true`.
     * When the user goes offline, the value of this variable is set to `false`.
     * <p>
     * This variable is used in the `User` class to track the online status of a user.
     * It is updated by the `setOnline()` and `setOffline()` methods of the `User` class.
     * <p>
     * The value of this variable can be retrieved using the `isOnline()` method of the `User` class.
     *
     * @see User
     */
    private boolean isOnline;
    /**
     * The player associated with a user.
     */
    private Player player;

    /**
     * Creates a new User object with the specified UUID.
     *
     * @param uuid the unique identifier for the user
     */
    public User(UUID uuid) {
        this.uuid = uuid;
        this.isOnline = false;
    }

    /**
     * Sets the player's online status to true and gets the corresponding Player object.
     * <p>
     * This method sets the player's online status to true by updating the 'isOnline' field.
     * Additionally, it retrieves the Player object associated with the player's UUID using
     * the Bukkit.getPlayer(UUID uuid) method and assigns it to the 'player' field.
     * <p>
     * Note: The 'uuid' field must be set before calling this method. Otherwise, the 'player' field
     * will remain null.
     */
    public void setOnline() {
        this.isOnline = true;
        this.player = Bukkit.getPlayer(this.uuid);
    }

    /**
     *
     */
    public void setOffline() {
        this.isOnline = false;
        this.player = null; // Clear cached player when offline
    }

    /**
     * Returns the online status of the user.
     *
     * @return {@code true} if the user is online, {@code false} otherwise
     */
    public boolean isOnline() {
        return this.isOnline;
    }

    /**
     *
     */
    public void show(IDisplayable displayable) {
        if (this.isOnline && this.player != null) {
            displayable.display(this);
        }
    }

    /**
     * Gets the player associated with this instance.
     *
     * @return The player object.
     */
    public Player getPlayer() {
        if (this.player == null || !this.player.isOnline()) {
            this.player = Bukkit.getPlayer(this.uuid);
        }
        return this.player;
    }

    /**
     * Retrieves the UUID associated with the current instance.
     *
     * @return The UUID associated with the current instance.
     */
    public UUID getUuid() {
        return uuid;
    }
}
