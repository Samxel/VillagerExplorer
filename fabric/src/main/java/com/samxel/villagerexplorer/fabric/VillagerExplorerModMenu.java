package com.samxel.villagerexplorer.fabric;

import com.samxel.villagerexplorer.screen.StyleScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class VillagerExplorerModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return StyleScreen::new;
    }
}
