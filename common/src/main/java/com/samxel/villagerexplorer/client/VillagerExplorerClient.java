package com.samxel.villagerexplorer.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.samxel.villagerexplorer.VillagerExplorer;
import com.samxel.villagerexplorer.config.ExplorerConfig;
import com.samxel.villagerexplorer.screen.VillagerExplorerScreen;
import com.samxel.villagerexplorer.screen.VillagerInfoScreen;
import com.samxel.villagerexplorer.villager.Merchant;
import com.samxel.villagerexplorer.villager.TradeSearch;
import com.samxel.villagerexplorer.villager.VillagerBiome;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.villager.VillagerData;
import net.minecraft.world.entity.npc.wanderingtrader.WanderingTrader;
import org.jspecify.annotations.Nullable;

import java.nio.file.Path;
import java.util.function.Supplier;

public final class VillagerExplorerClient {
    public static final Identifier KEY_CATEGORY = Identifier.fromNamespaceAndPath(VillagerExplorer.MOD_ID, VillagerExplorer.MOD_ID);

    private static @Nullable KeyMapping openKey;
    private static @Nullable Supplier<Screen> pendingScreen;

    private VillagerExplorerClient() {
    }

    public static void init(Path configDir) {
        ExplorerConfig.load(configDir);
    }

    public static KeyMapping createOpenKey(KeyMapping.Category category) {
        openKey = new KeyMapping("key.villagerexplorer.opengui", InputConstants.Type.KEYBOARD, InputConstants.KEY_V, category);
        return openKey;
    }

    // the chat screen closes after a command runs, so screens requested by commands open on the next tick
    public static void openLater(Supplier<Screen> screen) {
        pendingScreen = screen;
    }

    // looking at a villager or wandering trader opens its trades directly
    private static Screen screenFor(@Nullable Entity target) {
        if (target instanceof WanderingTrader) {
            return new VillagerInfoScreen(Merchant.WANDERING_TRADER, TradeSearch.EMPTY, null);
        }
        if (target instanceof Villager villager) {
            VillagerData data = villager.getVillagerData();
            Merchant merchant = data.profession().unwrapKey().map(Merchant::byProfession).orElse(null);
            if (merchant != null) {
                VillagerBiome biome = data.type().unwrapKey().map(VillagerBiome::byType).orElse(null);
                return new VillagerInfoScreen(merchant, TradeSearch.EMPTY, null, biome);
            }
        }
        return new VillagerExplorerScreen();
    }

    public static void tick(Minecraft minecraft) {
        Supplier<Screen> screen = pendingScreen;
        pendingScreen = null;
        if (openKey != null) {
            while (openKey.consumeClick()) {
                screen = () -> screenFor(minecraft.crosshairPickEntity);
            }
        }
        if (screen != null && minecraft.level != null) {
            minecraft.gui.setScreen(screen.get());
        }
    }
}
