package com.person98.commonsessence;

import com.person98.craftessence.core.Essence;
import com.person98.craftessence.core.Instances;
import com.person98.craftessence.util.annotations.EssenceInfo;
import com.person98.craftessence.util.logging.EssenceLogger; // Assuming you have EssenceLogger for logging
import lombok.Getter;

@EssenceInfo(
        version = "1.0",
        author = "Person98",
        name = "CommonsEssence",
        description = "This commons for every essence.",
        internalDependencies = {},
        externalDependencies = {}
)
public class CommonsEssence implements Essence {

    @Override
    public void onPreEnable() {
        EssenceLogger.Info("CommonsEssence is preparing to enable...");

    }

    @Override
    public void onEnable() {
        EssenceLogger.Info("CommonsEssence is now enabled!");
        // Add enable logic here
    }

    @Override
    public void onPreDisable() {
        EssenceLogger.Info("CommonsEssence is preparing to disable...");
        // Add pre-disable logic here
    }

    @Override
    public void onDisable() {
        EssenceLogger.Info("CommonsEssence is now disabled!");
        // Add disable logic here
    }

    @Override
    public void onReload() {
        EssenceLogger.Info("CommonsEssence is reloading...");
        // Add reload logic here
    }

    public String getCommonsData() {
        return "Shared Commons Data";
    }
}
