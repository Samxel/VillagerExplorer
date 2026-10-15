package com.samxel.villagerexplorer.villager;

import net.minecraft.world.item.ItemStack;

import java.util.List;

public record Trade(TradeLevel level, List<ItemStack> wanted, ItemStack given) {
}
