package com.lairon.plugins.xitemstackutils.sd.deserializer.meta;

import com.lairon.plugins.xitemstackutils.ItemStackUtils;
import com.lairon.plugins.xitemstackutils.sd.deserializer.ItemMetaDeserializer;
import lombok.NonNull;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import static com.lairon.plugins.xitemstackutils.sd.ItemStackSections.UNBREAKABLE;


public class UnbreakableDeserializer implements ItemMetaDeserializer {
    @Override
    public void deserialize(@NonNull ItemStack stack, @NonNull ConfigurationSection section) {
        if (!section.contains(UNBREAKABLE)) return;
        if(!section.isBoolean(UNBREAKABLE))
            throw new IllegalArgumentException("Section " + UNBREAKABLE + " is not a boolean");
        ItemStackUtils.setUnbreakable(stack, section.getBoolean(UNBREAKABLE));
    }
}
