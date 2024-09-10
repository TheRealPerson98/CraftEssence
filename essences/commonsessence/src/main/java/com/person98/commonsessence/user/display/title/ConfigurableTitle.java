package com.person98.commonsessence.user.display.title;

import com.person98.commonsessence.util.ComponentUtil;
import com.person98.commonsessence.user.User;
import com.person98.commonsessence.user.display.IDisplayable;
import com.person98.craftessence.util.builder.IBuilder;
import org.bukkit.entity.Player;

public class ConfigurableTitle implements IDisplayable, Cloneable {
    private final String title;
    private final String subtitle;
    private final int fadeIn;
    private final int stay;
    private final int fadeOut;

    public ConfigurableTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        this.title = title;
        this.subtitle = subtitle;
        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
    }

    public ConfigurableTitle(Builder builder) {
        this.title = builder.title;
        this.subtitle = builder.subtitle;
        this.fadeIn = builder.fadeIn;
        this.stay = builder.stay;
        this.fadeOut = builder.fadeOut;
    }

    @Override
    public void display(User user) {
        Player player = user.getPlayer();

        // Use ComponentUtil to translate MiniMessage strings to legacy format for title and subtitle
        String translatedTitle = ComponentUtil.translateToLegacy(this.title);
        String translatedSubtitle = ComponentUtil.translateToLegacy(this.subtitle);

        player.sendTitle(translatedTitle, translatedSubtitle, this.fadeIn, this.stay, this.fadeOut);
    }

    @Override
    public ConfigurableTitle clone() {
        return new ConfigurableTitle(this.title, this.subtitle, this.fadeIn, this.stay, this.fadeOut);
    }

    public static class Builder implements IBuilder<ConfigurableTitle> {
        private String title;
        private String subtitle;
        private int fadeIn = 10;
        private int stay = 70;
        private int fadeOut = 20;

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withSubtitle(String subtitle) {
            this.subtitle = subtitle;
            return this;
        }

        public Builder withTimings(int fadeIn, int stay, int fadeOut) {
            this.fadeIn = fadeIn;
            this.stay = stay;
            this.fadeOut = fadeOut;
            return this;
        }

        @Override
        public ConfigurableTitle build() {
            return new ConfigurableTitle(this);
        }
    }
}
