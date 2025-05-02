package com.samxel.villagerexplorer;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class VillagerExplorer implements ModInitializer {
    public static final String MOD_ID = "villagerexplorer";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Villager Explorer mod loaded!");
    }
}
