package com.samxel.villagerexplorer;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.npc.villager.VillagerProfession;

public class VillagerUtils {

    public static VillagerProfession getProfessionByName(String name) {
        Identifier id = Identifier.fromNamespaceAndPath("minecraft", name.toLowerCase());
        return BuiltInRegistries.VILLAGER_PROFESSION.getValue(id);
    }
}
