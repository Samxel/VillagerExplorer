package com.samxel.villagerexplorer.villager;

import org.jspecify.annotations.Nullable;

public enum TradeModifier {
    ENCHANTED("enchanted", "Random enchantments"),
    BOOK("book", "Random tradeable enchantment"),
    DYED("dyed", "Random dye color"),
    STEW("stew", "Random effect"),
    TIPPED("tipped", "Random potion effect"),
    MAP("map", "Points to the nearest structure");

    private final String id;
    private final String description;

    TradeModifier(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String description() {
        return this.description;
    }

    public boolean glint() {
        return this == ENCHANTED || this == BOOK;
    }

    public static @Nullable TradeModifier byId(@Nullable String id) {
        for (TradeModifier modifier : values()) {
            if (modifier.id.equals(id)) {
                return modifier;
            }
        }
        return null;
    }
}
