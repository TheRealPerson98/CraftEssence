package com.person98.craftessence.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

public class ComponentUtil {

    /**
     * miniMessage is an instance of the MiniMessage class.
     * It is used for deserializing and serializing text with the mini message format.
     * It provides methods for translating mini message strings to legacy strings,
     * as well as translating mini message strings to components.
     * <p>
     * Example usage:
     * String miniMessageString = "<red>Hello, <blue>world!</blue></red>";
     * String legacyString = ComponentUtil.translateToLegacy(miniMessageString);
     * Component component = ComponentUtil.translateToComponent(miniMessageString);
     */
    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
    /**
     *
     */
    private static final LegacyComponentSerializer LEGACY_SERIALIZER = LegacyComponentSerializer.legacySection();

    /**
     * Translates a MiniMessage string to legacy format.
     *
     * @param miniMessageString the MiniMessage string to be translated
     * @return the translated legacy string
     */
    public static String translateToLegacy(String miniMessageString) {
        Component component = MINI_MESSAGE.deserialize(miniMessageString);
        return LEGACY_SERIALIZER.serialize(component);
    }

    /**
     * Translates a MiniMessage formatted String into a Component.
     *
     * @param miniMessageString The MiniMessage formatted String to be translated.
     * @return The Component representing the translated MiniMessage String.
     */
    public static Component translateToComponent(String miniMessageString) {
        return MINI_MESSAGE.deserialize(miniMessageString);
    }

    public static @NotNull String readFromMiniMessage(@NotNull Component component) {
        return MiniMessage.miniMessage().serialize(component);
    }
}
