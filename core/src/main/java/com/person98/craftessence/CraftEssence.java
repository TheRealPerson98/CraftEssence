package com.person98.craftessence;

import com.person98.craftessence.loader.EssenceLoader;
import com.person98.craftessence.util.logging.ConsoleColors;
import org.bukkit.plugin.java.JavaPlugin;
import lombok.Getter;

public class CraftEssence extends JavaPlugin {

    @Getter
    private static CraftEssence instance;

    @Getter
    private EssenceLoader essenceLoader;

    private static final String PLUGIN_AUTHOR = "Person98";
    private static final String PLUGIN_VERSION = "1.0 Beta";

    @Override
    public void onEnable() {
        instance = this;
        printStartupBox();
        this.essenceLoader = new EssenceLoader();
        this.essenceLoader.loadEssencesInOrder();
    }

    @Override
    public void onDisable() {
        getLogger().info("Core Plugin Disabled!");
    }

    private void printStartupBox() {
        getLogger().info(ConsoleColors.YELLOW_BOLD + "###########################################" + ConsoleColors.RESET);
        getLogger().info(ConsoleColors.GREEN_BOLD + "#              " + ConsoleColors.BLUE_BOLD + "CraftEssence" + ConsoleColors.RESET + "              " + ConsoleColors.GREEN_BOLD + " #" + ConsoleColors.RESET);
        getLogger().info(ConsoleColors.CYAN_BOLD + "# " + ConsoleColors.PURPLE_BOLD + "Author: " + ConsoleColors.RESET + PLUGIN_AUTHOR + "  " + ConsoleColors.CYAN_BOLD + "Version: " + ConsoleColors.RESET + PLUGIN_VERSION + " " + ConsoleColors.CYAN_BOLD + "    #");
        getLogger().info(ConsoleColors.YELLOW_BOLD + "###########################################" + ConsoleColors.RESET);
    }
}
