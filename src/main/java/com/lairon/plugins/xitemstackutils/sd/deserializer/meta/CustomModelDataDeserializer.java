package com.lairon.plugins.xitemstackutils.sd.deserializer.meta;

import com.lairon.plugins.xitemstackutils.ItemStackUtils;
import com.lairon.plugins.xitemstackutils.sd.deserializer.ItemMetaDeserializer;
import lombok.NonNull;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import static com.lairon.plugins.xitemstackutils.sd.ItemStackSections.CUSTOM_MODEL_DATA;


public class CustomModelDataDeserializer implements ItemMetaDeserializer {

    @Override
    public void deserialize(@NonNull ItemStack stack, @NonNull ConfigurationSection section) {
        if (!section.contains(CUSTOM_MODEL_DATA)) return;
        if (!section.isInt(CUSTOM_MODEL_DATA))
            throw new IllegalArgumentException("Section " + CUSTOM_MODEL_DATA + " is not a integer");
        ItemStackUtils.setCustomModelData(stack, section.getInt(CUSTOM_MODEL_DATA));
    }

}
