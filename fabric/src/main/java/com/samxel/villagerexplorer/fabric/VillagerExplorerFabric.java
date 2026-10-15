package com.samxel.villagerexplorer.fabric;

import com.samxel.villagerexplorer.VillagerExplorer;
import com.samxel.villagerexplorer.client.VillagerExplorerClient;
import com.samxel.villagerexplorer.client.VillagerExplorerCommand;
import com.samxel.villagerexplorer.hud.PinnedTradesHud;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.resources.Identifier;

public class VillagerExplorerFabric implements ClientModInitializer {
    private static final Identifier PINNED_TRADES = Identifier.fromNamespaceAndPath(VillagerExplorer.MOD_ID, "pinned_trades");

    @Override
    public void onInitializeClient() {
        VillagerExplorerClient.init(FabricLoader.getInstance().getConfigDir());
        KeyMapping.Category category = KeyMapping.Category.register(VillagerExplorerClient.KEY_CATEGORY);
        KeyMappingHelper.registerKeyMapping(VillagerExplorerClient.createOpenKey(category));
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, context) -> dispatcher.register(VillagerExplorerCommand.create()));
        ClientTickEvents.END_CLIENT_TICK.register(VillagerExplorerClient::tick);
        HudElementRegistry.attachElementBefore(VanillaHudElements.CHAT, PINNED_TRADES, (graphics, deltaTracker) -> PinnedTradesHud.extract(graphics));
        ScreenEvents.AFTER_INIT.register((minecraft, screen, width, height) -> {
            if (screen instanceof ChatScreen) {
                ScreenMouseEvents.allowMouseClick(screen).register((current, event) -> !PinnedTradesHud.mouseClicked(event.x(), event.y()));
                ScreenMouseEvents.allowMouseDrag(screen).register((current, event, dx, dy) -> !PinnedTradesHud.mouseDragged(event.x(), event.y()));
                ScreenMouseEvents.allowMouseRelease(screen).register((current, event) -> !PinnedTradesHud.mouseReleased());
            }
        });
    }
}
