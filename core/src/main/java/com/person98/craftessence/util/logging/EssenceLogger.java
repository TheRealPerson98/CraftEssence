package com.person98.craftessence.util.logging;

import com.person98.craftessence.CraftEssence;

public class EssenceLogger {

    // Colors for brackets and 'CraftEssence' text
    private static final String BRACKET_COLOR = ConsoleColors.YELLOW_BOLD;
    private static final String PROJECT_COLOR = ConsoleColors.BLUE_BOLD;
    private static final String RESET_COLOR = ConsoleColors.RESET;

    // Prefix for all log messages with the colored [CraftEssence]
    private static final String LOG_PREFIX = BRACKET_COLOR + "[" + PROJECT_COLOR + "CraftEssence" + BRACKET_COLOR + "]" + RESET_COLOR;

    // Debug log in cyan
    public static void Debug(String message) {
        CraftEssence.getInstance().getLogger().info(ConsoleColors.CYAN + LOG_PREFIX + " " + message + RESET_COLOR);
    }

    // Warning log in yellow
    public static void Warning(String message) {
        CraftEssence.getInstance().getLogger().warning(ConsoleColors.YELLOW + LOG_PREFIX + " " + message + RESET_COLOR);
    }

    // Error log in red
    public static void Error(String message) {
        CraftEssence.getInstance().getLogger().severe(ConsoleColors.RED + LOG_PREFIX + " " + message + RESET_COLOR);
    }

    // General log in green
    public static void Info(String message) {
        CraftEssence.getInstance().getLogger().info(ConsoleColors.GREEN + LOG_PREFIX + " " + message + RESET_COLOR);
    }
}
