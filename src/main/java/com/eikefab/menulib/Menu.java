package com.eikefab.menulib;

import com.google.common.collect.ImmutableMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Map;

public abstract class Menu {

    private String title;
    private final int size;

    public Menu(String title, int size) {
        this.title = title;
        this.size = size;

        if (size % 9 != 0) {
            throw new IllegalArgumentException("Size must be multiple of 9.");
        }

        if (size > 54) {
            throw new IllegalArgumentException("Size must be lower than 54");
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSize() {
        return size;
    }

    protected abstract void build(Player player, MenuContext context);

    public void open(Player player, Map<String, Object> arguments) {
        final MenuContext menuContext = new MenuContext(this, arguments);

        build(player, menuContext);

        final Inventory inventory = Bukkit.createInventory(new MenuHolder(menuContext), size, title);

        menuContext.addItems(inventory);

        player.openInventory(inventory);
    }

    public void open(Player player) {
        open(player, ImmutableMap.of());
    }

}
