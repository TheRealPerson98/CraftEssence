package com.person98.commonsessence.conf;


import com.person98.commonsessence.user.display.messages.EssenceMessage;
import com.person98.commonsessence.user.display.sound.ConfigurableSound;
import com.person98.commonsessence.user.display.title.ConfigurableTitle;
import com.person98.craftessence.util.annotations.Configurable;
import org.bukkit.Sound;

@Configurable(fileName = "lang")
public class LangConf {

    private EssenceMessage joinMessage = new EssenceMessage.Builder()
            .withMessage("<red>Welcome to the <yellow>server!")
            .withSound(new ConfigurableSound.Builder()
                    .withSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP.name())
                    .withVolume(1.0f)
                    .withPitch(1.0f)
                    .build())
            .withTitle(new ConfigurableTitle.Builder()
                    .withTitle("<red>Welcome!")
                    .withSubtitle("To the server!")
                    .withTimings(10, 70, 20)
                    .build())
            .build();

    public EssenceMessage getJoinMessage() {
        return joinMessage;
    }
}
