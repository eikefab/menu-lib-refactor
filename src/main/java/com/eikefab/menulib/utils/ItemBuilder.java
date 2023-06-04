package com.eikefab.menulib.utils;

import com.google.common.collect.ImmutableMap;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ItemBuilder {

    public static char ALT_COLOR_CHAR = '&';

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    public ItemBuilder(Material material, int amount) {
        this.itemStack = new ItemStack(material, amount);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder modifyItem(Consumer<ItemStack> consumer) {
        consumer.accept(itemStack);

        return this;
    }

    public ItemBuilder modifyMeta(Consumer<ItemMeta> consumer) {
        consumer.accept(itemMeta);

        return this;
    }

    public ItemBuilder name(String name) {
        return modifyMeta(
                (meta) -> {
                    meta.setDisplayName(colorize(name));
                }
        );
    }

    public ItemBuilder lore(List<String> lore) {
        return modifyMeta(
                (meta) -> {
                    meta.setLore(colorize(lore));
                }
        );
    }

    public ItemBuilder lore(String... lore) {
        return modifyMeta(
                (meta) -> {
                    meta.setLore(colorize(Arrays.asList(lore)));
                }
        );
    }

    public ItemBuilder enchant(Map<Enchantment, Integer> enchantments) {
        return modifyMeta(
                (meta) -> {
                    for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                        final Enchantment enchantment = entry.getKey();
                        final int level = entry.getValue();

                        meta.addEnchant(enchantment, level, true);
                    }
                }
        );
    }

    public ItemBuilder enchant(Enchantment enchantment, int level) {
        return enchant(ImmutableMap.of(enchantment, level));
    }

    public ItemBuilder flag(ItemFlag... flags) {
        return modifyMeta(
                (meta) -> {
                    meta.addItemFlags(flags);
                }
        );
    }

    public ItemBuilder head(String owner) {
        return modifyMeta(
                (meta) -> {
                    modifyItem(
                            (item) -> {
                                item.setDurability((short) 3);
                            }
                    );

                    final SkullMeta skullMeta = (SkullMeta) meta;

                    skullMeta.setOwner(owner);
                }
        );
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    private String colorize(String text) {
        return ChatColor.translateAlternateColorCodes(ALT_COLOR_CHAR, text);
    }

    private List<String> colorize(List<String> list) {
        return list.stream().map(this::colorize).collect(Collectors.toList());
    }

}
