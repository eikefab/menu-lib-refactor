package com.eikefab.menulib;

import com.eikefab.menulib.utils.ItemBuilder;
import com.eikefab.menulib.utils.MenuUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class PageMenuContext<T> extends MenuContext {

    private final List<T> contents;
    private final int count;
    private final int maxIndex;
    private int currentIndex;

    private MenuItem nextPageItem;
    private MenuItem previousPageItem;

    public PageMenuContext(Menu menu, Map<String, Object> arguments, Map<Integer, MenuItem> items, List<T> contents, int count, int maxIndex) {
        super(menu, arguments, items);

        this.contents = contents;
        this.count = count;
        this.maxIndex = maxIndex;
        this.currentIndex = 0;
    }

    public PageMenuContext(Menu menu, Map<String, Object> arguments, List<T> contents, int count, int maxIndex) {
        this(menu, arguments, new HashMap<>(), contents, count, maxIndex);
    }

    public PageMenuContext(Menu menu, List<T> contents, int count, int maxIndex) {
        this(menu, new HashMap<>(), contents, count, maxIndex);
    }

    public int getMaxIndex() {
        return maxIndex;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public boolean hasNextPage() {
        return currentIndex < maxIndex;
    }

    public boolean hasPreviousPage() {
        return currentIndex > 0;
    }

    public void setNextPageItem(int slot, MenuItem nextPageItem) {
        this.nextPageItem = nextPageItem;

        getItems().put(slot, nextPageItem);
    }

    public void setNextPageItem(int slot, ItemBuilder itemBuilder) {
        setNextPageItem(slot, new MenuItem(itemBuilder.build()));
    }

    public MenuItem getNextPageItem() {
        return nextPageItem;
    }

    public void setPreviousPageItem(int slot, MenuItem previousPageItem) {
        this.previousPageItem = previousPageItem;

        getItems().put(slot, previousPageItem);
    }

    public void setPreviousPageItem(int slot, ItemBuilder itemBuilder) {
        setPreviousPageItem(slot, new MenuItem(itemBuilder.build()));
    }

    public MenuItem getPreviousPageItem() {
        return previousPageItem;
    }

    public List<T> getPage() {
        final int size = contents.size();

        int fromIndex = currentIndex * count;

        if (fromIndex > size) {
            fromIndex = 0;
        }

        int toIndex = (currentIndex + 1) * count;

        if (toIndex > size) {
            toIndex = size;
        }

        return contents.subList(fromIndex, toIndex);
    }

    public List<T> getNextPage() {
        if (hasNextPage()) {
            currentIndex++;
        }

        return getPage();
    }

    public List<T> getPreviousPage() {
        if (hasPreviousPage()) {
            currentIndex--;
        }

        return getPage();
    }

    public void fill(int start, int end) {
        MenuUtils.fillCenter(this, start, end, getContent());
    }

    public List<T> getCurrentPage() {
        return getPage();
    }

    public List<MenuItem> getContent() {
        final List<MenuItem> menuItems = new ArrayList<>();
        final List<T> content = getPage();
        final PageMenu<T> menu = getMenu();

        for (int index = 0; index < content.size(); index++) {
            final T item = content.get(index);
            final MenuItem menuItem = menu.adapt(item, index);

            if (menuItem == null) {
                continue;
            }

            final ItemStack itemStack = menuItem.getItemStack();

            if (itemStack == null || itemStack.getType() == Material.AIR) {
                continue;
            }

            menuItems.add(menuItem);
        }

        return menuItems;
    }

    @Override
    public PageMenu<T> getMenu() {
        return (PageMenu<T>) super.getMenu();
    }
}
