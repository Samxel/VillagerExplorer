package com.samxel.villagerexplorer.villager;

import net.minecraft.util.Util;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Locale;

public record TradeStack(List<ItemStack> variants, int count) {
    private static final long CYCLE_MILLIS = 1000L;

    public ItemStack current() {
        int index = (int) (Util.getMillis() / CYCLE_MILLIS % this.variants.size());
        return this.variants.get(index);
    }

    public boolean hasVariants() {
        return this.variants.size() > 1;
    }

    public boolean matches(String query) {
        for (ItemStack variant : this.variants) {
            if (variant.getHoverName().getString().toLowerCase(Locale.ROOT).contains(query)) {
                return true;
            }
        }
        return false;
    }

    public ItemStack firstMatch(String query) {
        for (ItemStack variant : this.variants) {
            if (variant.getHoverName().getString().toLowerCase(Locale.ROOT).contains(query)) {
                return variant;
            }
        }
        return this.variants.getFirst();
    }
}
