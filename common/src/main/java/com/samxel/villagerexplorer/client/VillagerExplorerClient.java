package com.samxel.villagerexplorer.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.samxel.villagerexplorer.VillagerExplorer;
import com.samxel.villagerexplorer.config.StyleConfig;
import com.samxel.villagerexplorer.screen.VillagerExplorerScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.Identifier;
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
        StyleConfig.load(configDir);
    }

    public static KeyMapping createOpenKey(KeyMapping.Category category) {
        openKey = new KeyMapping("key.villagerexplorer.opengui", InputConstants.Type.KEYBOARD, InputConstants.KEY_V, category);
        return openKey;
    }

    // the chat screen closes after a command runs, so screens requested by commands open on the next tick
    public static void openLater(Supplier<Screen> screen) {
        pendingScreen = screen;
    }

    public static void tick(Minecraft minecraft) {
        Supplier<Screen> screen = pendingScreen;
        pendingScreen = null;
        if (openKey != null) {
            while (openKey.consumeClick()) {
                screen = VillagerExplorerScreen::new;
            }
        }
        if (screen != null && minecraft.level != null) {
            minecraft.gui.setScreen(screen.get());
        }
    }
}
