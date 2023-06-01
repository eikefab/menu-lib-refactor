package com.eikefab.menulib;

import com.eikefab.menulib.utils.ItemBuilder;
import com.google.common.collect.ImmutableMap;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class MenuContext {

    private final Menu menu;
    private Map<String, Object> arguments;
    private Map<Integer, MenuItem> items;

    public MenuContext(Menu menu, Map<String, Object> arguments, Map<Integer, MenuItem> items) {
        this.menu = menu;
        this.arguments = arguments;
        this.items = items;
    }

    public MenuContext(Menu menu, Map<String, Object> arguments) {
        this(menu, arguments, new HashMap<>());
    }

    public MenuContext(Menu menu) {
        this(menu, ImmutableMap.of(), new HashMap<>());
    }

    public Menu getMenu() {
        return menu;
    }

    public Map<String, Object> getArguments() {
        return arguments;
    }

    public void setArguments(Map<String, Object> arguments) {
        this.arguments = arguments;
    }

    public Object getArgument(String key) {
        return arguments.getOrDefault(key, null);
    }

    public Map<Integer, MenuItem> getItems() {
        return items;
    }

    public void setItems(Map<Integer, MenuItem> items) {
        this.items = items;
    }

    public MenuItem getItem(int slot) {
        return items.getOrDefault(slot, null);
    }

    public MenuContext setItem(int slot, MenuItem menuItem) {
        items.put(slot, menuItem);

        return this;
    }

    public MenuContext setItem(int slot, ItemStack itemStack) {
        return setItem(slot, new MenuItem(itemStack));
    }

    public MenuContext setItem(int slot, ItemBuilder itemBuilder) {
        return setItem(slot, new MenuItem(itemBuilder.build()));
    }

    public MenuContext addItems(Inventory inventory) {
        for (Map.Entry<Integer, MenuItem> entry : items.entrySet()) {
            final int slot = entry.getKey();
            final ItemStack itemStack = entry.getValue().getItemStack();

            inventory.setItem(slot, itemStack);
        }

        return this;
    }

}
