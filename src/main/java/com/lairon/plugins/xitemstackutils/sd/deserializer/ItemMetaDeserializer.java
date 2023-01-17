package com.lairon.plugins.xitemstackutils.sd.deserializer;

import lombok.NonNull;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public interface ItemMetaDeserializer {
    void deserialize(@NonNull ItemStack stack,@NonNull ConfigurationSection section);
}
