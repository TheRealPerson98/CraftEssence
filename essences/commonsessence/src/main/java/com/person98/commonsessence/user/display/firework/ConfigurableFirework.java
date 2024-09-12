package com.person98.commonsessence.user.display.firework;

import com.person98.commonsessence.scheduler.EssenceSchedulers;
import com.person98.commonsessence.user.User;
import com.person98.commonsessence.user.display.IDisplayable;
import com.person98.craftessence.util.builder.IBuilder;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ConfigurableFirework implements IDisplayable {
    private final Type type;
    private final int power;
    private final int delay;
    private final List<Color> color;
    private final List<Color> fadeColor;
    private final boolean trail;
    private final boolean flicker;
    private final List<FireworkEffect> effects;

    public ConfigurableFirework(Type type, int power, int delay, List<Color> color, List<Color> fadeColor, boolean trail, boolean flicker, List<FireworkEffect> effects, Plugin plugin) {
        this.type = type;
        this.power = power;
        this.delay = delay;
        this.color = color;
        this.fadeColor = fadeColor;
        this.trail = trail;
        this.flicker = flicker;
        this.effects = effects;
    }

    public ConfigurableFirework(Builder builder) {
        this.type = builder.type;
        this.power = builder.power;
        this.delay = builder.delay;
        this.color = builder.color;
        this.fadeColor = builder.fadeColor;
        this.trail = builder.trail;
        this.flicker = builder.flicker;
        this.effects = builder.effects;
    }

    @Override
    public void display(User user) {
        Player player = user.getPlayer();
        Location location = player.getLocation();
        World world = player.getWorld();

        Runnable fireworkRunnable = () -> {
            // Spawn firework at player's location
            Firework firework = world.spawn(location, Firework.class);
            FireworkMeta fireworkMeta = firework.getFireworkMeta();

            // Apply firework effects
            FireworkEffect.Builder effectBuilder = FireworkEffect.builder()
                    .with(this.type)
                    .flicker(this.flicker)
                    .trail(this.trail)
                    .withColor(this.color)
                    .withFade(this.fadeColor);

            fireworkMeta.addEffect(effectBuilder.build());
            fireworkMeta.setPower(this.power);
            firework.setFireworkMeta(fireworkMeta);
        };

        if (this.delay > 0) {
            // Use EssenceSchedulers to run the firework launch after the specified delay
            EssenceSchedulers.sync().runLater(fireworkRunnable, this.delay, TimeUnit.SECONDS);
        } else {
            // Run immediately if no delay
            fireworkRunnable.run();
        }
    }

    public static class Builder implements IBuilder<ConfigurableFirework> {
        private Type type = Type.BALL;
        private int power = 1;
        private int delay = 0;
        private List<Color> color;
        private List<Color> fadeColor;
        private boolean trail = false;
        private boolean flicker = false;
        private List<FireworkEffect> effects;

        public Builder withType(Type type) {
            this.type = type;
            return this;
        }

        public Builder withPower(int power) {
            this.power = power;
            return this;
        }

        public Builder withDelay(int delay) {
            this.delay = delay;
            return this;
        }

        public Builder withColors(List<Color> color) {
            this.color = color;
            return this;
        }

        public Builder withFadeColors(List<Color> fadeColor) {
            this.fadeColor = fadeColor;
            return this;
        }

        public Builder withTrail(boolean trail) {
            this.trail = trail;
            return this;
        }

        public Builder withFlicker(boolean flicker) {
            this.flicker = flicker;
            return this;
        }

        public Builder withEffects(List<FireworkEffect> effects) {
            this.effects = effects;
            return this;
        }

        @Override
        public ConfigurableFirework build() {
            return new ConfigurableFirework(this);
        }
    }
}
