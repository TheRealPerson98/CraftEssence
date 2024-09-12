package com.person98.commonsessence.conf;


import com.person98.commonsessence.invtory.menu.ConfigurableMenu;
import com.person98.commonsessence.invtory.menu.EssenceMenuElement;
import com.person98.craftessence.util.annotations.Configurable;
import com.person98.craftessence.util.item.ItemBuilder;
import org.bukkit.Material;

@Configurable(fileName = "lang")
public class LangConf {

    private final ConfigurableMenu buyMenu = new ConfigurableMenu.Builder()
            .menuTitle("Buy Menu")
            .name("Buy Menu")
            .menuDesign("XXXXXXXXX",
                    "XOOOOOOOX",
                    "XOOOOOOOX",
                    "XXXXCXXXX"
            )
            .item(new EssenceMenuElement.Builder()
                    .name("border")
                    .key('X')
                    .defaultItem(ItemBuilder.of(Material.GRAY_STAINED_GLASS_PANE).name(" ").build())
                    .build())

            .item(new EssenceMenuElement.Builder()
                    .defaultItem(ItemBuilder.of(Material.PAPER).name("<blue>{task_name}").build())
                    .build())

            .item(new EssenceMenuElement.Builder()
                    .name("return_close")
                    .key('C')
                    .defaultItem(ItemBuilder.of(Material.BARRIER).name("<red>Close").build())
                    .build())

            .updateTicks(-1)
            .build();

    public ConfigurableMenu getBuyMenu() {
        return this.buyMenu;
    }

}
//        private EssenceMessage joinMessage = new EssenceMessage.Builder()
//            .withMessage("<red>Welcome to the <yellow>server!")
//            .withSound(new ConfigurableSound.Builder()
//                    .withSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP.name())
//                    .withVolume(1.0f)
//                    .withPitch(1.0f)
//                    .build())
//            .withTitle(new ConfigurableTitle.Builder()
//                    .withTitle("<red>Welcome!")
//                    .withSubtitle("To the server!")
//                    .withTimings(10, 70, 20)
//                    .build())
//            .withFirework(new ConfigurableFirework.Builder()
//                    .withType(FireworkEffect.Type.STAR)
//                    .withPower(2)
//                    .withDelay(40)  // 2 second delay
//                    .withColors(Arrays.asList(Color.RED, Color.YELLOW))
//                    .withFadeColors(Arrays.asList(Color.BLUE))
//                    .withTrail(true)
//                    .withFlicker(true)
//                    .build())
//            .build();

