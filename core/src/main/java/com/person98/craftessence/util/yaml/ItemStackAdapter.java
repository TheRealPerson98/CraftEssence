package com.person98.craftessence.util.yaml;

import com.person98.craftessence.util.item.ItemBuilder;
import com.person98.craftessence.util.item.ItemHelper;
import com.person98.craftessence.util.item.ConfigurableNBT;
import com.person98.craftessence.util.item.Json;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.google.gson.*;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ItemStackAdapter implements JsonDeserializer<ItemStack>, JsonSerializer<ItemStack> {
    private static final String MATERIAL = "material";
    private static final String AMOUNT = "amount";
    private static final String DISPLAY_NAME = "displayName";
    private static final String LORE = "lore";
    private static final String ENCHANTMENTS = "enchants";
    private static final String CUSTOM_MODEL_DATA = "customModelData";
    private static final String ITEM_FLAGS = "itemFlags";
    private static final String UNBREAKABLE = "unbreakable";
    private static final String NBT_DATA = "nbt";

    @Override
    public ItemStack deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        try {
            JsonObject obj = jsonElement.getAsJsonObject();
            Material material = Material.getMaterial(obj.get(MATERIAL).getAsString());
            int amount = obj.has(AMOUNT) ? obj.get(AMOUNT).getAsInt() : 1;

            if (material == null) {
                throw new JsonParseException("Invalid material: " + obj.get(MATERIAL).getAsString());
            }

            ItemBuilder builder = ItemBuilder.of(material).amount(amount);

            // Deserialize display name
            if (obj.has(DISPLAY_NAME)) {
                builder.name(obj.get(DISPLAY_NAME).getAsString());
            }

            // Deserialize lore
            if (obj.has(LORE)) {
                List<String> loreList = Json.getGson().fromJson(obj.get(LORE), List.class);
                builder.lore(loreList);
            }

            // Deserialize custom model data
            if (obj.has(CUSTOM_MODEL_DATA)) {
                builder.customModelData(obj.get(CUSTOM_MODEL_DATA).getAsInt());
            }

            // Deserialize item flags (Fix)
            if (obj.has(ITEM_FLAGS)) {
                List<String> flags = Json.getGson().fromJson(obj.get(ITEM_FLAGS), List.class);
                Map<String, Boolean> itemFlagsMap = new HashMap<>();
                for (String flag : flags) {
                    itemFlagsMap.put(flag, true);  // Assuming we treat all flags as "enabled"
                }
                builder.itemFlags(itemFlagsMap);  // Pass the map to the builder
            }

            // Deserialize unbreakable
            if (obj.has(UNBREAKABLE)) {
                builder.unbreakable(obj.get(UNBREAKABLE).getAsBoolean());
            }

            // Deserialize enchantments
            if (obj.has(ENCHANTMENTS)) {
                Map<String, Integer> enchantments = Json.getGson().fromJson(obj.get(ENCHANTMENTS), Map.class);
                for (Map.Entry<String, Integer> entry : enchantments.entrySet()) {
                    Enchantment enchantment = Enchantment.getByName(entry.getKey());
                    builder.enchant(enchantment, entry.getValue());
                }
            }

            // Build the item
            ItemStack item = builder.build();

            // Handle NBT data
            if (obj.has(NBT_DATA)) {
                List<JsonObject> nbtList = Json.getGson().fromJson(obj.get(NBT_DATA), List.class);

                for (JsonObject nbtObj : nbtList) {
                    try {
                        ConfigurableNBT configurableNBT = new ConfigurableNBT();

                        // Use reflection to set the private key and value fields
                        Field keyField = ConfigurableNBT.class.getDeclaredField("key");
                        keyField.setAccessible(true);
                        keyField.set(configurableNBT, nbtObj.get("key").getAsString());

                        Field valueField = ConfigurableNBT.class.getDeclaredField("value");
                        valueField.setAccessible(true);
                        valueField.set(configurableNBT, nbtObj.get("value").getAsString());

                        // Apply NBT data using the NBTHandler's action
                        NBTItem nbtItem = new NBTItem(item); // Create an NBTItem from the ItemStack
                        configurableNBT.getHandler().getAction().accept(nbtItem, configurableNBT.getKey(), configurableNBT.getValue());

                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new JsonParseException("Error setting NBT fields with reflection", e);
                    }
                }
            }

            return item;
        } catch (Exception ex) {
            throw new JsonParseException("Error deserializing ItemStack", ex);
        }
    }



    @Override
    public JsonElement serialize(ItemStack itemStack, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty(MATERIAL, itemStack.getType().name());
            obj.addProperty(AMOUNT, itemStack.getAmount());

            if (itemStack.hasItemMeta()) {
                ItemMeta meta = itemStack.getItemMeta();
                if (meta.hasDisplayName()) {
                    obj.addProperty(DISPLAY_NAME, ItemHelper.get().getSerializableDisplayName(itemStack));
                }

                if (meta.hasLore()) {
                    List<String> lore = ItemHelper.get().getSerializableLore(itemStack);
                    obj.add(LORE, new Gson().toJsonTree(lore));
                }

                if (meta.hasCustomModelData()) {
                    obj.addProperty(CUSTOM_MODEL_DATA, meta.getCustomModelData());
                }

                if (!meta.getItemFlags().isEmpty()) {
                    List<String> flags = ItemHelper.get().getSerializableItemFlags(meta);
                    obj.add(ITEM_FLAGS, new Gson().toJsonTree(flags));
                }

                obj.addProperty(UNBREAKABLE, meta.isUnbreakable());
            }

            if (!itemStack.getEnchantments().isEmpty()) {
                Map<String, Integer> enchants = ItemHelper.get().getSerializableEnchantments(itemStack);
                obj.add(ENCHANTMENTS, new Gson().toJsonTree(enchants));
            }

            // NBT handling
            List<ConfigurableNBT> nbtList = ItemHelper.get().getSerializableNBT(itemStack);
            if (!nbtList.isEmpty()) {
                obj.add(NBT_DATA, new Gson().toJsonTree(nbtList));
            }

            return obj;
        } catch (Exception ex) {
            throw new JsonParseException("Error serializing ItemStack", ex);
        }
    }
}
