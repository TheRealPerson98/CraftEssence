package com.person98.craftessence.util.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

public class ItemUtil {
    public static boolean isAirOrNull(@Nullable ItemStack itemStack) {
        return Objects.isNull(itemStack) || itemStack.getType() == Material.AIR;
    }

    public static @Nullable String serialize(@NotNull ItemStack itemStack) {
        try (final ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream()) {
            try (final BukkitObjectOutputStream objectOutputStream = new BukkitObjectOutputStream(arrayOutputStream)) {
                objectOutputStream.writeObject(itemStack);
                objectOutputStream.flush();
                return Base64Coder.encodeLines(arrayOutputStream.toByteArray());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static @Nullable ItemStack deserialize(@NotNull String data) {
        if (data.contains("{")) {
            return null;
        }

        try {
            final byte[] bytes = Base64Coder.decodeLines(data);

            try (final ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(bytes)) {
                try (final BukkitObjectInputStream objectInputStream = new BukkitObjectInputStream(arrayInputStream)) {
                    return (ItemStack) objectInputStream.readObject();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
