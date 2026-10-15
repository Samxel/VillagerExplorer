package com.samxel.villagerexplorer.villager;

import com.samxel.villagerexplorer.Tr;

import java.util.List;

public record TradeGroup(String id, int amount, List<Trade> trades) {

    public String displayName() {
        return Tr.get("group." + this.id);
    }

    public int color() {
        return switch (this.id) {
            case "level_1", "buying" -> 0xFF9E9E9E;
            case "level_2" -> 0xFFE0B84A;
            case "level_3", "common" -> 0xFF6FCF5C;
            case "level_4", "uncommon" -> 0xFF4FC3E8;
            case "level_5" -> 0xFFC77DFF;
            default -> 0xFFFFFFFF;
        };
    }
}
