package com.samxel.villagerexplorer.neoforge;

import com.samxel.villagerexplorer.VillagerExplorer;
import com.samxel.villagerexplorer.client.VillagerExplorerClient;
import com.samxel.villagerexplorer.client.VillagerExplorerCommand;
import com.samxel.villagerexplorer.screen.StyleScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = VillagerExplorer.MOD_ID, dist = Dist.CLIENT)
public class VillagerExplorerNeoForge {
    private static final KeyMapping.Category CATEGORY = new KeyMapping.Category(VillagerExplorerClient.KEY_CATEGORY);

    public VillagerExplorerNeoForge(IEventBus modBus, ModContainer container) {
        VillagerExplorerClient.init(FMLPaths.CONFIGDIR.get());
        container.registerExtensionPoint(IConfigScreenFactory.class, (mod, parent) -> new StyleScreen(parent));
        modBus.addListener(VillagerExplorerNeoForge::onRegisterKeyMappings);
        NeoForge.EVENT_BUS.addListener(VillagerExplorerNeoForge::onRegisterCommands);
        NeoForge.EVENT_BUS.addListener(VillagerExplorerNeoForge::onClientTick);
    }

    private static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.registerCategory(CATEGORY);
        event.register(VillagerExplorerClient.createOpenKey(CATEGORY));
    }

    private static void onRegisterCommands(RegisterClientCommandsEvent event) {
        event.getDispatcher().register(VillagerExplorerCommand.create());
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        VillagerExplorerClient.tick(Minecraft.getInstance());
    }
}
