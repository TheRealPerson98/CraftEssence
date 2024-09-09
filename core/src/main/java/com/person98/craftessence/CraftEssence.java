package com.person98.craftessence;

import io.papermc.paper.text.PaperComponents;
import org.bukkit.plugin.java.JavaPlugin;

public class CraftEssence extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Core Plugin Enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Core Plugin Disabled!");
    }
}
