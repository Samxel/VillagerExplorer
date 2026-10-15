package com.samxel.villagerexplorer.villager;

import org.jspecify.annotations.Nullable;

import java.util.List;

public record Trade(
        List<TradeStack> wants,
        TradeStack gives,
        int maxUses,
        int xp,
        @Nullable TradeModifier modifier,
        @Nullable PriceRange price,
        List<VillagerBiome> biomes
) {
    public boolean availableIn(@Nullable VillagerBiome biome) {
        return biome == null || this.biomes.isEmpty() || this.biomes.contains(biome);
    }

    public record PriceRange(int min, int max) {
    }
}
