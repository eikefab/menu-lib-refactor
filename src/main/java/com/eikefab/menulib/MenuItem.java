package com.eikefab.menulib;

import com.eikefab.menulib.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class MenuItem {

    private final ItemStack itemStack;
    private Consumer<InventoryClickEvent> eventConsumer;

    public MenuItem(ItemStack itemStack, Consumer<InventoryClickEvent> eventConsumer) {
        this.itemStack = itemStack;
        this.eventConsumer = eventConsumer;
    }

    public MenuItem(ItemStack itemStack) {
        this(itemStack, (event) -> {});
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Consumer<InventoryClickEvent> getEventConsumer() {
        return eventConsumer;
    }

    public MenuItem click(Consumer<InventoryClickEvent> eventConsumer) {
        this.eventConsumer = eventConsumer;

        return this;
    }

    public static MenuItem of(ItemStack itemStack) {
        return new MenuItem(itemStack);
    }

    public static MenuItem of(ItemBuilder itemBuilder) {
        return new MenuItem(itemBuilder.build());
    }

    public static MenuItem of(Material material) {
        return new MenuItem(new ItemStack(material));
    }

}
