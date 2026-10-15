package com.samxel.villagerexplorer.config;

import com.samxel.villagerexplorer.VillagerExplorer;
import com.samxel.villagerexplorer.screen.style.GuiStyles;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class StyleConfig {
    private static final String STYLE_KEY = "style";
    private static Path file;

    private StyleConfig() {
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
    }

    public static void save() {
        if (file == null) {
            return;
        }

        Properties properties = new Properties();
        properties.setProperty(STYLE_KEY, GuiStyles.current().id());
        try {
            Files.createDirectories(file.getParent());
            try (Writer writer = Files.newBufferedWriter(file)) {
                properties.store(writer, null);
            }
        } catch (IOException e) {
            VillagerExplorer.LOGGER.warn("Failed to write {}", file, e);
        }
    }
}
