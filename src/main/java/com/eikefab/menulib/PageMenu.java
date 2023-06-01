package com.eikefab.menulib;

import com.google.common.collect.ImmutableMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.Map;

public abstract class PageMenu<T> extends Menu {

    public PageMenu(String title, int size) {
        super(title, size);
    }

    protected abstract MenuItem adapt(T item);
    protected abstract List<T> content();
    protected abstract int itemCount();
    protected int maxIndex() {
        return ((int) Math.ceil((float) content().size() / itemCount())) - 1;
    }
    protected abstract void build(PageMenuContext<T> context);

    @Override
    protected void build(Player player, MenuContext context) {
        final PageMenuContext<T> pageMenuContext = new PageMenuContext<>(
                this,
                context.getArguments(),
                content(),
                itemCount(),
                maxIndex()
        );

        build(pageMenuContext);
    }

    public void open(Player player, Map<String, Object> arguments, int index) {
        final int maxIndex = maxIndex();

        final PageMenuContext<T> context = new PageMenuContext<>(
                this,
                arguments,
                content(),
                itemCount(),
                maxIndex
        );

        context.setCurrentIndex(index);
        build(context);

        final Inventory inventory = Bukkit.createInventory(
                new PageMenuHolder<>(context, maxIndex, index),
                getSize(),
                getTitle()
        );

        context.addItems(inventory);

        player.openInventory(inventory);
    }

    @Override
    public void open(Player player, Map<String, Object> arguments) {
        open(player, arguments, 0);
    }

    public void open(Player player, int index) {
        open(player, ImmutableMap.of(), index);
    }

    @Override
    public void open(Player player) {
        open(player, ImmutableMap.of(), 0);
    }

}
