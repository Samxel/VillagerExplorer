package com.samxel.villagerexplorer;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public final class Tr {

    private Tr() {
    }

    public static MutableComponent of(String key, Object... args) {
        return Component.translatable(VillagerExplorer.MOD_ID + "." + key, args);
    }

    public static String get(String key, Object... args) {
        return of(key, args).getString();
    }
}
