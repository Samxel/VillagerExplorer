package com.samxel.villagerexplorer.villager;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.samxel.villagerexplorer.VillagerExplorer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class VillagerTrades {
    private static final String RESOURCE = "/assets/" + VillagerExplorer.MOD_ID + "/trades.json";
    private static Map<Merchant, List<TradeGroup>> groups;

    private VillagerTrades() {
    }

    public static List<TradeGroup> forMerchant(Merchant merchant) {
        if (groups == null) {
            groups = load();
        }
        return groups.getOrDefault(merchant, List.of());
    }

    private static Map<Merchant, List<TradeGroup>> load() {
        Map<Merchant, List<TradeGroup>> result = new EnumMap<>(Merchant.class);
        try (InputStream stream = VillagerTrades.class.getResourceAsStream(RESOURCE)) {
            if (stream == null) {
                VillagerExplorer.LOGGER.error("Missing {}", RESOURCE);
                return result;
            }

            try (Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
                JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
                for (Merchant merchant : Merchant.values()) {
                    JsonArray array = root.getAsJsonArray(merchant.id());
                    if (array != null) {
                        result.put(merchant, parseGroups(array));
                    }
                }
            }
        } catch (IOException | RuntimeException e) {
            VillagerExplorer.LOGGER.error("Failed to load {}", RESOURCE, e);
        }
        return result;
    }

    private static List<TradeGroup> parseGroups(JsonArray array) {
        List<TradeGroup> result = new ArrayList<>();
        for (JsonElement element : array) {
            JsonObject group = element.getAsJsonObject();
            String id = group.get("group").getAsString();
            List<Trade> trades = new ArrayList<>();
            for (JsonElement trade : group.getAsJsonArray("trades")) {
                trades.add(parseTrade(trade.getAsJsonObject()));
            }
            result.add(new TradeGroup(TradeGroup.displayName(id), TradeGroup.color(id), group.get("amount").getAsInt(), List.copyOf(trades)));
        }
        return List.copyOf(result);
    }

    private static Trade parseTrade(JsonObject json) {
        TradeModifier modifier = TradeModifier.byId(json.has("modifier") ? json.get("modifier").getAsString() : null);

        List<TradeStack> wants = new ArrayList<>();
        for (JsonElement want : json.getAsJsonArray("wants")) {
            wants.add(parseStack(want.getAsJsonObject(), false));
        }

        Trade.PriceRange price = null;
        if (json.has("price")) {
            JsonArray range = json.getAsJsonArray("price");
            price = new Trade.PriceRange(range.get(0).getAsInt(), range.get(1).getAsInt());
        }

        List<VillagerBiome> biomes = new ArrayList<>();
        if (json.has("biomes")) {
            for (JsonElement biome : json.getAsJsonArray("biomes")) {
                VillagerBiome value = VillagerBiome.byId(biome.getAsString());
                if (value != null) {
                    biomes.add(value);
                }
            }
        }

        return new Trade(
                List.copyOf(wants),
                parseStack(json.getAsJsonObject("gives"), modifier != null && modifier.glint()),
                json.get("maxUses").getAsInt(),
                json.get("xp").getAsInt(),
                modifier,
                price,
                List.copyOf(biomes)
        );
    }

    private static TradeStack parseStack(JsonObject json, boolean glint) {
        int count = json.get("count").getAsInt();
        String potion = json.has("potion") ? json.get("potion").getAsString() : null;
        List<ItemStack> variants = new ArrayList<>();
        for (JsonElement id : json.getAsJsonArray("items")) {
            Item item = BuiltInRegistries.ITEM.getValue(Identifier.parse(id.getAsString()));
            if (item == Items.AIR) {
                continue;
            }

            ItemStack stack = new ItemStack(item, Math.max(1, count));
            if (potion != null) {
                BuiltInRegistries.POTION.get(Identifier.parse(potion))
                        .ifPresent(holder -> stack.set(DataComponents.POTION_CONTENTS, new PotionContents(holder)));
            }
            if (glint) {
                stack.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
            }
            variants.add(stack);
        }

        if (variants.isEmpty()) {
            variants.add(new ItemStack(Items.BARRIER));
        }
        return new TradeStack(List.copyOf(variants), count);
    }
}
