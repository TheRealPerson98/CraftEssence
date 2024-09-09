package com.person98.commonsessence.user;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManager {

    private static final Map<UUID, User> users = new HashMap<>();

    // Get user by UUID, create if not present
    public static User getUser(UUID uuid) {
        return users.computeIfAbsent(uuid, User::new);
    }

    // Mark a user as online
    public static void markUserOnline(UUID uuid) {
        User user = getUser(uuid);
        user.setOnline();
    }

    // Mark a user as offline
    public static void markUserOffline(UUID uuid) {
        User user = getUser(uuid);
        user.setOffline();
    }

    // Check if a user exists (useful for managing inactive users)
    public static boolean hasUser(UUID uuid) {
        return users.containsKey(uuid);
    }
}
