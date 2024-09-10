package com.person98.commonsessence.user.display.sound;

import com.person98.commonsessence.user.User;
import com.person98.commonsessence.user.display.IDisplayable;
import com.person98.craftessence.util.builder.IBuilder;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class ConfigurableSound implements IDisplayable, Cloneable {
    private final String sound;
    private final float volume;
    private final float pitch;
    private transient Sound cachedSound;

    public ConfigurableSound(String sound, float volume, float pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public ConfigurableSound(Builder builder) {
        this.sound = builder.sound;
        this.volume = builder.volume;
        this.pitch = builder.pitch;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Sound getCachedSound() {
        if (this.cachedSound == null) {
            this.cachedSound = Sound.valueOf(this.sound);
        }
        return this.cachedSound;
    }

    @Override
    public void display(User user) {
        Player player = user.getPlayer();
        player.playSound(player.getLocation(), getCachedSound(), volume, pitch);
    }

    @Override
    public ConfigurableSound clone() {
        return new ConfigurableSound(this.sound, this.volume, this.pitch);
    }

    public static class Builder implements IBuilder<ConfigurableSound> {
        private String sound;
        private float volume;
        private float pitch;

        public Builder withSound(String sound) {
            this.sound = sound;
            return this;
        }

        public Builder withVolume(float volume) {
            this.volume = volume;
            return this;
        }

        public Builder withPitch(float pitch) {
            this.pitch = pitch;
            return this;
        }

        @Override
        public ConfigurableSound build() {
            return new ConfigurableSound(this);
        }
    }
}
