package com.samxel.villagerexplorer.screen.style;

import com.samxel.villagerexplorer.villager.TradeLevel;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public class VanillaStyle extends GuiStyle {
    private static final Identifier BUTTON = Identifier.withDefaultNamespace("widget/button");
    private static final Identifier BUTTON_HIGHLIGHTED = Identifier.withDefaultNamespace("widget/button_highlighted");
    private static final Identifier SLOT = Identifier.withDefaultNamespace("container/slot");
    private static final Identifier TRADE_ARROW = Identifier.withDefaultNamespace("container/villager/trade_arrow");
    private static final int LABEL = 0xFF404040;

    @Override
    public void panel(GuiGraphicsExtractor graphics, int x, int y, int width, int height) {
        graphics.fill(x + 1, y + 1, x + width - 1, y + height - 1, 0xFFC6C6C6);
        roundedOutline(graphics, x, y, width, height, 0xFF000000);
        graphics.fill(x + 2, y + 1, x + width - 3, y + 3, 0xFFFFFFFF);
        graphics.fill(x + 1, y + 2, x + 3, y + height - 3, 0xFFFFFFFF);
        graphics.fill(x + 3, y + height - 3, x + width - 2, y + height - 1, 0xFF555555);
        graphics.fill(x + width - 3, y + 3, x + width - 1, y + height - 2, 0xFF555555);
    }

    @Override
    public void title(GuiGraphicsExtractor graphics, Font font, String text, int centerX, int y) {
        graphics.text(font, text, centerX - font.width(text) / 2, y, LABEL, false);
    }

    @Override
    public void tile(GuiGraphicsExtractor graphics, int x, int y, int size, boolean hovered) {
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, hovered ? BUTTON_HIGHLIGHTED : BUTTON, x, y, size, size);
    }

    @Override
    public void tileLabel(GuiGraphicsExtractor graphics, Font font, String text, int centerX, int y, boolean hovered) {
        graphics.text(font, text, centerX - font.width(text) / 2, y, hovered ? 0xFFFFFFA0 : 0xFFFFFFFF, true);
    }

    @Override
    public void name(GuiGraphicsExtractor graphics, Font font, String text, int x, int y) {
        graphics.text(font, text, x, y, LABEL, false);
    }

    @Override
    public void sectionLabel(GuiGraphicsExtractor graphics, Font font, String text, int x, int y) {
        graphics.text(font, text, x, y, LABEL, false);
    }

    @Override
    public void levelLabel(GuiGraphicsExtractor graphics, Font font, TradeLevel level, int x, int y) {
        graphics.text(font, level.displayName(), x, y + 5, LABEL, false);
    }

    @Override
    public void levelDivider(GuiGraphicsExtractor graphics, int x, int y, int width) {
        graphics.fill(x, y, x + width, y + 1, 0xFF8B8B8B);
        graphics.fill(x, y + 1, x + width, y + 2, 0xFFFFFFFF);
    }

    @Override
    public void slot(GuiGraphicsExtractor graphics, int x, int y, int size) {
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, SLOT, x, y, size, size);
    }

    @Override
    public void arrow(GuiGraphicsExtractor graphics, Font font, int x, int y) {
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, TRADE_ARROW, x, y + 4, 10, 9, 0xFF555555);
    }

    @Override
    public void scrollbar(GuiGraphicsExtractor graphics, int x, int y, int height, int thumbY, int thumbHeight) {
        bevel(graphics, x - 1, y, 8, height, 0xFF8B8B8B, 0xFF373737, 0xFFFFFFFF);
        graphics.outline(x, thumbY, 6, thumbHeight, 0xFF000000);
        bevel(graphics, x + 1, thumbY + 1, 4, thumbHeight - 2, 0xFFC6C6C6, 0xFFFFFFFF, 0xFF555555);
    }
}
