package com.person98.craftessence.core;

import com.person98.craftessence.util.annotations.EssenceInfo;

public interface Essence {

    // Default method for pre-enable, can be overridden by implementing classes
    default void onPreEnable() {
        // Default no-op, can be overridden by implementors
    }

    // Method that subclasses must implement
    void onEnable();

    // Default method for post-enable, can be overridden by implementing classes
    default void onPostEnable() {
        // Default no-op, can be overridden by implementors
    }

    // Default method for pre-disable, can be overridden by implementing classes
    default void onPreDisable() {
        // Default no-op, can be overridden by implementors
    }

    // Method that subclasses must implement
    void onDisable();

    // Default method for reload, can be overridden by implementing classes
    default void onReload() {
        // Default no-op, can be overridden by implementors
    }

    // Method to get EssenceInfo annotation, for easy access to metadata
    default EssenceInfo getEssenceInfo() {
        return this.getClass().getAnnotation(EssenceInfo.class);
    }
}
