package com.person98.commonsessence.conf;

import com.person98.commonsessence.invtory.Menu;
import com.person98.commonsessence.invtory.menu.ConfigurableMenu;
import com.person98.commonsessence.user.User;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CoolMenu extends Menu<ItemStack> {

    public CoolMenu(ConfigurableMenu configurableMenu, User user) {
        super(configurableMenu, user);
    }

    @Override
    public @NotNull List<ItemStack> requestObjects() {
        List<ItemStack> itemStacks = new ArrayList<>();
        itemStacks.add(new ItemStack(Material.DIAMOND));
        itemStacks.add(new ItemStack(Material.GOLD_INGOT));
        // Add more items dynamically
        return itemStacks;
    }

    @Override
    protected ItemStack convertObjectToItemStack(ItemStack object) {
        return object; // Since we're already working with ItemStack
    }
}
