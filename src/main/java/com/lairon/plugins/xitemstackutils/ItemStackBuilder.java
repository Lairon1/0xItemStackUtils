package com.lairon.plugins.xitemstackutils;

import lombok.NonNull;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class ItemStackBuilder {

    private final ItemStack stack;

    public ItemStackBuilder(@NonNull Material material) {
        stack = new ItemStack(material);
    }

    public ItemStackBuilder(@NonNull ItemStack stack) {
        this.stack = stack;
    }

    @NonNull
    public ItemStackBuilder type(@NonNull Material material) {
        stack.setType(material);
        return this;
    }

    @NonNull
    public ItemStackBuilder amount(@NonNull int amount) {
        stack.setAmount(amount);
        return this;
    }

    @NonNull
    public ItemStackBuilder data(@NonNull MaterialData data) {
        stack.setData(data);
        return this;
    }

    @NonNull
    public ItemStackBuilder durability(@NonNull short durability) {
        stack.setDurability(durability);
        return this;
    }

    @NonNull
    public ItemStackBuilder displayName(@NonNull String displayName) {
        ItemStackUtils.setDisplayName(stack, displayName);
        return this;
    }

    @NonNull
    public ItemStackBuilder displayName(@NonNull Component displayName) {
        ItemStackUtils.setDisplayName(stack, displayName);
        return this;
    }

    @NonNull
    public ItemStackBuilder lore(@NonNull String... lore) {
        ItemStackUtils.setLore(stack, List.of(lore));
        return this;
    }

    @NonNull
    public ItemStackBuilder lore(@NonNull Component... loreComp) {
        ItemStackUtils.setLoreComponent(stack, List.of(loreComp));
        return this;
    }

    @NonNull
    public ItemStackBuilder customModelData(@NonNull int cmd) {
        ItemStackUtils.setCustomModelData(stack, cmd);
        return this;
    }

    @NonNull
    public ItemStackBuilder enchantment(@NonNull Enchantment enchantment, @NonNull int level, @NonNull boolean ignoreLevelRestriction) {
        ItemStackUtils.addEnchant(stack, enchantment, level, ignoreLevelRestriction);
        return this;
    }

    @NonNull
    public ItemStackBuilder enchantment(@NonNull Enchantment enchantment, @NonNull int level) {
        ItemStackUtils.addEnchant(stack, enchantment, level);
        return this;
    }

    @NonNull
    public ItemStackBuilder attributeModifier(Attribute attribute, AttributeModifier attributeModifier) {
        ItemStackUtils.addAttributeModifier(stack, attribute, attributeModifier);
        return this;
    }

    @NonNull
    public ItemStackBuilder unbreakable(@NonNull boolean unbreakable) {
        ItemStackUtils.setUnbreakable(stack, unbreakable);
        return this;
    }

    @NonNull
    public ItemStackBuilder itemFlags(@NonNull ItemFlag... itemFlags) {
        ItemStackUtils.addItemFlags(stack, itemFlags);
        return this;
    }

    @NonNull
    public ItemStackBuilder persistentData(@NonNull NamespacedKey key,
                                           @NonNull PersistentDataType dataType,
                                           @NonNull Object value) {
        ItemStackUtils.setPersistentData(stack, key, dataType, value);
        return this;
    }


    @NonNull
    public ItemStack build() {
        return stack.clone();
    }

    @NonNull
    public ItemStack create() {
        return build();
    }

}
