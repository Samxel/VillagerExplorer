package com.samxel.villagerexplorer.villager;

public enum TradeLevel {
    NOVICE("Novice", 0xFF9E9E9E),
    APPRENTICE("Apprentice", 0xFFE0B84A),
    JOURNEYMAN("Journeyman", 0xFF6FCF5C),
    EXPERT("Expert", 0xFF4FC3E8),
    MASTER("Master", 0xFFC77DFF);

    private final String displayName;
    private final int color;

    TradeLevel(String displayName, int color) {
        this.displayName = displayName;
        this.color = color;
    }

    public String displayName() {
        return this.displayName;
    }

    public int color() {
        return this.color;
    }
}
