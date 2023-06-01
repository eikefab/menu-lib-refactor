package com.eikefab.menulib;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public final class MenuLib implements Listener {

    @EventHandler
    private void menu(InventoryClickEvent event) {
        final Inventory inventory = event.getInventory();
        final InventoryHolder inventoryHolder = inventory.getHolder();
        final ItemStack itemStack = event.getCurrentItem();

        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return;
        }

        if (!(inventoryHolder instanceof MenuHolder)) {
            return;
        }

        event.setCancelled(true);

        final MenuHolder menuHolder = (MenuHolder) inventoryHolder;
        final MenuContext menuContext = menuHolder.getMenuContext();
        final MenuItem menuItem = menuContext.getItem(event.getSlot());

        if (menuItem == null) {
            return;
        }

        menuItem.getEventConsumer().accept(event);
    }

    @EventHandler
    private void page(InventoryClickEvent event) {
        final Inventory inventory = event.getInventory();
        final InventoryHolder inventoryHolder = inventory.getHolder();
        final ItemStack itemStack = event.getCurrentItem();

        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return;
        }

        if (!(inventoryHolder instanceof PageMenuHolder)) {
            return;
        }

        final Player player = (Player) event.getWhoClicked();

        final PageMenuHolder<?> menuHolder = (PageMenuHolder<?>) inventoryHolder;
        final PageMenuContext<?> menuContext = menuHolder.getMenuContext();
        final PageMenu<?> pageMenu = menuContext.getMenu();

        final MenuItem nextPageItem = menuContext.getNextPageItem();
        final MenuItem previousPageItem = menuContext.getPreviousPageItem();

        final int page = menuContext.getCurrentIndex();

        if (itemStack.isSimilar(nextPageItem.getItemStack()) && menuContext.hasNextPage()) {
            pageMenu.open(player, menuContext.getArguments(), page + 1);
        } else if (itemStack.isSimilar(previousPageItem.getItemStack()) && menuContext.hasPreviousPage()) {
            pageMenu.open(player, menuContext.getArguments(), page - 1);
        }
    }


    public static void register(Plugin plugin) {
        final PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new MenuLib(), plugin);
    }

}
