package com.samxel.villagerexplorer.screen.style;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.item.ItemStack;

public abstract class GuiStyle {

    public Layout layout() {
        return Layout.COMPACT;
    }

    public abstract void panel(GuiGraphicsExtractor graphics, int x, int y, int width, int height);

    public abstract void title(GuiGraphicsExtractor graphics, Font font, String text, int centerX, int y);

    public boolean borderedSearch() {
        return true;
    }

    public void searchBackground(GuiGraphicsExtractor graphics, int x, int y, int width, int height, boolean focused) {
    }

    public int searchTextColor() {
        return 0xFFE0E0E0;
    }

    public abstract void tile(GuiGraphicsExtractor graphics, int x, int y, int size, boolean hovered);

    public abstract void tileLabel(GuiGraphicsExtractor graphics, Font font, String text, int centerX, int y, boolean hovered);

    public abstract void name(GuiGraphicsExtractor graphics, Font font, String text, int x, int y);

    public void sectionLabel(GuiGraphicsExtractor graphics, Font font, String text, int x, int y) {
    }

    public abstract void levelLabel(GuiGraphicsExtractor graphics, Font font, String name, int color, int x, int y);

    public void levelDivider(GuiGraphicsExtractor graphics, int x, int y, int width) {
    }

    public void rowHighlight(GuiGraphicsExtractor graphics, int x, int y, int width, int height) {
        graphics.fill(x, y, x + width, y + height, 0x30FFFF80);
    }

    public int bodyColor() {
        return 0xFFAAAAAA;
    }

    public void bodyText(GuiGraphicsExtractor graphics, Font font, String text, int x, int y) {
        graphics.text(font, text, x, y, this.bodyColor(), false);
    }

    public void button(GuiGraphicsExtractor graphics, int x, int y, int width, int height, boolean hovered) {
        this.tile(graphics, x, y, width, hovered);
    }

    public void buttonLabel(GuiGraphicsExtractor graphics, Font font, String text, int centerX, int y, boolean hovered) {
        this.tileLabel(graphics, font, text, centerX, y, hovered);
    }

    public abstract void slot(GuiGraphicsExtractor graphics, int x, int y, int size);

    public void item(GuiGraphicsExtractor graphics, Font font, ItemStack stack, int x, int y) {
        graphics.item(stack, x + 1, y + 1);
        graphics.itemDecorations(font, stack, x + 1, y + 1);
    }

    public boolean framedResult() {
        return true;
    }

    public abstract void arrow(GuiGraphicsExtractor graphics, Font font, int x, int y);

    public abstract void scrollbar(GuiGraphicsExtractor graphics, int x, int y, int height, int thumbY, int thumbHeight);

    protected static void roundedFill(GuiGraphicsExtractor graphics, int x, int y, int width, int height, int color) {
        graphics.fill(x + 2, y, x + width - 2, y + height, color);
        graphics.fill(x + 1, y + 1, x + 2, y + height - 1, color);
        graphics.fill(x + width - 2, y + 1, x + width - 1, y + height - 1, color);
        graphics.fill(x, y + 2, x + 1, y + height - 2, color);
        graphics.fill(x + width - 1, y + 2, x + width, y + height - 2, color);
    }

    protected static void roundedOutline(GuiGraphicsExtractor graphics, int x, int y, int width, int height, int color) {
        graphics.fill(x + 2, y, x + width - 2, y + 1, color);
        graphics.fill(x + 2, y + height - 1, x + width - 2, y + height, color);
        graphics.fill(x, y + 2, x + 1, y + height - 2, color);
        graphics.fill(x + width - 1, y + 2, x + width, y + height - 2, color);
        graphics.fill(x + 1, y + 1, x + 2, y + 2, color);
        graphics.fill(x + width - 2, y + 1, x + width - 1, y + 2, color);
        graphics.fill(x + 1, y + height - 2, x + 2, y + height - 1, color);
        graphics.fill(x + width - 2, y + height - 2, x + width - 1, y + height - 1, color);
    }

    protected static void bevel(GuiGraphicsExtractor graphics, int x, int y, int width, int height, int fill, int dark, int light) {
        graphics.fill(x, y, x + width, y + height, fill);
        graphics.fill(x, y, x + width - 1, y + 1, dark);
        graphics.fill(x, y, x + 1, y + height - 1, dark);
        graphics.fill(x + 1, y + height - 1, x + width, y + height, light);
        graphics.fill(x + width - 1, y + 1, x + width, y + height, light);
    }
}
