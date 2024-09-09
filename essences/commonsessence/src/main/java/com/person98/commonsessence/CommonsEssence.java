package com.person98.commonsessence;

import com.person98.commonsessence.conf.LangConf;
import com.person98.commonsessence.user.event.UserListener;
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
        this.loadConfig(LangConf.class);
    }

    @Override
    public void onEnable() {
        new UserListener(this);
    }

    @Override
    public void onPreDisable() {
        EssenceLogger.Info("CommonsEssence is preparing to disable...");
    }

    @Override
    public void onDisable() {
        EssenceLogger.Info("CommonsEssence is now disabled!");
    }

    @Override
    public void onReload() {
        EssenceLogger.Info("CommonsEssence is reloading...");
        // Add reload logic here
    }
}
