package com.samxel.villagerexplorer.client;

import com.samxel.villagerexplorer.VillagerExplorerScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class VillagerExplorerClient implements ClientModInitializer {

    public static KeyBinding openGuiKeybind;

    @Override
    public void onInitializeClient() {
        openGuiKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.villagerexplorer.opengui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                "category.villagerexplorer"
        ));

        net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGuiKeybind.wasPressed()) {
                client.setScreen(new VillagerExplorerScreen());
            }
        });
    }
}
