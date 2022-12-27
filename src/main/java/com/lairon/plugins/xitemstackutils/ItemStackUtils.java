package com.lairon.plugins.xitemstackutils;

import com.google.common.collect.Multimap;
import lombok.NonNull;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ItemStackUtils extends JavaPlugin {

    @NonNull
    public static ItemStack setDisplayName(@NonNull ItemStack stack, @NonNull Component displayName) {
        ItemMeta meta = stack.getItemMeta();
        meta.displayName(displayName);
        stack.setItemMeta(meta);
        return stack;
    }

    @NonNull
    public static ItemStack setDisplayName(ItemStack stack, @NonNull String displayName) {
        return setDisplayName(stack, toComponentAndChatColor(displayName));
    }

    @NonNull
    public static ItemStack removeDisplayName(@NonNull ItemStack itemStack) {
        return setDisplayName(itemStack, Component.empty());
    }

    @NonNull
    public static ItemStack setLoreComponent(@NonNull ItemStack stack, @NonNull List<Component> lore) {
        ItemMeta meta = stack.getItemMeta();
        meta.lore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    @NonNull
    public static ItemStack setLore(@NonNull ItemStack stack, @NonNull List<String> lore) {
        List<Component> components = new ArrayList<>();
        lore.forEach(s -> components.add(toComponentAndChatColor(s)));
        return setLoreComponent(stack, components);
    }

    @NonNull
    public static ItemStack addLoreComponent(@NonNull ItemStack stack, @NonNull List<Component> lore) {
        List<Component> loreComponents = stack.getItemMeta().lore();
        if (loreComponents == null) loreComponents = new ArrayList<>();
        loreComponents.addAll(lore);
        return setLoreComponent(stack, loreComponents);
    }

    @NonNull
    public static ItemStack addLore(@NonNull ItemStack stack, @NonNull List<String> lore) {
        List<Component> loreComponents = new ArrayList<>();
        lore.forEach(s -> loreComponents.add(toComponentAndChatColor(s)));
        return addLoreComponent(stack, loreComponents);
    }

    @NonNull
    public static Component toComponentAndChatColor(@NonNull String str) {
        return Component.text(ChatColor.translateAlternateColorCodes('&', str));
    }

    @NonNull
    public static ItemStack setCustomModelData(@NonNull ItemStack stack, @NonNull int cmd) {
        ItemMeta meta = stack.getItemMeta();
        meta.setCustomModelData(cmd);
        stack.setItemMeta(meta);
        return stack;
    }

    @NonNull
    public static ItemStack addEnchant(@NonNull ItemStack stack, @NonNull Enchantment enchantment, @NonNull int level, @NonNull boolean ignoreLevelRestriction) {
        ItemMeta meta = stack.getItemMeta();
        meta.addEnchant(enchantment, level, ignoreLevelRestriction);
        stack.setItemMeta(meta);
        return stack;
    }

    @NonNull
    public static ItemStack addEnchant(@NonNull ItemStack stack, @NonNull Enchantment enchantment, @NonNull int level) {
        return addEnchant(stack, enchantment, level, false);
    }

    @NonNull
    public static ItemStack removeEnchant(@NonNull ItemStack stack, @NonNull Enchantment enchantment) {
        ItemMeta meta = stack.getItemMeta();
        meta.removeEnchant(enchantment);
        stack.setItemMeta(meta);
        return stack;
    }

    @NonNull
    public static ItemStack setAttributeModifier(@NonNull ItemStack stack, @NonNull Multimap<Attribute, AttributeModifier> attributeModifiers) {
        ItemMeta meta = stack.getItemMeta();
        meta.setAttributeModifiers(attributeModifiers);
        stack.setItemMeta(meta);
        return stack;
    }

    @NonNull
    public static ItemStack addAttributeModifier(@NonNull ItemStack stack, @NonNull Attribute attribute, @NonNull AttributeModifier attributeModifier) {
        ItemMeta meta = stack.getItemMeta();
        meta.addAttributeModifier(attribute, attributeModifier);
        stack.setItemMeta(meta);
        return stack;
    }

    @NonNull
    public static ItemStack removeAttributeModifier(@NonNull ItemStack stack, @NonNull Attribute attribute) {
        ItemMeta meta = stack.getItemMeta();
        meta.removeAttributeModifier(attribute);
        stack.setItemMeta(meta);
        return stack;
    }

    @NonNull
    public static ItemStack setUnbreakable(@NonNull ItemStack stack, @NonNull boolean unbreakable) {
        ItemMeta meta = stack.getItemMeta();
        meta.setUnbreakable(unbreakable);
        stack.setItemMeta(meta);
        return stack;
    }

    @NonNull
    public static ItemStack addItemFlags(@NonNull ItemStack stack, @NonNull ItemFlag... itemFlags) {
        ItemMeta meta = stack.getItemMeta();
        meta.addItemFlags(itemFlags);
        stack.setItemMeta(meta);
        return stack;
    }

    @NonNull
    public static ItemStack removeItemFlags(@NonNull ItemStack stack, @NonNull ItemFlag... flags) {
        ItemMeta meta = stack.getItemMeta();
        meta.removeItemFlags(flags);
        stack.setItemMeta(meta);
        return stack;
    }

    @NonNull
    public static ItemStack setPersistentData(@NonNull ItemStack stack,
                                              @NonNull NamespacedKey key,
                                              @NonNull PersistentDataType dataType,
                                              @NonNull Object value) {
        ItemMeta meta = stack.getItemMeta();
        meta.getPersistentDataContainer().set(key, dataType, value);
        stack.setItemMeta(meta);
        return stack;
    }

    @NonNull
    public static ItemStack removePersistentData(@NonNull ItemStack stack, @NonNull NamespacedKey key){
        ItemMeta meta = stack.getItemMeta();
        meta.getPersistentDataContainer().remove(key);
        stack.setItemMeta(meta);
        return stack;
    }

    @NonNull
    public static Map<String, Object> serialize(@NonNull ItemStack stack){
        return ItemStackSerializerDeserializer.serialize(stack);
    }

    @NonNull
    public static ItemStack deserialize(ConfigurationSection section){
        return ItemStackSerializerDeserializer.deserialize(section);
    }

}
