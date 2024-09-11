package com.person98.commonsessence.invtory;

import com.person98.commonsessence.invtory.menu.ConfigurableMenu;
import com.person98.commonsessence.invtory.menu.EssenceMenuElement;
import com.person98.commonsessence.user.User;
import com.person98.commonsessence.user.display.IDisplayable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class Menu<T> implements InventoryHolder, IDisplayable {
    protected final ConfigurableMenu configurableMenu;
    protected final Inventory inventory;

    public Menu(ConfigurableMenu configurableMenu, User user) {
        this.configurableMenu = configurableMenu;
        this.inventory = Bukkit.createInventory(this, configurableMenu.getMenuDesign().size() * 9, configurableMenu.getMenuTitle());
        setupMenu(user);
    }

    // Request objects to place dynamically in the menu
    public abstract @NotNull List<T> requestObjects();

    // Setup the menu based on design and objects
    protected void setupMenu(User user) {
        List<T> objects = requestObjects();
        int objectIndex = 0;

        for (int i = 0; i < configurableMenu.getMenuDesign().size(); i++) {
            String row = configurableMenu.getMenuDesign().get(i);
            for (int j = 0; j < row.length(); j++) {
                char key = row.charAt(j);

                if (key == 'O') {
                    if (objectIndex < objects.size()) {
                        // Get the default item from the menu
                        EssenceMenuElement element = findElementByKey('O');
                        if (element != null) {
                            // Copy the default item meta (name and lore)
                            ItemStack defaultItem = element.getDefaultItem();
                            ItemStack objectItem = convertObjectToItemStack(objects.get(objectIndex));
                            ItemMeta meta = defaultItem.getItemMeta();

                            if (meta != null) {
                                // Replace the type of the default item with the new object's type
                                objectItem.setItemMeta(meta); // Keep name and lore
                            }

                            inventory.setItem(i * 9 + j, objectItem); // Place in inventory
                        }
                        objectIndex++;
                    } else {
                        // When there are no more objects, leave the slot empty or fill with a placeholder (optional)
                        inventory.setItem(i * 9 + j, null); // You can use a placeholder ItemStack if needed
                    }
                } else {
                    EssenceMenuElement element = findElementByKey(key);
                    if (element != null) {
                        inventory.setItem(i * 9 + j, element.getDefaultItem());
                    }
                }
            }
        }
    }

    // Convert the object to an ItemStack
    protected abstract ItemStack convertObjectToItemStack(T object);

    private EssenceMenuElement findElementByKey(char key) {
        return configurableMenu.getElements().stream()
                .filter(element -> element.getKey() == key)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void display(User user) {
        openMenu(user);
    }

    public void openMenu(User user) {
        Player player = user.getPlayer();
        if (player != null && player.isOnline()) {
            player.openInventory(inventory);
        }
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
}
