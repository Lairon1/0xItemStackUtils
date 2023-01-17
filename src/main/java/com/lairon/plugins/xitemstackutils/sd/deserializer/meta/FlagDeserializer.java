package com.lairon.plugins.xitemstackutils.sd.deserializer.meta;

import com.lairon.plugins.xitemstackutils.ItemStackUtils;
import com.lairon.plugins.xitemstackutils.sd.deserializer.ItemMetaDeserializer;
import lombok.NonNull;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import static com.lairon.plugins.xitemstackutils.sd.ItemStackSections.FLAGS;


public class FlagDeserializer implements ItemMetaDeserializer {

    @Override
    public void deserialize(@NonNull ItemStack stack, @NonNull ConfigurationSection section) {
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

}
