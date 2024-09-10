package com.person98.commonsessence.user.display.messages;

import com.person98.commonsessence.uitl.ComponentUtil;
import com.person98.commonsessence.user.User;
import com.person98.commonsessence.user.display.IDisplayable;
import com.person98.commonsessence.user.display.sound.ConfigurableSound;
import com.person98.commonsessence.user.display.title.ConfigurableTitle;
import com.person98.craftessence.util.builder.IBuilder;
import org.bukkit.entity.Player;

public class EssenceMessage implements IDisplayable {
    private final String message;
    private final ConfigurableSound sound;
    private final ConfigurableTitle title;

    public EssenceMessage(Builder builder) {
        this.message = builder.message;
        this.sound = builder.sound;
        this.title = builder.title;
    }

    @Override
    public void display(User user) {
        Player player = user.getPlayer();

        if (this.message != null) {
            // Use ComponentUtil to translate the MiniMessage string to legacy format
            String translatedMessage = ComponentUtil.translateToLegacy(this.message);
            player.sendMessage(translatedMessage);
        }

        if (this.sound != null) {
            this.sound.display(user);
        }

        if (this.title != null) {
            this.title.display(user);
        }
    }

    public static class Builder implements IBuilder<EssenceMessage> {
        private String message;
        private ConfigurableSound sound;
        private ConfigurableTitle title;

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder withSound(ConfigurableSound sound) {
            this.sound = sound;
            return this;
        }

        public Builder withTitle(ConfigurableTitle title) {
            this.title = title;
            return this;
        }

        @Override
        public EssenceMessage build() {
            return new EssenceMessage(this);
        }
    }
}
