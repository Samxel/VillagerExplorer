package com.samxel.villagerexplorer.screen;

import com.samxel.villagerexplorer.screen.style.GuiStyle;
import com.samxel.villagerexplorer.screen.style.GuiStyles;
import com.samxel.villagerexplorer.screen.style.Layout;
import com.samxel.villagerexplorer.villager.JobSiteRecipe;
import com.samxel.villagerexplorer.villager.Profession;
import com.samxel.villagerexplorer.villager.Trade;
import com.samxel.villagerexplorer.villager.TradeLevel;
import com.samxel.villagerexplorer.villager.VillagerTrades;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class VillagerInfoScreen extends Screen {
    private static final int SLOT_SIZE = 18;
    private static final int SLOT_SPACING = 4;
    private static final int ROW_HEIGHT = SLOT_SIZE + 6;
    private static final int PREVIEW_SIZE = 45;

    private final Profession profession;
    private final Screen parent;
    private final List<Trade> trades;
    private GuiStyle style;
    private Layout layout;
    private @Nullable Villager villager;
    private int scrollOffset;

    public VillagerInfoScreen(Profession profession, Screen parent) {
        super(Component.literal(profession.displayName()));
        this.profession = profession;
        this.parent = parent;
        this.trades = VillagerTrades.forProfession(profession);
    }

    @Override
    protected void init() {
        this.style = GuiStyles.current().style();
        this.layout = this.style.layout();
        this.villager = VillagerPreview.create(this.profession);
    }

    @Override
    public void onClose() {
        this.minecraft.gui.setScreen(this.parent);
    }

    private int maxScroll() {
        return Math.max(0, this.trades.size() - this.layout.tradesVisible());
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        double amount = scrollY != 0.0 ? scrollY : scrollX;
        if (amount < 0) {
            this.scrollOffset = Math.min(this.scrollOffset + 1, this.maxScroll());
        } else if (amount > 0) {
            this.scrollOffset = Math.max(this.scrollOffset - 1, 0);
        }
        return true;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        int windowX = (this.width - this.layout.width()) / 2;
        int windowY = (this.height - this.layout.height()) / 2;
        int right = windowX + this.layout.width();

        this.style.panel(graphics, windowX, windowY, this.layout.width(), this.layout.height());
        this.style.name(graphics, this.font, this.profession.displayName(), windowX + this.layout.nameX(), windowY + this.layout.nameY());

        if (this.villager != null) {
            int centerX = right - 70;
            InventoryScreen.extractEntityInInventoryFollowsMouse(graphics,
                    centerX - this.layout.previewHalfWidth(), windowY + this.layout.previewTop(),
                    centerX + this.layout.previewHalfWidth(), windowY + this.layout.previewBottom(),
                    PREVIEW_SIZE, this.layout.previewOffset(), mouseX, mouseY, this.villager);
        }

        this.extractTrades(graphics, windowX + this.layout.tradesX(), windowY + this.layout.tradesY(), mouseX, mouseY);
        this.extractJobSite(graphics, right - 120, windowY + this.layout.jobSiteY(), mouseX, mouseY);

        super.extractRenderState(graphics, mouseX, mouseY, a);
    }

    private void extractTrades(GuiGraphicsExtractor graphics, int tableX, int tableY, int mouseX, int mouseY) {
        this.style.sectionLabel(graphics, this.font, "Trades", tableX - 5, tableY - 14);

        int start = this.scrollOffset;
        int end = Math.min(this.trades.size(), start + this.layout.tradesVisible());
        TradeLevel lastLevel = null;

        for (int row = start; row < end; row++) {
            Trade trade = this.trades.get(row);
            int y = tableY + (row - start) * ROW_HEIGHT;

            if (trade.level() != lastLevel) {
                if (lastLevel != null) {
                    this.style.levelDivider(graphics, tableX - 5, y - 3, 225);
                }
                this.style.levelLabel(graphics, this.font, trade.level(), tableX - 5, y);
                lastLevel = trade.level();
            }

            int slotX = tableX + this.layout.levelWidth();
            for (ItemStack stack : trade.wanted()) {
                this.extractSlot(graphics, stack, slotX, y, mouseX, mouseY);
                slotX += SLOT_SIZE + SLOT_SPACING;
            }

            int arrowX = slotX + 2;
            this.style.arrow(graphics, this.font, arrowX, y);
            this.extractSlot(graphics, trade.given(), arrowX + 15, y, mouseX, mouseY);
        }

        if (this.trades.size() > this.layout.tradesVisible()) {
            int barArea = this.layout.tradesVisible() * ROW_HEIGHT;
            int barHeight = (int) ((float) this.layout.tradesVisible() / this.trades.size() * barArea);
            int barY = tableY + (int) ((float) this.scrollOffset / this.trades.size() * barArea);
            this.style.scrollbar(graphics, tableX + 230, tableY, barArea, barY, barHeight);
        }
    }

    private void extractJobSite(GuiGraphicsExtractor graphics, int gridX, int gridY, int mouseX, int mouseY) {
        JobSiteRecipe recipe = this.profession.jobSite();
        this.style.sectionLabel(graphics, this.font, "Job Site", gridX, gridY - 14);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                this.style.slot(graphics, gridX + col * SLOT_SIZE, gridY + row * SLOT_SIZE, SLOT_SIZE);
            }
        }

        for (JobSiteRecipe.Ingredient ingredient : recipe.ingredients()) {
            int slotX = gridX + ingredient.col() * SLOT_SIZE;
            int slotY = gridY + ingredient.row() * SLOT_SIZE;
            this.style.item(graphics, this.font, ingredient.stack(), slotX, slotY);
            this.tooltip(graphics, ingredient.stack(), slotX, slotY, mouseX, mouseY);
        }

        int arrowX = gridX + SLOT_SIZE * 4 - 10;
        this.style.arrow(graphics, this.font, arrowX, gridY + SLOT_SIZE);

        int resultX = gridX + SLOT_SIZE * 4 + 4;
        int resultY = gridY + SLOT_SIZE;
        if (this.style.framedResult()) {
            this.style.slot(graphics, resultX, resultY, SLOT_SIZE);
        }
        this.style.item(graphics, this.font, recipe.result(), resultX, resultY);
        this.tooltip(graphics, recipe.result(), resultX, resultY, mouseX, mouseY);
    }

    private void extractSlot(GuiGraphicsExtractor graphics, ItemStack stack, int x, int y, int mouseX, int mouseY) {
        this.style.slot(graphics, x, y, SLOT_SIZE);
        this.style.item(graphics, this.font, stack, x, y);
        this.tooltip(graphics, stack, x, y, mouseX, mouseY);
    }

    private void tooltip(GuiGraphicsExtractor graphics, ItemStack stack, int x, int y, int mouseX, int mouseY) {
        if (mouseX >= x && mouseX < x + SLOT_SIZE && mouseY >= y && mouseY < y + SLOT_SIZE) {
            graphics.setTooltipForNextFrame(this.font, stack, mouseX, mouseY);
        }
    }
}
