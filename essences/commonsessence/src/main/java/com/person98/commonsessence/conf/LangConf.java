package com.person98.commonsessence.conf;


import com.person98.commonsessence.user.display.firework.ConfigurableFirework;
import com.person98.commonsessence.user.display.messages.EssenceMessage;
import com.person98.commonsessence.user.display.sound.ConfigurableSound;
import com.person98.commonsessence.user.display.title.ConfigurableTitle;
import com.person98.craftessence.util.annotations.Configurable;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Sound;

import java.util.Arrays;

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
            .withFirework(new ConfigurableFirework.Builder()
                    .withType(FireworkEffect.Type.STAR)
                    .withPower(2)
                    .withDelay(40)  // 2 second delay
                    .withColors(Arrays.asList(Color.RED, Color.YELLOW))
                    .withFadeColors(Arrays.asList(Color.BLUE))
                    .withTrail(true)
                    .withFlicker(true)
                    .build())
            .build();

    public EssenceMessage getJoinMessage() {
        return joinMessage;
    }
}
