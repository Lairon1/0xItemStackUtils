package com.lairon.plugins.xitemstackutils.sd.deserializer.meta;

import com.lairon.plugins.xitemstackutils.ItemStackUtils;
import com.lairon.plugins.xitemstackutils.sd.deserializer.ItemMetaDeserializer;
import lombok.NonNull;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import static com.lairon.plugins.xitemstackutils.sd.ItemStackSections.LORE;

public class LoreDeserializer implements ItemMetaDeserializer {

    @Override
    public void deserialize(@NonNull ItemStack stack, @NonNull ConfigurationSection section) {
        if (!section.contains(LORE)) return;
        if (!section.isList(LORE))
            throw new IllegalArgumentException("Section " + LORE + " is not a list");
        ItemStackUtils.setLore(stack, section.getStringList(LORE));
    }

}
