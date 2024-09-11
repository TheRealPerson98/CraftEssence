package com.person98.craftessence.util.item;

import com.person98.craftessence.util.collections.TriConsumer;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;

public enum NBTHandler {
    STRING(NBTCompound::setString),
    INTEGER((nbtItem, key, literal) -> {
        final int value = Integer.parseInt(literal);
        nbtItem.setInteger(key, value);
    }),
    BOOLEAN((nbtItem, key, literal) -> {
        final boolean value = Boolean.parseBoolean(literal);
        nbtItem.setBoolean(key, value);
    }),
    DOUBLE((nbtItem, key, literal) -> {
        final double value = Double.parseDouble(literal);
        nbtItem.setDouble(key, value);
    }),
    LONG((nbtItem, key, literal) -> {
        final long value = Long.parseLong(literal);
        nbtItem.setLong(key, value);
    }),
    ;

    private final TriConsumer<NBTItem, String, String> action;

    NBTHandler(TriConsumer<NBTItem, String, String> action) {
        this.action = action;
    }

    public TriConsumer<NBTItem, String, String> getAction() {
        return this.action;
    }
}