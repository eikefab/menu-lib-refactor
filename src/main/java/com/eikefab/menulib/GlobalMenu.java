package com.eikefab.menulib;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Map;

public abstract class GlobalMenu extends Menu {

    private final MenuContext menuContext;
    private final Inventory inventory;

    public GlobalMenu(String title, int size) {
        super(title, size);

        this.menuContext = new MenuContext(this);
        this.inventory = Bukkit.createInventory(new MenuHolder(menuContext), size, title);
    }

    protected abstract void build(MenuContext context);

    public void update() {
        build(menuContext);
        menuContext.addItems(inventory);
    }

    @Override
    protected void build(Player player, MenuContext context) {
        build(context);
    }

    public void open(Player player, boolean update) {
        if (update) {
            update();
        }

        player.openInventory(inventory);
    }

    @Override
    public void open(Player player) {
        open(player, true);
    }

    @Override
    public void open(Player player, Map<String, Object> arguments) {
        open(player, true);
    }

}
