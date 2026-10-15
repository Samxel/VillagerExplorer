package com.samxel.villagerexplorer.villager;

import com.samxel.villagerexplorer.Tr;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.villager.VillagerType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.Nullable;

public enum VillagerBiome {
    DESERT("desert", VillagerType.DESERT, Items.SAND),
    JUNGLE("jungle", VillagerType.JUNGLE, Items.JUNGLE_SAPLING),
    PLAINS("plains", VillagerType.PLAINS, Items.SHORT_GRASS),
    SAVANNA("savanna", VillagerType.SAVANNA, Items.ACACIA_SAPLING),
    SNOW("snow", VillagerType.SNOW, Items.SNOWBALL),
    SWAMP("swamp", VillagerType.SWAMP, Items.LILY_PAD),
    TAIGA("taiga", VillagerType.TAIGA, Items.SPRUCE_SAPLING);

    private final String id;
    private final ResourceKey<VillagerType> type;
    private final Item icon;

    VillagerBiome(String id, ResourceKey<VillagerType> type, Item icon) {
        this.id = id;
        this.type = type;
        this.icon = icon;
    }

    public String displayName() {
        return Tr.get("biome." + this.id);
    }

    public static @Nullable VillagerBiome byType(ResourceKey<VillagerType> type) {
        for (VillagerBiome biome : values()) {
            if (biome.type.equals(type)) {
                return biome;
            }
        }
        return null;
    }

    public ResourceKey<VillagerType> type() {
        return this.type;
    }

    public ItemStack icon() {
        return new ItemStack(this.icon);
    }

    public static @Nullable VillagerBiome byId(String id) {
        for (VillagerBiome biome : values()) {
            if (biome.id.equals(id)) {
                return biome;
            }
        }
        return null;
    }
}
