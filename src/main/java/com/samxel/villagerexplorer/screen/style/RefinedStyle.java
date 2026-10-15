package com.samxel.villagerexplorer.screen.style;

import com.samxel.villagerexplorer.villager.TradeLevel;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class RefinedStyle extends GuiStyle {
    private static final Identifier TRADE_ARROW = Identifier.withDefaultNamespace("container/villager/trade_arrow");
    private static final int SHADOW = 0xFF0B0907;
    private static final int BRONZE = 0xFF6B5638;
    private static final int BRONZE_DARK = 0xFF4A3C28;
    private static final int BRONZE_LIGHT = 0xFFB89A6A;
    private static final int EMERALD_GREEN = 0xFF2FBF71;
    private static final int GOLD = 0xFFF0C75E;
    private static final int PARCHMENT = 0xFFE8DCC8;
    private static final int INSET = 0xFF16120E;

    @Override
    public void panel(GuiGraphicsExtractor graphics, int x, int y, int width, int height) {
        graphics.fillGradient(x, y, x + width, y + height, 0xF2241E18, 0xF2181410);
        graphics.outline(x, y, width, height, SHADOW);
        graphics.outline(x + 1, y + 1, width - 2, height - 2, BRONZE);
        graphics.outline(x + 2, y + 2, width - 4, height - 4, 0x40000000);
        corner(graphics, x + 1, y + 1);
        corner(graphics, x + width - 5, y + 1);
        corner(graphics, x + 1, y + height - 5);
        corner(graphics, x + width - 5, y + height - 5);
    }

    private static void corner(GuiGraphicsExtractor graphics, int x, int y) {
        graphics.fill(x, y, x + 4, y + 4, EMERALD_GREEN);
        graphics.fill(x + 1, y + 1, x + 3, y + 3, 0xFF8AF0B4);
    }

    @Override
    public void title(GuiGraphicsExtractor graphics, Font font, String text, int centerX, int y) {
        int textWidth = font.width(text);
        int textX = centerX - textWidth / 2 + 9;
        graphics.item(new ItemStack(Items.EMERALD), textX - 20, y - 4);
        graphics.text(font, text, textX, y, GOLD, true);
        divider(graphics, centerX, y + 12, 70);
    }

    private static void divider(GuiGraphicsExtractor graphics, int centerX, int y, int halfWidth) {
        graphics.fill(centerX - halfWidth / 2, y, centerX + halfWidth / 2, y + 1, EMERALD_GREEN);
        graphics.fill(centerX - halfWidth, y, centerX - halfWidth / 2, y + 1, 0x802FBF71);
        graphics.fill(centerX + halfWidth / 2, y, centerX + halfWidth, y + 1, 0x802FBF71);
    }

    @Override
    public boolean borderedSearch() {
        return false;
    }

    @Override
    public void searchBackground(GuiGraphicsExtractor graphics, int x, int y, int width, int height, boolean focused) {
        bevel(graphics, x, y, width, height, INSET, SHADOW, focused ? EMERALD_GREEN : BRONZE_DARK);
    }

    @Override
    public int searchTextColor() {
        return PARCHMENT;
    }

    @Override
    public void tile(GuiGraphicsExtractor graphics, int x, int y, int size, boolean hovered) {
        graphics.fillGradient(x, y, x + size, y + size, hovered ? 0xFF3A3024 : 0xFF2E261E, 0xFF1E1914);
        graphics.outline(x, y, size, size, hovered ? EMERALD_GREEN : BRONZE_DARK);
        if (hovered) {
            graphics.outline(x + 1, y + 1, size - 2, size - 2, 0x402FBF71);
        }
    }

    @Override
    public void tileLabel(GuiGraphicsExtractor graphics, Font font, String text, int centerX, int y, boolean hovered) {
        graphics.text(font, text, centerX - font.width(text) / 2, y, hovered ? GOLD : PARCHMENT, true);
    }

    @Override
    public void name(GuiGraphicsExtractor graphics, Font font, String text, int x, int y) {
        graphics.pose().pushMatrix();
        graphics.pose().translate(x, y - 2);
        graphics.pose().scale(1.5F, 1.5F);
        graphics.text(font, text, 0, 0, GOLD, true);
        graphics.pose().popMatrix();
    }

    @Override
    public void sectionLabel(GuiGraphicsExtractor graphics, Font font, String text, int x, int y) {
        graphics.text(font, text, x, y, BRONZE_LIGHT, false);
        graphics.fill(x, y + 10, x + font.width(text), y + 11, BRONZE);
    }

    @Override
    public void levelLabel(GuiGraphicsExtractor graphics, Font font, TradeLevel level, int x, int y) {
        graphics.fill(x, y + 7, x + 3, y + 10, level.color());
        graphics.text(font, level.displayName(), x + 6, y + 5, level.color(), true);
    }

    @Override
    public void levelDivider(GuiGraphicsExtractor graphics, int x, int y, int width) {
        for (int dx = 0; dx < width; dx += 4) {
            graphics.fill(x + dx, y, x + Math.min(dx + 2, width), y + 1, 0x806B5638);
        }
    }

    @Override
    public void slot(GuiGraphicsExtractor graphics, int x, int y, int size) {
        bevel(graphics, x, y, size, size, INSET, SHADOW, 0xFF5A4A32);
    }

    @Override
    public void arrow(GuiGraphicsExtractor graphics, Font font, int x, int y) {
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, TRADE_ARROW, x, y + 4, 10, 9, GOLD);
    }

    @Override
    public void scrollbar(GuiGraphicsExtractor graphics, int x, int y, int height, int thumbY, int thumbHeight) {
        bevel(graphics, x, y, 6, height, INSET, SHADOW, BRONZE_DARK);
        graphics.fill(x + 1, thumbY, x + 5, thumbY + thumbHeight, 0xFF8A7048);
        graphics.fill(x + 1, thumbY, x + 5, thumbY + 1, BRONZE_LIGHT);
    }
}
