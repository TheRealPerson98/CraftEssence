package com.person98.commonsessence;

import com.person98.commonsessence.conf.LangConf;
import com.person98.commonsessence.event.Events;
import com.person98.commonsessence.user.UserManager;
import com.person98.commonsessence.user.event.UserListener;
import com.person98.craftessence.core.Essence;
import com.person98.craftessence.core.Instances;
import com.person98.craftessence.util.annotations.EssenceInfo;
import com.person98.craftessence.util.logging.EssenceLogger; // Assuming you have EssenceLogger for logging
import jdk.jfr.Event;
import lombok.Getter;
import org.bukkit.event.player.PlayerJoinEvent;

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

        Events.hook(PlayerJoinEvent.class, event -> {
            UserManager.getUser(event.getPlayer().getUniqueId()).show(this.getConfig(LangConf.class).getJoinMessage());
        }).bindTo(this);
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
