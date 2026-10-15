package com.samxel.villagerexplorer.screen.style;

import com.samxel.villagerexplorer.villager.TradeLevel;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public class VanillaStyle extends GuiStyle {
    private static final Identifier PANEL = Identifier.withDefaultNamespace("popup/background");
    private static final Identifier BUTTON = Identifier.withDefaultNamespace("widget/button");
    private static final Identifier BUTTON_HIGHLIGHTED = Identifier.withDefaultNamespace("widget/button_highlighted");
    private static final Identifier SLOT = Identifier.withDefaultNamespace("container/slot");
    private static final Identifier TRADE_ARROW = Identifier.withDefaultNamespace("container/villager/trade_arrow");
    private static final Identifier SCROLLER = Identifier.withDefaultNamespace("widget/scroller");
    private static final Identifier SCROLLER_BACKGROUND = Identifier.withDefaultNamespace("widget/scroller_background");
    private static final int LABEL = 0xFF404040;

    @Override
    public void panel(GuiGraphicsExtractor graphics, int x, int y, int width, int height) {
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, PANEL, x, y, width, height);
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
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, TRADE_ARROW, x, y + 4, 10, 9);
    }

    @Override
    public void scrollbar(GuiGraphicsExtractor graphics, int x, int y, int height, int thumbY, int thumbHeight) {
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, SCROLLER_BACKGROUND, x, y, 6, height);
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, SCROLLER, x, thumbY, 6, thumbHeight);
    }
}
