package com.samxel.villagerexplorer.villager;

import java.util.List;

public record TradeGroup(String displayName, int color, int amount, List<Trade> trades) {

    public static String displayName(String id) {
        return switch (id) {
            case "level_1" -> "Novice";
            case "level_2" -> "Apprentice";
            case "level_3" -> "Journeyman";
            case "level_4" -> "Expert";
            case "level_5" -> "Master";
            case "buying" -> "Buying";
            case "common" -> "Common";
            case "uncommon" -> "Uncommon";
            default -> id;
        };
    }

    public static int color(String id) {
        return switch (id) {
            case "level_1", "buying" -> 0xFF9E9E9E;
            case "level_2" -> 0xFFE0B84A;
            case "level_3", "common" -> 0xFF6FCF5C;
            case "level_4", "uncommon" -> 0xFF4FC3E8;
            case "level_5" -> 0xFFC77DFF;
            default -> 0xFFFFFFFF;
        };
    }
}
