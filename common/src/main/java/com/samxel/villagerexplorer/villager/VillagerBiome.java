package com.samxel.villagerexplorer.villager;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.villager.VillagerType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.Nullable;

public enum VillagerBiome {
    DESERT("desert", "Desert", VillagerType.DESERT, Items.SAND),
    JUNGLE("jungle", "Jungle", VillagerType.JUNGLE, Items.JUNGLE_SAPLING),
    PLAINS("plains", "Plains", VillagerType.PLAINS, Items.SHORT_GRASS),
    SAVANNA("savanna", "Savanna", VillagerType.SAVANNA, Items.ACACIA_SAPLING),
    SNOW("snow", "Snow", VillagerType.SNOW, Items.SNOWBALL),
    SWAMP("swamp", "Swamp", VillagerType.SWAMP, Items.LILY_PAD),
    TAIGA("taiga", "Taiga", VillagerType.TAIGA, Items.SPRUCE_SAPLING);

    private final String id;
    private final String displayName;
    private final ResourceKey<VillagerType> type;
    private final Item icon;

    VillagerBiome(String id, String displayName, ResourceKey<VillagerType> type, Item icon) {
        this.id = id;
        this.displayName = displayName;
        this.type = type;
        this.icon = icon;
    }

    public String displayName() {
        return this.displayName;
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
