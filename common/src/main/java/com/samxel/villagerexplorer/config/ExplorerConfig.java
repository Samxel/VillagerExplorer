package com.samxel.villagerexplorer.config;

import com.samxel.villagerexplorer.VillagerExplorer;
import com.samxel.villagerexplorer.hud.PinnedTradesHud;
import com.samxel.villagerexplorer.pin.Pins;
import com.samxel.villagerexplorer.screen.style.GuiStyles;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class ExplorerConfig {
    private static final String STYLE_KEY = "style";
    private static final String PINS_KEY = "pins";
    private static final String HUD_X_KEY = "hud.x";
    private static final String HUD_Y_KEY = "hud.y";
    private static Path file;

    private ExplorerConfig() {
    }

    public static void load(Path configDir) {
        file = configDir.resolve(VillagerExplorer.MOD_ID + ".properties");
        if (!Files.exists(file)) {
            return;
        }

        Properties properties = new Properties();
        try (Reader reader = Files.newBufferedReader(file)) {
            properties.load(reader);
        } catch (IOException e) {
            VillagerExplorer.LOGGER.warn("Failed to read {}", file, e);
            return;
        }

        GuiStyles.byId(properties.getProperty(STYLE_KEY, "")).ifPresent(GuiStyles::setCurrent);
        Pins.load(properties.getProperty(PINS_KEY, ""));
        PinnedTradesHud.setPosition(parse(properties.getProperty(HUD_X_KEY), PinnedTradesHud.DEFAULT_X),
                parse(properties.getProperty(HUD_Y_KEY), PinnedTradesHud.DEFAULT_Y));
    }

    public static void save() {
        if (file == null) {
            return;
        }

        Properties properties = new Properties();
        properties.setProperty(STYLE_KEY, GuiStyles.current().id());
        properties.setProperty(PINS_KEY, Pins.serialize());
        properties.setProperty(HUD_X_KEY, Float.toString(PinnedTradesHud.x()));
        properties.setProperty(HUD_Y_KEY, Float.toString(PinnedTradesHud.y()));
        try {
            Files.createDirectories(file.getParent());
            try (Writer writer = Files.newBufferedWriter(file)) {
                properties.store(writer, null);
            }
        } catch (IOException e) {
            VillagerExplorer.LOGGER.warn("Failed to write {}", file, e);
        }
    }

    private static float parse(String value, float fallback) {
        if (value == null) {
            return fallback;
        }
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }
}
