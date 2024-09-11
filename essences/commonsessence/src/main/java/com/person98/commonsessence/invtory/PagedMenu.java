package com.person98.commonsessence.invtory;

import com.person98.commonsessence.invtory.Menu;
import com.person98.commonsessence.invtory.menu.ConfigurableMenu;
import com.person98.commonsessence.user.User;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class PagedMenu<T> extends Menu<T> {
    protected final int pageSize;
    protected final List<T> elements;
    protected int currentPage;

    public PagedMenu(ConfigurableMenu configurableMenu, User user, List<T> elements, int pageSize) {
        super(configurableMenu, user);
        this.elements = elements;
        this.pageSize = pageSize;
        this.currentPage = 0;
    }

    @Override
    public void openMenu(User user) {
        displayPage(user, currentPage);
    }

    private void displayPage(User user, int page) {
        Player player = user.getPlayer();
        Inventory inventory = getInventory();

        if (player != null && player.isOnline()) {
            inventory.clear();

            int start = page * pageSize;
            int end = Math.min(start + pageSize, elements.size());

            for (int i = start; i < end; i++) {
                T object = elements.get(i);
                ItemStack item = convertObjectToItemStack(object);
                inventory.addItem(item);
            }

            player.openInventory(inventory);
        }
    }

    public void nextPage(User user) {
        if ((currentPage + 1) * pageSize < elements.size()) {
            currentPage++;
            displayPage(user, currentPage);
        }
    }

    public void previousPage(User user) {
        if (currentPage > 0) {
            currentPage--;
            displayPage(user, currentPage);
        }
    }
}
