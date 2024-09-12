package com.person98.warpessence;

import com.person98.craftessence.core.Essence;
import com.person98.craftessence.util.annotations.EssenceInfo;
import com.person98.craftessence.util.logging.EssenceLogger;

@EssenceInfo(
        version = "1.0",
        author = "Person98",
        name = "WarpEssence",
        description = "This essence provides warp functionality.",
        internalDependencies = {"CommonsEssence"},
        externalDependencies = {}
)
public class WarpEssence implements Essence {


    @Override
    public void onPreEnable() {
        EssenceLogger.Info("WarpEssence is preparing to enable...");
        // Add pre-enable logic here
    }

    @Override
    public void onEnable() {
        EssenceLogger.Info("WarpEssence is now enabled!");

    }

    @Override
    public void onPreDisable() {
        EssenceLogger.Info("WarpEssence is preparing to disable...");
        // Add pre-disable logic here
    }

    @Override
    public void onDisable() {
        EssenceLogger.Info("WarpEssence is now disabled!");
        // Add disable logic here
    }

    @Override
    public void onReload() {
        EssenceLogger.Info("WarpEssence is reloading...");
        // Add reload logic here
    }
}
