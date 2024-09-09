package com.person98.commonsessence.messages;

import com.person98.craftessence.util.builder.IBuilder;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class EssenceMessage {

    private final String message;
    private final Sound sound;
    private final float volume;
    private final float pitch;
    private final String title;
    private final String subtitle;
    private final int fadeIn;
    private final int stay;
    private final int fadeOut;

    // Private constructor to be used by the builder
    private EssenceMessage(Builder builder) {
        this.message = builder.message;
        this.sound = builder.sound;
        this.volume = builder.volume;
        this.pitch = builder.pitch;
        this.title = builder.title;
        this.subtitle = builder.subtitle;
        this.fadeIn = builder.fadeIn;
        this.stay = builder.stay;
        this.fadeOut = builder.fadeOut;
    }

    // Getters for message properties
    public String getMessage() {
        return message;
    }

    public Sound getSound() {
        return sound;
    }

    public float getVolume() {
        return volume;
    }

    public float getPitch() {
        return pitch;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public int getFadeIn() {
        return fadeIn;
    }

    public int getStay() {
        return stay;
    }

    public int getFadeOut() {
        return fadeOut;
    }

    // Send the message, title, and sound to the player
    public void sendTo(Player player) {
        if (message != null) {
            player.sendMessage(message);
        }
        if (sound != null) {
            player.playSound(player.getLocation(), sound, volume, pitch);
        }
        if (title != null || subtitle != null) {
            player.sendTitle(title != null ? title : "", subtitle != null ? subtitle : "", fadeIn, stay, fadeOut);
        }
    }

    // Builder class
    public static class Builder implements IBuilder<EssenceMessage> {
        private String message;
        private Sound sound;
        private float volume = 1.0f;
        private float pitch = 1.0f;
        private String title;
        private String subtitle;
        private int fadeIn = 10;
        private int stay = 70;
        private int fadeOut = 20;

        // Set message
        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        // Set sound with default volume and pitch
        public Builder withSound(Sound sound) {
            this.sound = sound;
            return this;
        }

        // Set sound with custom volume and pitch
        public Builder withSound(Sound sound, float volume, float pitch) {
            this.sound = sound;
            this.volume = volume;
            this.pitch = pitch;
            return this;
        }

        // Set title and subtitle with timings
        public Builder withTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
            this.title = title;
            this.subtitle = subtitle;
            this.fadeIn = fadeIn;
            this.stay = stay;
            this.fadeOut = fadeOut;
            return this;
        }

        // Build the EssenceMessage
        public EssenceMessage build() {
            return new EssenceMessage(this);
        }
    }
}
