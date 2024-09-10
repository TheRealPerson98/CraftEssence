package com.person98.commonsessence.uitl;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.Component;

public class ComponentUtil {

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
    private static final LegacyComponentSerializer LEGACY_SERIALIZER = LegacyComponentSerializer.legacySection();

    // Converts MiniMessage formatted string to a legacy-formatted string
    public static String translateToLegacy(String miniMessageString) {
        Component component = MINI_MESSAGE.deserialize(miniMessageString);
        return LEGACY_SERIALIZER.serialize(component);
    }

    // Converts MiniMessage formatted string to a Component (useful for other Adventure API uses)
    public static Component translateToComponent(String miniMessageString) {
        return MINI_MESSAGE.deserialize(miniMessageString);
    }
}
