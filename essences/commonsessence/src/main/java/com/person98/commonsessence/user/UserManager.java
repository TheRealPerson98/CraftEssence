package com.person98.commonsessence.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The UserManager class is responsible for managing user data and state.
 * It provides methods to retrieve, mark users as online or offline, and check if a user exists.
 */
public class UserManager {

    /**
     * The `users` variable is a private static final Map that stores user data.
     * It is a mapping between UUID (unique identifier) and User objects.
     * <p>
     * The keys (UUIDs) are used to uniquely identify each user in the map.
     * The values (User objects) represent individual users.
     * <p>
     * User objects are created instances of the `User` class, which represents a user in the system.
     * Each `User` object contains a UUID, a flag indicating if the user is online or offline,
     * and a reference to the corresponding `Player` object (if the user is online).
     * <p>
     * The `users` map is initialized as an empty HashMap in the `UserManager` class.
     * It is used to store and manage all user data and state in the system.
     * <p>
     * The `getUser` method in the `UserManager` class is used to retrieve a `User` object from the `users` map using a given UUID.
     * If the `User` object does not exist in the map, it will be created using the User constructor and added to the map.
     * <p>
     * The `markUserOnline` method in the `UserManager` class is used to mark a user as online.
     * It retrieves the corresponding `User` object from the `users` map using the given UUID and calls the `setOnline` method on it.
     * The `setOnline` method sets the `isOnline` flag to `true` and gets the corresponding `Player` object from Bukkit using the UUID.
     * <p>
     * The `markUserOffline` method in the `UserManager` class is used to mark a user as offline.
     * It retrieves the corresponding `User` object from the `users` map using the given UUID and calls the `setOffline` method on it.
     * The `setOffline` method sets the `isOnline` flag to `false` and clears the cached `Player` object.
     * <p>
     * The `hasUser` method in the `UserManager` class is used to check if a user with the given UUID exists in the `users` map.
     * It returns `true` if the user exists, and `false` otherwise.
     * <p>
     * The `getAllUsers` method in the `UserManager` class is used to retrieve a list of all `User` objects in the `users` map.
     * It returns an unmodifiable copy of the values in the `users` map.
     * <p>
     * The `User` class contains methods to interact with individual users.
     * The `show` method is used to send a message to the user if they are online.
     * It takes an object that implements the `IDisplayable` interface as a parameter.
     * The `display` method of the `IDisplayable` object is called with the `User` object as an argument.
     * <p>
     * The `getPlayer` method in the `User` class is used to obtain the corresponding `Player` object if the user is online.
     * If the `Player` object is `null` or not online, it retrieves the `Player` object from Bukkit using the UUID.
     * <p>
     * The `IDisplayable` interface represents an object that can be displayed to a user.
     * It contains a single method `display` that takes a `User` object as a parameter.
     * <p>
     * Example usage:
     * <p>
     * // Get a user by UUID
     * UUID uuid = UUID.randomUUID();
     * User user = UserManager.getUser(uuid);
     * <p>
     * // Mark a user as online
     * UserManager.markUserOnline(uuid);
     * <p>
     * // Mark a user as offline
     * UserManager.markUserOffline(uuid);
     * <p>
     * // Check if a user exists
     * boolean exists = UserManager.hasUser(uuid);
     * <p>
     * // Get all users
     * List<User> allUsers = UserManager.getAllUsers();
     * <p>
     * // Display a message to a user
     * user.show(new MyDisplayable());
     * <p>
     * // Get the Player object of a user
     * Player player = user.getPlayer();
     */
    private static final Map<UUID, User> users = new HashMap<>();

    /**
     * Retrieves the User object associated with the given UUID. If a User object does not exist
     * for the UUID, a new User object is created and added to the users map.
     *
     * @param uuid the UUID of the user
     * @return the User object associated with the UUID
     */
    public static User getUser(UUID uuid) {
        return users.computeIfAbsent(uuid, User::new);
    }

    /**
     * Marks a user as online.
     *
     * @param uuid The UUID of the user to mark as online.
     */
    public static void markUserOnline(UUID uuid) {
        User user = getUser(uuid);
        user.setOnline();
    }

    /**
     * Marks a user as offline.
     *
     * @param uuid The UUID of the user to mark as offline.
     */
    public static void markUserOffline(UUID uuid) {
        User user = getUser(uuid);
        user.setOffline();
    }

    /**
     * Checks if a user with the given UUID exists.
     *
     * @param uuid The UUID of the user to check.
     * @return true if a user with the given UUID exists, false otherwise.
     */
    public static boolean hasUser(UUID uuid) {
        return users.containsKey(uuid);
    }

    /**
     * Retrieves a list of all users.
     *
     * @return The list of all users.
     */
    public static List<User> getAllUsers() {
        return List.copyOf(users.values());
    }
}
