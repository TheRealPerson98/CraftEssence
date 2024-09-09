package com.person98.commonsessence.conf;

import com.person98.commonsessence.messages.EssenceMessage;
import com.person98.craftessence.util.annotations.Configurable;
import org.bukkit.Sound;

import java.util.List;
import java.util.Map;

@Configurable(fileName = "lang")
public class LangConf {

    private EssenceMessage test = new EssenceMessage.Builder()
            .withMessage("Welcome,!")
            .withSound(Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f)
                .withTitle("Welcome to the server!", "Enjoy your stay!", 10, 70, 20)
                .build();

    public EssenceMessage getTest() {
        return test;
    }
}
