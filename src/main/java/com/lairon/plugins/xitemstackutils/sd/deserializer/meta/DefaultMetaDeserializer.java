package com.lairon.plugins.xitemstackutils.sd.deserializer.meta;

import com.lairon.plugins.xitemstackutils.ItemStackUtils;
import com.lairon.plugins.xitemstackutils.sd.deserializer.ItemMetaDeserializer;
import lombok.NonNull;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

import static com.lairon.plugins.xitemstackutils.sd.ItemStackSections.*;

public class DefaultMetaDeserializer implements ItemMetaDeserializer {

    @Override
    public void deserialize(@NonNull ItemStack stack, @NonNull ConfigurationSection section) {
        deserializeName(stack, section);
        deserializeLore(stack, section);
        deserializeEnchants(stack, section);
        deserializeFlags(stack, section);
        deserializeUnbreakable(stack, section);
        deserializeCustomModelData(stack, section);
    }

    private void deserializeCustomModelData(@NonNull ItemStack stack, @NonNull ConfigurationSection section) {
        if (!section.contains(CUSTOM_MODEL_DATA)) return;
        if (!section.isInt(CUSTOM_MODEL_DATA))
            throw new IllegalArgumentException("Section " + CUSTOM_MODEL_DATA + " is not a integer");
        ItemStackUtils.setCustomModelData(stack, section.getInt(CUSTOM_MODEL_DATA));
    }

    private void deserializeEnchants(@NonNull ItemStack stack, @NonNull ConfigurationSection section) {
        if (!section.contains(ENCHANTS)) return;
        if (!section.isConfigurationSection(ENCHANTS))
            throw new IllegalArgumentException("Section " + ENCHANTS + " is not a section");

        for (String enchString : section.getConfigurationSection(ENCHANTS).getKeys(false)) {
            Enchantment enchantment = Enchantment.getByName(enchString.toUpperCase());
            if(enchantment == null)
                new IllegalArgumentException("Enchant " + enchString + " not found");
            if(!section.isInt(ENCHANTS + "." + enchString))
                throw new IllegalArgumentException("Level for enchant " + enchString + " is not a integer");

            ItemStackUtils.addEnchant(stack, enchantment, section.getInt(ENCHANTS + "." + enchString), true);
        }
    }

    private void deserializeFlags(@NonNull ItemStack stack, @NonNull ConfigurationSection section) {
        if (!section.contains(FLAGS)) return;
        if (!section.isList(FLAGS))
            throw new IllegalArgumentException("Section " + FLAGS + " is not a list");
        for (String stringFlag : section.getStringList(FLAGS)) {
            ItemFlag itemFlag = null;
            try {
                itemFlag = ItemFlag.valueOf(stringFlag.toUpperCase());
            }catch (Exception e){
                throw new IllegalArgumentException("Item flag " + stringFlag + " not found");
            }
            ItemStackUtils.addItemFlags(stack, itemFlag);
        }
    }

    private void deserializeLore(@NonNull ItemStack stack, @NonNull ConfigurationSection section) {
        if (!section.contains(LORE)) return;
        if (!section.isList(LORE))
            throw new IllegalArgumentException("Section " + LORE + " is not a list");
        ItemStackUtils.setLore(stack, section.getStringList(LORE));
    }

    private void deserializeName(@NonNull ItemStack stack, @NonNull ConfigurationSection section) {
        if (!section.contains(NAME)) return;
        if (!section.isString(NAME))
            throw new IllegalArgumentException("Section " + NAME + " is not a string");
        ItemStackUtils.setDisplayName(stack, section.getString(NAME));
    }

    private void deserializeUnbreakable(@NonNull ItemStack stack, @NonNull ConfigurationSection section) {
        if (!section.contains(UNBREAKABLE)) return;
        if(!section.isBoolean(UNBREAKABLE))
            throw new IllegalArgumentException("Section " + UNBREAKABLE + " is not a boolean");
        ItemStackUtils.setUnbreakable(stack, section.getBoolean(UNBREAKABLE));
    }

}
