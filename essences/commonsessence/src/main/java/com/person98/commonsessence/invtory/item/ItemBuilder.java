package com.person98.commonsessence.invtory.item;


import com.person98.craftessence.core.Instances;
import com.person98.craftessence.util.builder.IBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.eclipse.sisu.Priority;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;

public interface ItemBuilder extends IBuilder<ItemStack> {
    static @NotNull ItemBuilder of(@NotNull Material material) {
        return of(new ItemStack(material));
    }

    static @NotNull ItemBuilder of(@NotNull ItemStack itemStack) {
        return Instances.getOrThrow(ItemBuilder.class).create(itemStack);
    }

    static @NotNull ItemBuilder of() {
        return of(Material.STONE);
    }

    @NotNull
    ItemBuilder create(@NotNull ItemStack itemStack);

    @NotNull
    ItemBuilder material(@NotNull Material material);

    @NotNull
    ItemBuilder name(@NotNull String name);

    @NotNull
    ItemBuilder name(@NotNull Component name);

    @NotNull
    ItemBuilder lore(@NotNull String... lore);

    @NotNull
    ItemBuilder lore(@NotNull Collection<String> lore);

    @NotNull
    ItemBuilder lore(@NotNull Component lore, @NotNull Priority priority);

    @NotNull
    ItemBuilder clearLore();

    @NotNull
    ItemBuilder customModelData(int modelData);

    @NotNull
    ItemBuilder enchant(@NotNull Enchantment enchantment, int level);

    @NotNull
    ItemBuilder amount(int amount);

    @NotNull
    ItemBuilder itemFlags(@NotNull ItemFlag... itemFlags);

    @NotNull
    ItemBuilder itemFlags(@NotNull Map<String, Boolean> itemFlagsMap);

    @NotNull
    ItemBuilder unbreakable(boolean unbreakable);

    @NotNull
    ItemBuilder glow(boolean glow);

    @NotNull
    ItemBuilder skull(@NotNull String value);

    @NotNull
    ItemBuilder useMeta(@NotNull ItemStack stack);


}