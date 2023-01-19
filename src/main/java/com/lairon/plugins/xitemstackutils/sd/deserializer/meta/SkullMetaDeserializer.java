package com.lairon.plugins.xitemstackutils.sd.deserializer.meta;

import com.lairon.plugins.xitemstackutils.sd.deserializer.ItemMetaDeserializer;
import lombok.NonNull;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class SkullMetaDeserializer implements ItemMetaDeserializer {

    @Override
    public void deserialize(@NonNull ItemStack stack, @NonNull ConfigurationSection section) {
        ItemMeta itemMeta = stack.getItemMeta();
        if (!(itemMeta instanceof SkullMeta skullMeta)) return;
        if (!section.contains("SkullMeta")) return;
        ConfigurationSection skullMetaSection = section.getConfigurationSection("SkullMeta");

        deserializeValue(skullMeta, skullMetaSection);
        deserializeOwner(skullMeta, skullMetaSection);

        stack.setItemMeta(skullMeta);
    }

    private void deserializeValue(@NonNull SkullMeta skullMeta, @NonNull ConfigurationSection section) {
        if(!section.contains("Value")) return;
        PersistentDataContainer pdc = skullMeta.getPersistentDataContainer();
        pdc.set(new NamespacedKey("minecraft", "skull_texture"), PersistentDataType.STRING, section.getString("Value"));
    }

    private void deserializeOwner(@NonNull SkullMeta skullMeta, @NonNull ConfigurationSection section) {
        if(!section.contains("Owner")) return;
        skullMeta.setOwner(section.getString("Owner"));
    }

}
