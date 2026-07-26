package com.samxel.villagerexplorer.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.samxel.villagerexplorer.VillagerExplorerScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class VillagerExplorerClient implements ClientModInitializer {

    public static KeyMapping openGuiKeybind;

    @Override
    public void onInitializeClient() {
        openGuiKeybind = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.villagerexplorer.opengui",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                KeyMapping.Category.register(Identifier.fromNamespaceAndPath("villagerexplorer", "villagerexplorer"))
        ));

        net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGuiKeybind.consumeClick()) {
                client.gui.setScreen(new VillagerExplorerScreen());
            }
        });
    }
}
