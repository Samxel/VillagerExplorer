package com.samxel.villagerexplorer.screen;

import com.mojang.blaze3d.platform.InputConstants;
import com.samxel.villagerexplorer.screen.style.GuiStyle;
import com.samxel.villagerexplorer.screen.style.GuiStyles;
import com.samxel.villagerexplorer.screen.style.Layout;
import com.samxel.villagerexplorer.villager.Profession;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.npc.villager.Villager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class VillagerExplorerScreen extends Screen {
    private static final String TITLE = "Villager Explorer";
    private static final int TILE_SIZE = 90;
    private static final int TILE_SPACING = 18;
    private static final int TILES_PER_ROW = 3;
    private static final int SEARCH_HEIGHT = 20;

    private final Map<Profession, Villager> villagers = new EnumMap<>(Profession.class);
    private final List<Tile> visibleTiles = new ArrayList<>();
    private List<Profession> filtered = List.of();
    private GuiStyle style;
    private Layout layout;
    private EditBox searchField;
    private int scrollOffset;
    private int maxScroll;

    public VillagerExplorerScreen() {
        super(Component.literal(TITLE));
    }

    @Override
    protected void init() {
        this.style = GuiStyles.current().style();
        this.layout = this.style.layout();

        int searchX = this.windowX() + 20;
        int searchY = this.windowY() + this.layout.searchY();
        int searchWidth = this.layout.width() - 40;
        if (this.style.borderedSearch()) {
            this.searchField = new EditBox(this.font, searchX, searchY, searchWidth, SEARCH_HEIGHT, Component.literal("Search Villager"));
        } else {
            this.searchField = new EditBox(this.font, searchX + 6, searchY + 6, searchWidth - 12, SEARCH_HEIGHT - 8, Component.literal("Search Villager"));
            this.searchField.setBordered(false);
            this.searchField.setTextColor(this.style.searchTextColor());
        }
        this.searchField.setHint(Component.literal("Search..."));
        this.searchField.setResponder(this::updateFilter);
        this.addRenderableWidget(this.searchField);
        this.setInitialFocus(this.searchField);

        for (Profession profession : Profession.values()) {
            Villager villager = VillagerPreview.create(profession);
            if (villager != null) {
                this.villagers.put(profession, villager);
            }
        }

        this.updateFilter(this.searchField.getValue());
    }

    private void updateFilter(String filter) {
        String query = filter.toLowerCase(Locale.ROOT);
        this.filtered = Arrays.stream(Profession.values())
                .filter(profession -> profession.displayName().toLowerCase(Locale.ROOT).contains(query))
                .toList();
        this.updateScroll();
    }

    private void updateScroll() {
        this.maxScroll = Math.max(0, this.rowCount() - this.visibleRows());
        this.scrollOffset = Math.clamp(this.scrollOffset, 0, this.maxScroll);
    }

    private int rowCount() {
        return (this.filtered.size() + TILES_PER_ROW - 1) / TILES_PER_ROW;
    }

    private int visibleRows() {
        return (this.tileAreaBottom() - this.layout.tileTop()) / (TILE_SIZE + TILE_SPACING);
    }

    private int tileAreaBottom() {
        return this.layout.height() - 20;
    }

    private int windowX() {
        return (this.width - this.layout.width()) / 2;
    }

    private int windowY() {
        return (this.height - this.layout.height()) / 2;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        double amount = scrollY != 0.0 ? scrollY : scrollX;
        if (this.maxScroll > 0 && amount != 0.0) {
            this.scrollOffset = Math.clamp(this.scrollOffset - (int) Math.signum(amount), 0, this.maxScroll);
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (this.searchField.keyPressed(event)) {
            return true;
        }

        if (event.key() == InputConstants.KEY_DOWN && this.scrollOffset < this.maxScroll) {
            this.scrollOffset++;
            return true;
        }

        if (event.key() == InputConstants.KEY_UP && this.scrollOffset > 0) {
            this.scrollOffset--;
            return true;
        }

        return super.keyPressed(event);
    }

    @Override
    public boolean charTyped(CharacterEvent event) {
        return this.searchField.charTyped(event) || super.charTyped(event);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        if (super.mouseClicked(event, doubleClick)) {
            return true;
        }

        for (Tile tile : this.visibleTiles) {
            if (tile.contains(event.x(), event.y())) {
                this.minecraft.gui.setScreen(new VillagerInfoScreen(tile.profession(), this));
                return true;
            }
        }
        return false;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        int windowX = this.windowX();
        int windowY = this.windowY();
        this.visibleTiles.clear();

        this.style.panel(graphics, windowX, windowY, this.layout.width(), this.layout.height());
        this.style.title(graphics, this.font, TITLE, windowX + this.layout.width() / 2, windowY + this.layout.titleY());

        if (!this.style.borderedSearch()) {
            this.style.searchBackground(graphics, windowX + 20, windowY + this.layout.searchY(),
                    this.layout.width() - 40, SEARCH_HEIGHT, this.searchField.isFocused());
        }

        int rows = this.rowCount();
        int firstRow = this.scrollOffset;
        int lastRow = Math.min(rows, firstRow + this.visibleRows());
        int startY = windowY + this.layout.tileTop();

        for (int row = firstRow; row < lastRow; row++) {
            int tilesInRow = Math.min(TILES_PER_ROW, this.filtered.size() - row * TILES_PER_ROW);
            int rowWidth = tilesInRow * TILE_SIZE + (tilesInRow - 1) * TILE_SPACING;
            int rowStartX = windowX + (this.layout.width() - rowWidth) / 2;

            for (int col = 0; col < tilesInRow; col++) {
                Profession profession = this.filtered.get(row * TILES_PER_ROW + col);
                int x = rowStartX + col * (TILE_SIZE + TILE_SPACING);
                int y = startY + (row - firstRow) * (TILE_SIZE + TILE_SPACING);
                Tile tile = new Tile(x, y, profession);
                boolean hovered = tile.contains(mouseX, mouseY);

                this.style.tile(graphics, x, y, TILE_SIZE, hovered);

                Villager villager = this.villagers.get(profession);
                if (villager != null) {
                    int boxHalf = 28;
                    int centerX = x + TILE_SIZE / 2;
                    VillagerPreview.extractFacingFront(graphics, villager,
                            centerX - boxHalf, y + 10, centerX + boxHalf, y + 10 + boxHalf * 2, 30.0F);
                }

                this.style.tileLabel(graphics, this.font, profession.displayName(), x + TILE_SIZE / 2, y + TILE_SIZE - 18, hovered);
                this.visibleTiles.add(tile);
            }
        }

        if (this.maxScroll > 0) {
            int barArea = this.tileAreaBottom() - this.layout.tileTop();
            int barHeight = Math.max(20, (int) ((float) this.visibleRows() / rows * barArea));
            int barY = startY + (int) ((float) this.scrollOffset / this.maxScroll * (barArea - barHeight));
            this.style.scrollbar(graphics, windowX + this.layout.width() - 10, startY, barArea, barY, barHeight);
        }

        super.extractRenderState(graphics, mouseX, mouseY, a);
    }

    private record Tile(int x, int y, Profession profession) {
        boolean contains(double mouseX, double mouseY) {
            return mouseX >= this.x && mouseX < this.x + TILE_SIZE && mouseY >= this.y && mouseY < this.y + TILE_SIZE;
        }
    }
}
