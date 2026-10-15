package com.samxel.villagerexplorer.villager;

import com.samxel.villagerexplorer.Tr;
import org.jspecify.annotations.Nullable;

public enum TradeModifier {
    ENCHANTED("enchanted"),
    BOOK("book"),
    DYED("dyed"),
    STEW("stew"),
    TIPPED("tipped"),
    MAP("map");

    private final String id;

    TradeModifier(String id) {
        this.id = id;
    }

    public String description() {
        return Tr.get("modifier." + this.id);
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
