package com.samxel.villagerexplorer.fabric;

import com.samxel.villagerexplorer.client.VillagerExplorerClient;
import com.samxel.villagerexplorer.client.VillagerExplorerCommand;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;

public class VillagerExplorerFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        VillagerExplorerClient.init(FabricLoader.getInstance().getConfigDir());
        KeyMapping.Category category = KeyMapping.Category.register(VillagerExplorerClient.KEY_CATEGORY);
        KeyMappingHelper.registerKeyMapping(VillagerExplorerClient.createOpenKey(category));
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, context) -> dispatcher.register(VillagerExplorerCommand.create()));
        ClientTickEvents.END_CLIENT_TICK.register(VillagerExplorerClient::tick);
    }
}
