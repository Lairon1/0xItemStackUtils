package com.lairon.plugins.xitemstackutils.sd.deserializer.meta;

import com.lairon.plugins.xitemstackutils.ItemStackUtils;
import com.lairon.plugins.xitemstackutils.sd.deserializer.ItemMetaDeserializer;
import lombok.NonNull;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

import static com.lairon.plugins.xitemstackutils.sd.ItemStackSections.ENCHANTS;

public class EnchantsDeserializer implements ItemMetaDeserializer {

    @Override
    public void deserialize(@NonNull ItemStack stack, @NonNull ConfigurationSection section) {
        if (!section.contains(ENCHANTS)) return;
        if (!section.isConfigurationSection(ENCHANTS))
            throw new IllegalArgumentException("Section " + ENCHANTS + " is not a section");

        for (String enchString : section.getConfigurationSection(ENCHANTS).getKeys(false)) {
            Enchantment enchantment = Arrays
                    .stream(Enchantment.values())
                    .filter(enchantment1 -> enchantment1.getKey().getKey().equalsIgnoreCase(enchString))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Enchant " + enchString + " not found"));

            if(!section.isInt(ENCHANTS + "." + enchString))
                throw new IllegalArgumentException("Level for enchant " + enchString + " is not a integer");

            ItemStackUtils.addEnchant(stack, enchantment, section.getInt(ENCHANTS + "." + enchString), true);
        }
    }

}
