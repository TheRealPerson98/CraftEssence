package com.person98.commonsessence.invtory.menu;

import com.person98.craftessence.util.builder.IBuilder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class EssenceMenuElement {
    private final String name;
    private final char key;
    private final Map<String, ItemStack> items; // Map to store different states of the item
    private final ItemStack defaultItem; // Default item to display

    private EssenceMenuElement(Builder builder) {
        this.name = builder.name;
        this.key = builder.key;
        this.items = builder.items;
        this.defaultItem = builder.defaultItem;
    }

    public String getName() {
        return name;
    }

    public char getKey() {
        return key;
    }

    public Map<String, ItemStack> getItems() {
        return items;
    }

    public ItemStack getDefaultItem() {
        return defaultItem;
    }

    public static class Builder implements IBuilder<EssenceMenuElement> {
        private String name;
        private char key;
        private Map<String, ItemStack> items = new HashMap<>();
        private ItemStack defaultItem;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder key(char key) {
            this.key = key;
            return this;
        }

        public Builder item(String state, ItemStack item) {
            this.items.put(state, item);
            return this;
        }

        public Builder defaultItem(ItemStack defaultItem) {
            this.defaultItem = defaultItem;
            return this;
        }

        public EssenceMenuElement build() {
            return new EssenceMenuElement(this);
        }
    }
}
