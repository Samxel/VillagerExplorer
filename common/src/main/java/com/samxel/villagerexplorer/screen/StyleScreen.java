package com.samxel.villagerexplorer.screen;

import com.samxel.villagerexplorer.config.StyleConfig;
import com.samxel.villagerexplorer.screen.style.GuiStyles;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.Nullable;

public class StyleScreen extends Screen {
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 20;
    private static final int SPACING = 4;

    private final @Nullable Screen parent;

    public StyleScreen(@Nullable Screen parent) {
        super(Component.literal("Villager Explorer Style"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int x = (this.width - BUTTON_WIDTH) / 2;
        int y = this.height / 2 - (GuiStyles.values().length * (BUTTON_HEIGHT + SPACING) + 40) / 2;

        for (GuiStyles style : GuiStyles.values()) {
            boolean selected = style == GuiStyles.current();
            Component label = Component.literal(selected ? "> " + displayName(style) + " <" : displayName(style));
            this.addRenderableWidget(Button.builder(label, button -> this.select(style))
                    .bounds(x, y, BUTTON_WIDTH, BUTTON_HEIGHT)
                    .build());
            y += BUTTON_HEIGHT + SPACING;
        }

        y += 12;
        int halfWidth = (BUTTON_WIDTH - SPACING) / 2;
        boolean canPreview = this.minecraft.level != null;
        Button preview = Button.builder(Component.literal("Preview"), button -> this.minecraft.gui.setScreen(new VillagerExplorerScreen(this)))
                .bounds(x, y, halfWidth, BUTTON_HEIGHT)
                .build();
        preview.active = canPreview;
        this.addRenderableWidget(preview);
        this.addRenderableWidget(Button.builder(Component.literal("Done"), button -> this.onClose())
                .bounds(x + halfWidth + SPACING, y, halfWidth, BUTTON_HEIGHT)
                .build());
    }

    private void select(GuiStyles style) {
        GuiStyles.setCurrent(style);
        StyleConfig.save();
        this.rebuildWidgets();
    }

    private static String displayName(GuiStyles style) {
        String id = style.id();
        return Character.toUpperCase(id.charAt(0)) + id.substring(1);
    }

    @Override
    public void onClose() {
        this.minecraft.gui.setScreen(this.parent);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);
        int top = this.height / 2 - (GuiStyles.values().length * (BUTTON_HEIGHT + SPACING) + 40) / 2;
        graphics.centeredText(this.font, this.title, this.width / 2, top - 30, 0xFFFFFFFF);
        if (this.minecraft.level == null) {
            graphics.centeredText(this.font, Component.literal("Join a world to preview the explorer"), this.width / 2, top - 16, 0xFFA0A0A0);
        }
    }
}
