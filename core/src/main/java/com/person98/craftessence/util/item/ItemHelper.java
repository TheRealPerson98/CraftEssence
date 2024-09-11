package com.person98.craftessence.util.item;

import com.google.common.collect.Lists;
import com.person98.craftessence.util.ComponentUtil;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.kyori.adventure.text.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemHelper {

    private static final ItemHelper instance = new ItemHelper();

    private ItemHelper() {}

    public static ItemHelper get() {
        return instance;
    }

    public @Nullable String getSerializableDisplayName(@NotNull ItemStack itemStack) {
        if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName()) {
            return ComponentUtil.readFromMiniMessage(itemStack.getItemMeta().displayName());
        }
        return null;
    }

    public @NotNull List<String> getSerializableLore(@NotNull ItemStack itemStack) {
        final List<String> serializable = Lists.newArrayList();

        if (itemStack.hasItemMeta()) {
            final ItemMeta meta = itemStack.getItemMeta();

            if (!meta.hasLore()) {
                return serializable;
            }

            final List<Component> lore = meta.lore();

            for (final Component component : lore) {
                serializable.add(ComponentUtil.readFromMiniMessage(component));
            }
        }

        return serializable;
    }

    public @NotNull Component asComponent(@NotNull ItemStack itemStack) {
        Component component = Component.empty();

        if (ItemUtil.isAirOrNull(itemStack)) {
            return component;
        }

        final ItemMeta meta = itemStack.getItemMeta();

        component = component.append(this.asNameComponent(itemStack));

        if (meta.hasEnchants()) {
            for (final Map.Entry<Enchantment, Integer> entry : meta.getEnchants().entrySet()) {
                component = component.append(Component.newline())
                        .append(entry.getKey().displayName(entry.getValue()));
            }
        }

        if (meta.hasLore()) {
            for (final Component line : meta.lore()) {
                component = component.append(Component.newline()).append(line);
            }
        }

        return component;
    }

    public @NotNull Component asNameComponent(@NotNull ItemStack itemStack) {
        if (ItemUtil.isAirOrNull(itemStack)) {
            return Component.text("Air");
        }

        final ItemMeta meta = itemStack.getItemMeta();

        if (meta.hasDisplayName()) {
            return meta.displayName();
        } else {
            final Material material = itemStack.getType();
            final String name = material.name().replace("_", " ");

            return Component.text(name);
        }
    }

    // Implementing missing methods

    /**
     * Get the list of ItemFlags for serialization.
     *
     * @param meta The ItemMeta from the ItemStack
     * @return List of item flags in string form
     */
    public List<String> getSerializableItemFlags(ItemMeta meta) {
        List<String> flags = new ArrayList<>();
        if (meta != null) {
            for (ItemFlag flag : meta.getItemFlags()) {
                flags.add(flag.name());
            }
        }
        return flags;
    }

    /**
     * Get the list of ConfigurableNBT from the ItemStack.
     *
     * @param itemStack The item stack that may contain NBT data.
     * @return A list of ConfigurableNBT objects representing the NBT.
     */
    public List<ConfigurableNBT> getSerializableNBT(ItemStack itemStack) {
        List<ConfigurableNBT> nbtList = new ArrayList<>();
        if (itemStack != null && itemStack.hasItemMeta()) {
            NBTItem nbtItem = new NBTItem(itemStack); // Assuming you're using NBTAPI
            for (String key : nbtItem.getKeys()) {
                String value = nbtItem.getString(key); // Assuming NBT data is stored as a string

                try {
                    ConfigurableNBT configurableNBT = new ConfigurableNBT();

                    // Use reflection to set the private key and value fields
                    Field keyField = ConfigurableNBT.class.getDeclaredField("key");
                    keyField.setAccessible(true); // Make the field accessible
                    keyField.set(configurableNBT, key); // Set the key field

                    Field valueField = ConfigurableNBT.class.getDeclaredField("value");
                    valueField.setAccessible(true); // Make the field accessible
                    valueField.set(configurableNBT, value); // Set the value field

                    nbtList.add(configurableNBT); // Add the configured NBT to the list
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace(); // Handle potential reflection issues
                }
            }
        }
        return nbtList;
    }
    /**
     * Get the enchantments of the item for serialization.
     *
     * @param itemStack The item stack that contains enchantments.
     * @return A map of enchantment names and their levels.
     */
    public Map<String, Integer> getSerializableEnchantments(ItemStack itemStack) {
        Map<String, Integer> enchantments = new HashMap<>();
        if (itemStack != null && itemStack.hasItemMeta()) {
            for (Map.Entry<Enchantment, Integer> entry : itemStack.getEnchantments().entrySet()) {
                enchantments.put(entry.getKey().getKey().getKey(), entry.getValue());
            }
        }
        return enchantments;
    }
}
