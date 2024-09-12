package com.person98.craftessence.util.logging;

import com.person98.craftessence.CraftEssence;

/**
 * The EssenceLogger class provides static methods for logging messages with different log levels.
 * It uses ANSI escape codes to change the color and style of console output.
 * <p>
 * Usage example:
 * <p>
 * EssenceLogger.Debug("Debug message");
 * EssenceLogger.Warning("Warning message");
 * EssenceLogger.Error("Error message");
 * EssenceLogger.Info("Info message");
 */
public class EssenceLogger {

    /**
     * The BRACKET_COLOR variable represents the ANSI escape code for the color used to display the brackets in the console output of the CraftEssence logger.
     */
    private static final String BRACKET_COLOR = ConsoleColors.YELLOW_BOLD;
    /**
     *
     */
    private static final String PROJECT_COLOR = ConsoleColors.BLUE_BOLD;
    /**
     * The RESET_COLOR variable is used to reset the color and style of console output.
     * It uses the ANSI escape code provided by the ConsoleColors.RESET constant.
     * <p>
     * Example usage:
     * <p>
     * System.out.println(ConsoleColors.RED + "This text is in red");
     * System.out.println(RESET_COLOR + "This text is back to default color");
     */
    private static final String RESET_COLOR = ConsoleColors.RESET;

    /**
     * String constant that represents the log prefix for console output.
     * <p>
     * The log prefix is a formatted string that is displayed at the beginning of each log message.
     * It includes the project name "CraftEssence" enclosed in brackets, and uses ANSI escape codes
     * to change the color and style of the console output.
     * <p>
     * Usage example:
     * <p>
     * String logMessage = LOG_PREFIX + " Debug message";
     * System.out.println(logMessage);
     */
    private static final String LOG_PREFIX = BRACKET_COLOR + "[" + PROJECT_COLOR + "CraftEssence" + BRACKET_COLOR + "]" + RESET_COLOR;

    /**
     * Logs a debug message to the console.
     *
     * @param message the message to be logged
     */
    public static void Debug(String message) {
        CraftEssence.getInstance().getLogger().info(ConsoleColors.CYAN + LOG_PREFIX + " " + message + RESET_COLOR);
    }

    /**
     * Displays a warning message in the console.
     *
     * @param message the message to display
     */
    public static void Warning(String message) {
        CraftEssence.getInstance().getLogger().warning(ConsoleColors.YELLOW + LOG_PREFIX + " " + message + RESET_COLOR);
    }

    /**
     * Logs an error message to the console with a given message.
     * The message will be logged with the color red.
     *
     * @param message The error message to be logged
     */
    public static void Error(String message) {
        CraftEssence.getInstance().getLogger().severe(ConsoleColors.RED + LOG_PREFIX + " " + message + RESET_COLOR);
    }

    /**
     * Logs an information message to the console.
     *
     * @param message the message to be logged
     */
    public static void Info(String message) {
        CraftEssence.getInstance().getLogger().info(ConsoleColors.GREEN + LOG_PREFIX + " " + message + RESET_COLOR);
    }
}
