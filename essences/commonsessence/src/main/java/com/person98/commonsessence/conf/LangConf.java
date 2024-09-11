package com.person98.commonsessence.conf;


import com.person98.commonsessence.invtory.menu.ConfigurableMenu;
import com.person98.commonsessence.invtory.menu.EssenceMenuElement;
import com.person98.commonsessence.user.display.firework.ConfigurableFirework;
import com.person98.commonsessence.user.display.messages.EssenceMessage;
import com.person98.commonsessence.user.display.sound.ConfigurableSound;
import com.person98.commonsessence.user.display.title.ConfigurableTitle;
import com.person98.craftessence.util.annotations.Configurable;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.Sound;

import java.util.Arrays;
import java.util.List;

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

    private final ConfigurableMenu buyMenu = new ConfigurableMenu.Builder()
            .menuTitle("Buy Menu")
            .name("Buy Menu")
            .menuDesign("XXXXXXXXX",
                    "XBNMOIHUX",
                    "XXXRXAXXX",
                    "XXXVXCXXX"
            )
            .item(new ConfigurableMenuElement.Builder()
                    .name("border")
                    .key('X')
                    .defaultItem(ItemBuilder.of(Material.GRAY_STAINED_GLASS_PANE).name(" ").build())
                    .build())

            .item(new EssenceMenuElement.Builder()
                    .name("return_close")
                    .key('C')
                    .item("close", ItemBuilder.of(Material.BARRIER).name("Close").build())
                    .build())

            .item(new EssenceMenuElement.Builder()
                    .name("add_1")
                    .key('U')
                    .defaultItem(ItemBuilder.of(Material.GREEN_STAINED_GLASS_PANE)
                            .name("Add one")
                            .build())
                    .build())

            .item(new EssenceMenuElement.Builder()
                    .name("add_16")
                    .key('I')
                    .defaultItem(ItemBuilder.of(Material.GREEN_STAINED_GLASS_PANE)
                            .name("Add ten")
                            .build())
                    .build())

            .item(new EssenceMenuElement.Builder()
                    .name("add_64")
                    .key('H')
                    .defaultItem(ItemBuilder.of(Material.GREEN_STAINED_GLASS_PANE)
                            .name("Add a stack")
                            .build())
                    .build())

            .item(new EssenceMenuElement.Builder()
                    .name("remove_1")
                    .key('B')
                    .defaultItem(ItemBuilder.of(Material.RED_STAINED_GLASS_PANE)
                            .name("Remove one")
                            .build())
                    .build())

            .item(new EssenceMenuElement.Builder()
                    .name("remove_16")
                    .key('N')
                    .defaultItem(ItemBuilder.of(Material.RED_STAINED_GLASS_PANE)
                            .name("Remove ten")
                            .build())
                    .build())

            .item(new EssenceMenuElement.Builder()
                    .name("remove_64")
                    .key('M')
                    .defaultItem(ItemBuilder.of(Material.RED_STAINED_GLASS_PANE)
                            .name("Remove a stack")
                            .build())
                    .build())
            .item(new EssenceMenuElement.Builder()
                    .name("shop_item")
                    .defaultItem(ItemBuilder.of(Material.PAPER)
                            .build())
                    .build())
            .item(new EssenceMenuElement.Builder()
                    .name("confirm")
                    .key('V')
                    .item("buying", ItemBuilder.of(Material.PAPER)
                            .name("Buy Price")
                            .lore(List.of(
                                    "Cost: {buy_cost}",
                                    "Amount: {amount}",
                                    "Currency: {currency}"
                            ))
                            .build())
                    .item("selling", ItemBuilder.of(Material.PAPER)
                            .name("Sell Price")
                            .lore(List.of(
                                    "Cost: {sell_cost}",
                                    "Amount: {amount}",
                                    "Currency: {currency}"
                            ))
                            .build())
                    .build())
            .item(new EssenceMenuElement.Builder()
                    .name("price_type")
                    .key('R')
                    .item("buy_price", ItemBuilder.of(Material.PAPER).name("Buy Price").build())
                    .item("sell_price", ItemBuilder.of(Material.PAPER).name("Sell Price").build())
                    .build())
            .updateTicks(-1)
            .build();

    public EssenceMessage getJoinMessage() {
        return joinMessage;
    }
}
