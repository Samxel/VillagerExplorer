package com.samxel.villagerexplorer;

import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;

public class VillagerUtils {

    public static VillagerProfession getProfessionByName(String name) {
        Identifier id = Identifier.of("minecraft", name.toLowerCase());
        return Registries.VILLAGER_PROFESSION.get(id);
    }
}
