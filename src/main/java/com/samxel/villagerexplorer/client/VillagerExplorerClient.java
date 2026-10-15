package com.samxel.villagerexplorer.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.samxel.villagerexplorer.VillagerExplorer;
import com.samxel.villagerexplorer.config.StyleConfig;
import com.samxel.villagerexplorer.screen.VillagerExplorerScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

public class VillagerExplorerClient implements ClientModInitializer {
    private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(VillagerExplorer.MOD_ID, VillagerExplorer.MOD_ID));
    private static final KeyMapping OPEN_GUI = new KeyMapping("key.villagerexplorer.opengui", InputConstants.Type.KEYBOARD, InputConstants.KEY_V, CATEGORY);

    @Override
    public void onInitializeClient() {
        StyleConfig.load(FabricLoader.getInstance().getConfigDir());
        KeyMappingHelper.registerKeyMapping(OPEN_GUI);
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, context) -> dispatcher.register(VillagerExplorerCommand.create()));
        ClientTickEvents.END_CLIENT_TICK.register(VillagerExplorerClient::onTick);
    }

    private static void onTick(Minecraft minecraft) {
        boolean open = VillagerExplorerCommand.consumeOpenRequest();
        while (OPEN_GUI.consumeClick()) {
            open = true;
        }
        if (open && minecraft.level != null) {
            minecraft.gui.setScreen(new VillagerExplorerScreen());
        }
    }
}
