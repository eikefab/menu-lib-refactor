package com.eikefab.menulib;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class MenuHolder implements InventoryHolder {

    private final MenuContext menuContext;

    public MenuHolder(MenuContext menuContext) {
        this.menuContext = menuContext;
    }

    public MenuContext getMenuContext() {
        return menuContext;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }

}
