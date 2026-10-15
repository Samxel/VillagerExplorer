package com.samxel.villagerexplorer.villager;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Locale;

public final class TradeSearch {
    public static final TradeSearch EMPTY = new TradeSearch("");

    private final String query;
    private final boolean enchantmentMatch;

    public TradeSearch(String query) {
        this.query = query.trim().toLowerCase(Locale.ROOT);
        this.enchantmentMatch = !this.query.isEmpty() && tradeableEnchantments().stream().anyMatch(name -> name.contains(this.query));
    }

    public boolean isEmpty() {
        return this.query.isEmpty();
    }

    public boolean matchesName(Merchant merchant) {
        if (merchant.displayName().toLowerCase(Locale.ROOT).contains(this.query)) {
            return true;
        }
        JobSiteRecipe jobSite = merchant.jobSite();
        return jobSite != null && jobSite.result().getHoverName().getString().toLowerCase(Locale.ROOT).contains(this.query);
    }

    public boolean matches(Trade trade) {
        if (this.isEmpty()) {
            return false;
        }
        if (this.enchantmentMatch && trade.modifier() == TradeModifier.BOOK) {
            return true;
        }
        if (trade.gives().matches(this.query)) {
            return true;
        }
        for (TradeStack want : trade.wants()) {
            if (want.matches(this.query)) {
                return true;
            }
        }
        return false;
    }

    public @Nullable ItemStack matchingItem(Merchant merchant) {
        if (this.isEmpty()) {
            return null;
        }
        for (TradeGroup group : VillagerTrades.forMerchant(merchant)) {
            for (Trade trade : group.trades()) {
                if (!this.matches(trade)) {
                    continue;
                }
                if (trade.gives().matches(this.query) || trade.modifier() == TradeModifier.BOOK && this.enchantmentMatch) {
                    return trade.gives().firstMatch(this.query);
                }
                for (TradeStack want : trade.wants()) {
                    if (want.matches(this.query)) {
                        return want.firstMatch(this.query);
                    }
                }
            }
        }
        return null;
    }

    private static List<String> tradeableEnchantments() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) {
            return List.of();
        }
        return minecraft.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT)
                .get(EnchantmentTags.TRADEABLE)
                .map(set -> set.stream()
                        .map(Holder::value)
                        .map(Enchantment::description)
                        .map(component -> component.getString().toLowerCase(Locale.ROOT))
                        .toList())
                .orElse(List.of());
    }
}
