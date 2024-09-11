package com.person98.craftessence.util.item;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

public class ItemBuilder {
    private final ItemStack itemStack;

    private ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public static ItemBuilder of(Material material) {
        return new ItemBuilder(material);
    }

    public ItemBuilder amount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder name(String name) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setDisplayName(name);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setLore(lore);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder enchant(Enchantment enchantment, int level) {
        this.itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder customModelData(int data) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setCustomModelData(data);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder unbreakable(boolean unbreakable) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setUnbreakable(unbreakable);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder itemFlags(Map<String, Boolean> flags) {
        ItemMeta meta = this.itemStack.getItemMeta();
        for (String flagName : flags.keySet()) {
            meta.addItemFlags(ItemFlag.valueOf(flagName));
        }
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder skull(String skullTexture) {
        // Handle skull textures if applicable (for player heads)
        return this;
    }

    public ItemStack build() {
        return this.itemStack;
    }
}
