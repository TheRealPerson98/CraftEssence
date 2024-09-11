package com.person98.craftessence.util.item;


public class ConfigurableNBT {
    private NBTHandler handler = NBTHandler.STRING;
    private String key;
    private String value;

    public NBTHandler getHandler() {
        return this.handler;
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}
