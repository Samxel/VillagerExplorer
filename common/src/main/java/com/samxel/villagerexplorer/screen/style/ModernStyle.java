package com.samxel.villagerexplorer.screen.style;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

public class ModernStyle extends GuiStyle {
    private static final Identifier TRADE_ARROW = Identifier.withDefaultNamespace("container/villager/trade_arrow");
    private static final int BACKGROUND = 0xF0161A21;
    private static final int SURFACE = 0xFF1E232C;
    private static final int SURFACE_HOVER = 0xFF262C37;
    private static final int INSET = 0xFF0F1217;
    private static final int BORDER = 0xFF2A303B;
    private static final int ACCENT = 0xFF7AA2F7;
    private static final int TEXT = 0xFFE6E9EF;
    private static final int MUTED = 0xFF8B93A1;

    @Override
    public void panel(GuiGraphicsExtractor graphics, int x, int y, int width, int height) {
        roundedFill(graphics, x, y, width, height, BACKGROUND);
        roundedOutline(graphics, x, y, width, height, BORDER);
        graphics.fill(x + 12, y + 1, x + width - 12, y + 3, ACCENT);
    }

    @Override
    public void title(GuiGraphicsExtractor graphics, Font font, String text, int centerX, int y) {
        graphics.text(font, text, centerX - font.width(text) / 2, y, TEXT, false);
    }

    @Override
    public boolean borderedSearch() {
        return false;
    }

    @Override
    public void searchBackground(GuiGraphicsExtractor graphics, int x, int y, int width, int height, boolean focused) {
        roundedFill(graphics, x, y, width, height, INSET);
        roundedOutline(graphics, x, y, width, height, focused ? ACCENT : BORDER);
    }

    @Override
    public int searchTextColor() {
        return TEXT;
    }

    @Override
    public void tile(GuiGraphicsExtractor graphics, int x, int y, int size, boolean hovered) {
        roundedFill(graphics, x, y, size, size, hovered ? SURFACE_HOVER : SURFACE);
        roundedOutline(graphics, x, y, size, size, hovered ? ACCENT : BORDER);
    }

    @Override
    public void tileLabel(GuiGraphicsExtractor graphics, Font font, String text, int centerX, int y, boolean hovered) {
        graphics.text(font, text, centerX - font.width(text) / 2, y, hovered ? TEXT : MUTED, false);
    }

    @Override
    public void name(GuiGraphicsExtractor graphics, Font font, String text, int x, int y) {
        graphics.text(font, text, x, y, TEXT, false);
        graphics.fill(x, y + 11, x + font.width(text), y + 12, ACCENT);
    }

    @Override
    public void sectionLabel(GuiGraphicsExtractor graphics, Font font, String text, int x, int y) {
        graphics.text(font, text.toUpperCase(), x, y, MUTED, false);
    }

    @Override
    public void levelLabel(GuiGraphicsExtractor graphics, Font font, String name, int color, int x, int y) {
        int width = font.width(name) + 8;
        roundedFill(graphics, x, y + 3, width, 12, ARGB.color(0x40, color));
        graphics.text(font, name, x + 4, y + 5, color, false);
    }

    @Override
    public void levelDivider(GuiGraphicsExtractor graphics, int x, int y, int width) {
        graphics.fill(x, y, x + width, y + 1, BORDER);
    }

    @Override
    public void slot(GuiGraphicsExtractor graphics, int x, int y, int size) {
        roundedFill(graphics, x, y, size, size, INSET);
        roundedOutline(graphics, x, y, size, size, BORDER);
    }

    @Override
    public void arrow(GuiGraphicsExtractor graphics, Font font, int x, int y) {
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, TRADE_ARROW, x, y + 4, 10, 9, MUTED);
    }

    @Override
    public void scrollbar(GuiGraphicsExtractor graphics, int x, int y, int height, int thumbY, int thumbHeight) {
        roundedFill(graphics, x + 1, y, 4, height, INSET);
        roundedFill(graphics, x + 1, thumbY, 4, thumbHeight, ACCENT);
    }

    @Override
    public void rowHighlight(GuiGraphicsExtractor graphics, int x, int y, int width, int height) {
        roundedFill(graphics, x, y, width, height, 0x307AA2F7);
    }

    @Override
    public int bodyColor() {
        return MUTED;
    }

    @Override
    public void button(GuiGraphicsExtractor graphics, int x, int y, int width, int height, boolean hovered) {
        roundedFill(graphics, x, y, width, height, hovered ? SURFACE_HOVER : SURFACE);
        roundedOutline(graphics, x, y, width, height, hovered ? ACCENT : BORDER);
    }
}
