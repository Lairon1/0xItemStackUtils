package com.lairon.plugins.xitemstackutils;

import lombok.NonNull;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ItemStackSerializerDeserializer {

    private static final String TYPE = "Type";
    private static final String NAME = "Name";
    private static final String LORE = "Lore";
    private static final String ENCHANTS = "Enchants";
    private static final String FLAGS = "ItemFlags";
    private static final String CUSTOM_MODEL_DATA = "CustomModelData";
    private static final String AMOUNT = "Amount";

    @NonNull
    public static Map<String, Object> serialize(@NonNull ItemStack stack) {
        Map<String, Object> map = new HashMap<>();
        map.put(TYPE, stack.getType().name());
        map.put(AMOUNT, stack.getAmount());
        if (stack.hasItemMeta()) {
            ItemMeta meta = stack.getItemMeta();
            if (meta.hasDisplayName())
                map.put(NAME, meta.getDisplayName());
            if (meta.hasLore()) {
                map.put(LORE, meta.getLore());
            }
            if (meta.hasEnchants()) {
                List<String> enchs = new ArrayList<>();
                meta.getEnchants().entrySet().forEach(enchantmentIntegerEntry -> {
                    enchs.add(enchantmentIntegerEntry.getKey().getKey().getKey() + ";" + enchantmentIntegerEntry.getValue());
                });
                map.put(ENCHANTS, enchs);
            }
            Set<ItemFlag> itemFlags = meta.getItemFlags();
            if (itemFlags.size() > 0) {
                List<String> flags = new ArrayList<>();
                for (ItemFlag itemFlag : itemFlags) {
                    flags.add(itemFlag.name());
                }
                map.put(FLAGS, flags);
            }
            if(meta.hasCustomModelData()){
                map.put(CUSTOM_MODEL_DATA, meta.getCustomModelData());
            }
        }
        return map;
    }

    @NonNull
    public static ItemStack deserialize(@NonNull ConfigurationSection section) {
        ItemStack stack = new ItemStack(Material.valueOf(section.getString(TYPE, Material.STONE.name())));
        if (section.contains(NAME))
            ItemStackUtils.setDisplayName(stack, color(section.getString(NAME)));
        if (section.contains(LORE))
            ItemStackUtils.setLore(stack, color(section.getStringList(LORE)));
        if (section.contains(ENCHANTS)) {
            for (String ench : section.getStringList(ENCHANTS)) {
                String[] split = ench.split(";");
                Enchantment enchantment = Arrays.stream(Enchantment
                                .values())
                        .filter(enchantment1 -> enchantment1.getKey().getKey().equalsIgnoreCase(split[0]))
                        .findFirst()
                        .orElse(Enchantment.DURABILITY);
                int level = Integer.parseInt(split[1]);
                ItemStackUtils.addEnchant(stack, enchantment, level);
            }
        }
        if(section.contains(FLAGS)){
            List<String> flags = section.getStringList(FLAGS);
            for (String flag : flags) {
                ItemStackUtils.addItemFlags(stack, ItemFlag.valueOf(flag));
            }
        }
        if(section.contains(CUSTOM_MODEL_DATA)){
            ItemStackUtils.setCustomModelData(stack, section.getInt(CUSTOM_MODEL_DATA));
        }
        if(section.contains(AMOUNT)){
            stack.setAmount(section.getInt(AMOUNT));
        }
        return stack;
    }

    private static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    private static List<String> color(List<String> list) {
        list = new ArrayList<>(list);
        for (int i = 0; i < list.size(); i++) {
            list.set(i, color(list.get(i)));
        }
        return list;
    }

}
