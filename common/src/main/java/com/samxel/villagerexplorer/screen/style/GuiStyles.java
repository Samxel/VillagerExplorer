package com.samxel.villagerexplorer.screen.style;

import java.util.Locale;
import java.util.Optional;

public enum GuiStyles {
    VANILLA(new VanillaStyle()),
    MODERN(new ModernStyle()),
    REFINED(new RefinedStyle()),
    LEDGER(new LedgerStyle());

    private static GuiStyles current = VANILLA;

    private final GuiStyle style;

    GuiStyles(GuiStyle style) {
        this.style = style;
    }

    public GuiStyle style() {
        return this.style;
    }

    public String id() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public static Optional<GuiStyles> byId(String id) {
        for (GuiStyles value : values()) {
            if (value.id().equalsIgnoreCase(id)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }

    public static GuiStyles current() {
        return current;
    }

    public static void setCurrent(GuiStyles style) {
        current = style;
    }
}
