package com.lairon.plugins.xitemstackutils.sd.deserializer;

import com.lairon.plugins.xitemstackutils.ItemStackUtils;
import com.lairon.plugins.xitemstackutils.sd.deserializer.meta.*;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;
import static com.lairon.plugins.xitemstackutils.sd.ItemStackSections.*;

public class ItemStackDeserializer {

    private static Set<ItemMetaDeserializer> itemMetaDeserializers = new HashSet<>(){{
        add(new NameDeserializer());
        add(new LoreDeserializer());
        add(new EnchantsDeserializer());
        add(new FlagDeserializer());
        add(new CustomModelDataDeserializer());
        add(new UnbreakableDeserializer());
    }};

    @NonNull
    public ItemStack deserialize(@NonNull ConfigurationSection section) {
        if(!section.contains(TYPE))
            throw new NullPointerException("Section " + TYPE + " is exists");
        if(!section.isString(TYPE))
            throw new IllegalArgumentException("Section " + TYPE + " is not string data type");
        String type = section.getString(TYPE);
        Material material = null;
        try{
            material = Material.valueOf(type.toUpperCase());
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Material " + type + " not found");
        }
        int amount = 1;

        if(section.contains(AMOUNT) && section.isInt(AMOUNT)){
            amount = section.getInt(AMOUNT);
        }

        ItemStack stack = new ItemStack(material, amount);

        itemMetaDeserializers.forEach(imd -> imd.deserialize(stack, section));

        return ItemStackUtils.applyColors(stack);
    }

    public static void addMetaDeserializer(@NonNull ItemMetaDeserializer itemMetaDeserializer){
        itemMetaDeserializers.add(itemMetaDeserializer);
    }
}
