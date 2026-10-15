package com.samxel.villagerexplorer.screen.style;

import com.samxel.villagerexplorer.villager.TradeLevel;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.item.ItemStack;

public class ClassicStyle extends GuiStyle {
    private static final int WHITE = 0xFFFFFFFF;

    @Override
    public Layout layout() {
        return Layout.CLASSIC;
    }

    @Override
    public void panel(GuiGraphicsExtractor graphics, int x, int y, int width, int height) {
        graphics.fill(x, y, x + width, y + height, 0xCC222222);
        graphics.outline(x, y, width, height, 0xFF000000);
    }

    @Override
    public void title(GuiGraphicsExtractor graphics, Font font, String text, int centerX, int y) {
        graphics.text(font, text, centerX - font.width(text) / 2, y, WHITE, false);
    }

    @Override
    public void tile(GuiGraphicsExtractor graphics, int x, int y, int size, boolean hovered) {
        graphics.fill(x, y, x + size, y + size, 0x10FFFFFF);
        graphics.outline(x, y, size, size, WHITE);
    }

    @Override
    public void tileLabel(GuiGraphicsExtractor graphics, Font font, String text, int centerX, int y, boolean hovered) {
        graphics.text(font, text, centerX - font.width(text) / 2, y, WHITE, false);
    }

    @Override
    public void name(GuiGraphicsExtractor graphics, Font font, String text, int x, int y) {
        graphics.text(font, text, x, y, WHITE, false);
    }

    @Override
    public void levelLabel(GuiGraphicsExtractor graphics, Font font, TradeLevel level, int x, int y) {
        graphics.text(font, level.displayName(), x, y + 4, WHITE, false);
    }

    @Override
    public void slot(GuiGraphicsExtractor graphics, int x, int y, int size) {
        graphics.fill(x, y, x + size, y + size, 0xFF444444);
        graphics.outline(x, y, size, size, 0xFF000000);
    }

    @Override
    public void item(GuiGraphicsExtractor graphics, Font font, ItemStack stack, int x, int y) {
        graphics.item(stack, x + 1, y + 1);
        if (stack.getCount() > 1) {
            graphics.text(font, Integer.toString(stack.getCount()), x + 2, y + 10, WHITE, true);
        }
    }

    @Override
    public boolean framedResult() {
        return false;
    }

    @Override
    public void arrow(GuiGraphicsExtractor graphics, Font font, int x, int y) {
        graphics.text(font, "→", x, y + 5, WHITE, false);
    }

    @Override
    public void scrollbar(GuiGraphicsExtractor graphics, int x, int y, int height, int thumbY, int thumbHeight) {
        graphics.fill(x, y, x + 6, y + height, 0xFF222222);
        graphics.fill(x, thumbY, x + 6, thumbY + thumbHeight, 0xFF888888);
    }
}
