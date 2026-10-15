package com.samxel.villagerexplorer.screen.style;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

public class LedgerStyle extends GuiStyle {
    private static final Identifier TRADE_ARROW = Identifier.withDefaultNamespace("container/villager/trade_arrow");
    private static final int PAPER = 0xFFF3E7C9;
    private static final int PAPER_EDGE = 0xFFE3D0A4;
    private static final int PAPER_DARK = 0xFFE6D5AC;
    private static final int LEATHER = 0xFF5B4126;
    private static final int LEATHER_DARK = 0xFF3A2915;
    private static final int INK = 0xFF3B2A1A;
    private static final int INK_MUTED = 0xFF7A6248;
    private static final int WAX = 0xFF9E2B25;
    private static final int SHADOW = 0x40000000;

    @Override
    public void panel(GuiGraphicsExtractor graphics, int x, int y, int width, int height) {
        graphics.fill(x, y, x + width, y + height, LEATHER_DARK);
        graphics.fill(x + 1, y + 1, x + width - 1, y + height - 1, LEATHER);
        graphics.fillGradient(x + 4, y + 4, x + width - 4, y + height - 4, PAPER, PAPER_EDGE);
        graphics.outline(x + 4, y + 4, width - 8, height - 8, 0x30000000);
        graphics.fill(x + width - 26, y + 1, x + width - 18, y + 14, WAX);
        graphics.fill(x + width - 26, y + 14, x + width - 22, y + 17, WAX);
        graphics.fill(x + width - 22, y + 14, x + width - 18, y + 16, WAX);
    }

    @Override
    public void title(GuiGraphicsExtractor graphics, Font font, String text, int centerX, int y) {
        int width = font.width(text);
        graphics.text(font, text, centerX - width / 2, y, INK, false);
        graphics.fill(centerX - width / 2 - 6, y + 11, centerX + width / 2 + 6, y + 12, WAX);
    }

    @Override
    public boolean borderedSearch() {
        return false;
    }

    @Override
    public void searchBackground(GuiGraphicsExtractor graphics, int x, int y, int width, int height, boolean focused) {
        graphics.fill(x, y, x + width, y + height, PAPER_DARK);
        graphics.fill(x, y + height - 1, x + width, y + height, focused ? WAX : LEATHER);
    }

    @Override
    public int searchTextColor() {
        return INK;
    }

    @Override
    public boolean searchTextShadow() {
        return false;
    }

    @Override
    public int searchHintColor() {
        return 0xFF9C8466;
    }

    @Override
    public void tile(GuiGraphicsExtractor graphics, int x, int y, int size, boolean hovered) {
        int offset = hovered ? 3 : 2;
        graphics.fill(x + offset, y + offset, x + size + offset, y + size + offset, SHADOW);
        graphics.fill(x, y, x + size, y + size, hovered ? 0xFFFBF3DE : PAPER);
        graphics.outline(x, y, size, size, hovered ? WAX : 0xFFB89F73);
    }

    @Override
    public void tileLabel(GuiGraphicsExtractor graphics, Font font, String text, int centerX, int y, boolean hovered) {
        graphics.text(font, text, centerX - font.width(text) / 2, y, hovered ? WAX : INK, false);
    }

    @Override
    public void name(GuiGraphicsExtractor graphics, Font font, String text, int x, int y) {
        graphics.pose().pushMatrix();
        graphics.pose().translate(x, y - 2);
        graphics.pose().scale(1.5F, 1.5F);
        graphics.text(font, text, 0, 0, INK, false);
        graphics.pose().popMatrix();
        graphics.fill(x, y + 12, x + Math.round(font.width(text) * 1.5F), y + 13, WAX);
    }

    @Override
    public void sectionLabel(GuiGraphicsExtractor graphics, Font font, String text, int x, int y) {
        graphics.text(font, text, x, y, INK_MUTED, false);
    }

    @Override
    public void levelLabel(GuiGraphicsExtractor graphics, Font font, String name, int color, int x, int y) {
        int ink = ARGB.scaleRGB(color, 0.55F);
        graphics.fill(x, y + 4, x + 2, y + 13, ink);
        graphics.text(font, name, x + 5, y + 5, ink, false);
    }

    @Override
    public void levelDivider(GuiGraphicsExtractor graphics, int x, int y, int width) {
        for (int dx = 0; dx < width; dx += 3) {
            graphics.fill(x + dx, y, x + Math.min(dx + 1, width), y + 1, 0x805B4126);
        }
    }

    @Override
    public void rowHighlight(GuiGraphicsExtractor graphics, int x, int y, int width, int height) {
        graphics.fill(x, y, x + width, y + height, 0x30E8C04A);
        graphics.fill(x, y, x + 1, y + height, WAX);
    }

    @Override
    public void rowHover(GuiGraphicsExtractor graphics, int x, int y, int width, int height) {
        graphics.fill(x, y, x + width, y + height, 0x14000000);
    }

    @Override
    public int bodyColor() {
        return INK_MUTED;
    }

    @Override
    public void slot(GuiGraphicsExtractor graphics, int x, int y, int size) {
        graphics.fill(x, y, x + size, y + size, PAPER_DARK);
        graphics.outline(x, y, size, size, 0xFFB89F73);
    }

    @Override
    public void arrow(GuiGraphicsExtractor graphics, Font font, int x, int y) {
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, TRADE_ARROW, x, y + 4, 10, 9, LEATHER);
    }

    @Override
    public void scrollbar(GuiGraphicsExtractor graphics, int x, int y, int height, int thumbY, int thumbHeight) {
        graphics.fill(x + 2, y, x + 4, y + height, 0x405B4126);
        graphics.fill(x + 1, thumbY, x + 5, thumbY + thumbHeight, WAX);
    }

    @Override
    public void button(GuiGraphicsExtractor graphics, int x, int y, int width, int height, boolean hovered) {
        graphics.fill(x, y, x + width, y + height, hovered ? 0xFFFBF3DE : PAPER_DARK);
        graphics.outline(x, y, width, height, hovered ? WAX : LEATHER);
    }

    @Override
    public void buttonLabel(GuiGraphicsExtractor graphics, Font font, String text, int centerX, int y, boolean hovered) {
        graphics.text(font, text, centerX - font.width(text) / 2, y, hovered ? WAX : INK, false);
    }
}
