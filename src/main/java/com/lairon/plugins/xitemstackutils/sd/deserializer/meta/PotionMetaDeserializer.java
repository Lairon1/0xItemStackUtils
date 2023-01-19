package com.lairon.plugins.xitemstackutils.sd.deserializer.meta;

import com.lairon.plugins.xitemstackutils.sd.deserializer.ItemMetaDeserializer;
import lombok.NonNull;
import org.bukkit.Color;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

import static com.lairon.plugins.xitemstackutils.sd.ItemStackSections.*;


public class PotionMetaDeserializer implements ItemMetaDeserializer {

    private static final Map<String, Color> colorNames = new HashMap<>(){{
       put("SILVER", Color.SILVER);
       put("GRAY", Color.GRAY);
       put("BLACK", Color.BLACK);
       put("RED", Color.RED);
       put("MAROON", Color.MAROON);
       put("YELLOW", Color.YELLOW);
       put("OLIVE", Color.OLIVE);
       put("LIME", Color.LIME);
       put("GREEN", Color.GREEN);
       put("AQUA", Color.AQUA);
       put("TEAL", Color.TEAL);
       put("BLUE", Color.BLUE);
       put("NAVY", Color.NAVY);
       put("FUCHSIA", Color.FUCHSIA);
       put("PURPLE", Color.PURPLE);
       put("ORANGE", Color.ORANGE);
    }};

    @Override
    public void deserialize(@NonNull ItemStack stack, @NonNull ConfigurationSection section) {
        ItemMeta itemMeta = stack.getItemMeta();
        if (!(itemMeta instanceof PotionMeta potionMeta)) return;
        if (!section.contains(POTION_META)) return;
        ConfigurationSection potionMetaSection = section.getConfigurationSection(POTION_META);

        deserializePotionEffects(potionMeta, potionMetaSection);
        deserializeRGBColor(potionMeta, potionMetaSection);
        deserializeColor(potionMeta, potionMetaSection);

        stack.setItemMeta(potionMeta);
    }

    private void deserializePotionEffects(@NonNull PotionMeta potionMeta, @NonNull ConfigurationSection section) {
        if (!section.contains(POTION_EFFECTS)) return;
        for (String key : section.getConfigurationSection(POTION_EFFECTS).getKeys(false)) {
            PotionEffectType effectType = PotionEffectType.getByName(key);
            if(effectType == null)
                throw new IllegalArgumentException("PotionEffectType " + key + " not found");
            potionMeta.addCustomEffect(
                    new PotionEffect(
                            effectType,
                            section.getInt(POTION_EFFECTS + key + ".Duration", 0),
                            section.getInt(POTION_EFFECTS + key + ".Amplifier", 0),
                            section.getBoolean(POTION_EFFECTS + key + ".Ambient", true),
                            section.getBoolean(POTION_EFFECTS + key + ".Particles", true),
                            section.getBoolean(POTION_EFFECTS + key + ".Icon", true)
                    ),
                    true
            );
        }
    }

    private void deserializeRGBColor(@NonNull PotionMeta potionMeta, @NonNull ConfigurationSection section) {
        if (!section.contains(RGB_COLOR)) return;
        String rgbColor = section.getString(RGB_COLOR);
        String[] split = rgbColor.split(";");
        if(split.length != 3)
            throw new IllegalArgumentException(rgbColor + " is invalid RGB format. Use RRR;GGG;BBB. Use only integers");

        int r, g, b;
        try{
            r = Integer.parseInt(split[0]);
            g = Integer.parseInt(split[1]);
            b = Integer.parseInt(split[2]);
        }catch (Exception e){
            throw new IllegalArgumentException(rgbColor + " is invalid RGB format. Use RRR;GGG;BBB. Use only integers");
        }
        potionMeta.setColor(Color.fromRGB(r, g, b));
    }

    private void deserializeColor(@NonNull PotionMeta potionMeta, @NonNull ConfigurationSection section) {
        if (!section.contains(COLOR)) return;
        String stringColor = section.getString(COLOR);
        Color color = colorNames.get(stringColor.toUpperCase());
        if(color == null)
            throw new IllegalArgumentException("Color " + stringColor + " not found");
        potionMeta.setColor(color);
    }

}
