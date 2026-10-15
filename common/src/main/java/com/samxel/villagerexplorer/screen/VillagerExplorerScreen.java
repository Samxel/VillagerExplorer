package com.samxel.villagerexplorer.screen;

import com.mojang.blaze3d.platform.InputConstants;
import com.samxel.villagerexplorer.screen.style.GuiStyle;
import com.samxel.villagerexplorer.screen.style.GuiStyles;
import com.samxel.villagerexplorer.screen.style.Layout;
import com.samxel.villagerexplorer.villager.Merchant;
import com.samxel.villagerexplorer.villager.TradeSearch;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class VillagerExplorerScreen extends Screen {
    private static final String TITLE = "Villager Explorer";
    private static final Component SEARCH_HINT = Component.literal("Search for a job, villager or item...");
    private static final int TILE_SIZE = 90;
    private static final int MIN_TILE_SIZE = 72;
    private static final int TILE_SPACING = 18;
    private static final int TILES_PER_ROW = 3;
    private static final int SEARCH_HEIGHT = 20;

    private final Map<Merchant, LivingEntity> previews = new EnumMap<>(Merchant.class);
    private final Map<Merchant, ItemStack> matchedItems = new EnumMap<>(Merchant.class);
    private final List<Tile> visibleTiles = new ArrayList<>();
    private final ScrollBar scrollBar = new ScrollBar();
    private List<Merchant> filtered = List.of();
    private TradeSearch search = TradeSearch.EMPTY;
    private String query = "";
    private GuiStyle style;
    private Layout layout;
    private EditBox searchField;
    private int tileSize = TILE_SIZE;
    private final @Nullable Screen parent;
    private int scrollOffset;

    public VillagerExplorerScreen() {
        this(null);
    }

    public VillagerExplorerScreen(@Nullable Screen parent) {
        super(Component.literal(TITLE));
        this.parent = parent;
    }

    @Override
    public void onClose() {
        this.minecraft.gui.setScreen(this.parent);
    }

    @Override
    protected void init() {
        this.style = GuiStyles.current().style();
        this.layout = this.style.layout().fit(this.height);
        int area = this.tileAreaBottom() - this.layout.tileTop();
        this.tileSize = this.layout == Layout.CLASSIC ? TILE_SIZE : Math.clamp((area - TILE_SPACING) / 2, MIN_TILE_SIZE, TILE_SIZE);

        int searchX = this.windowX() + 20;
        int searchY = this.windowY() + this.layout.searchY();
        int searchWidth = this.layout.width() - 40;
        if (this.style.borderedSearch()) {
            this.searchField = new EditBox(this.font, searchX, searchY, searchWidth, SEARCH_HEIGHT, SEARCH_HINT);
        } else {
            this.searchField = new EditBox(this.font, searchX + 6, searchY + 6, searchWidth - 12, SEARCH_HEIGHT - 8, SEARCH_HINT);
            this.searchField.setBordered(false);
            this.searchField.setTextColor(this.style.searchTextColor());
        }
        this.searchField.setHint(SEARCH_HINT);
        this.searchField.setValue(this.query);
        this.searchField.setResponder(this::updateFilter);
        this.addRenderableWidget(this.searchField);
        this.setInitialFocus(this.searchField);

        for (Merchant merchant : Merchant.values()) {
            LivingEntity preview = VillagerPreview.create(merchant);
            if (preview != null) {
                this.previews.put(merchant, preview);
            }
        }

        this.updateFilter(this.query);
    }

    private void updateFilter(String value) {
        this.query = value;
        this.search = new TradeSearch(value);
        this.matchedItems.clear();

        List<Merchant> result = new ArrayList<>();
        for (Merchant merchant : Merchant.values()) {
            if (this.search.isEmpty() || this.search.matchesName(merchant)) {
                result.add(merchant);
                continue;
            }
            ItemStack item = this.search.matchingItem(merchant);
            if (item != null) {
                this.matchedItems.put(merchant, item);
                result.add(merchant);
            }
        }
        this.filtered = List.copyOf(result);
        this.setScroll(this.scrollOffset);
    }

    private void setScroll(int offset) {
        this.scrollOffset = Math.clamp(offset, 0, this.maxScroll());
    }

    private int maxScroll() {
        return Math.max(0, this.rowCount() - this.visibleRows());
    }

    private int rowCount() {
        return (this.filtered.size() + TILES_PER_ROW - 1) / TILES_PER_ROW;
    }

    private int visibleRows() {
        return Math.max(1, (this.tileAreaBottom() - this.layout.tileTop() + TILE_SPACING) / (this.tileSize + TILE_SPACING));
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
        if (this.maxScroll() > 0 && amount != 0.0) {
            this.setScroll(this.scrollOffset - (int) Math.signum(amount));
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (this.searchField.keyPressed(event)) {
            return true;
        }
        if (event.key() == InputConstants.KEY_DOWN && this.scrollOffset < this.maxScroll()) {
            this.setScroll(this.scrollOffset + 1);
            return true;
        }
        if (event.key() == InputConstants.KEY_UP && this.scrollOffset > 0) {
            this.setScroll(this.scrollOffset - 1);
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
        if (this.scrollBar.press(event.x(), event.y())) {
            this.setScroll(this.scrollBar.drag(event.y()));
            return true;
        }
        if (super.mouseClicked(event, doubleClick)) {
            return true;
        }
        for (Tile tile : this.visibleTiles) {
            if (tile.contains(event.x(), event.y())) {
                this.minecraft.gui.setScreen(new VillagerInfoScreen(tile.merchant(), this.search, this));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dx, double dy) {
        if (this.scrollBar.dragging()) {
            this.setScroll(this.scrollBar.drag(event.y()));
            return true;
        }
        return super.mouseDragged(event, dx, dy);
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        if (this.scrollBar.dragging()) {
            this.scrollBar.release();
            return true;
        }
        return super.mouseReleased(event);
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

        int startY = windowY + this.layout.tileTop();
        if (this.filtered.isEmpty()) {
            String text = "Nobody trades \"" + this.query + "\"";
            this.style.bodyText(graphics, this.font, text, windowX + (this.layout.width() - this.font.width(text)) / 2, startY + 20);
        }

        int lastRow = Math.min(this.rowCount(), this.scrollOffset + this.visibleRows());
        for (int row = this.scrollOffset; row < lastRow; row++) {
            int tilesInRow = Math.min(TILES_PER_ROW, this.filtered.size() - row * TILES_PER_ROW);
            int rowWidth = tilesInRow * this.tileSize + (tilesInRow - 1) * TILE_SPACING;
            int rowStartX = windowX + (this.layout.width() - rowWidth) / 2;

            for (int col = 0; col < tilesInRow; col++) {
                Merchant merchant = this.filtered.get(row * TILES_PER_ROW + col);
                int x = rowStartX + col * (this.tileSize + TILE_SPACING);
                int y = startY + (row - this.scrollOffset) * (this.tileSize + TILE_SPACING);
                Tile tile = new Tile(x, y, this.tileSize, merchant);
                this.extractTile(graphics, tile, tile.contains(mouseX, mouseY) && !this.scrollBar.dragging(), mouseX, mouseY);
                this.visibleTiles.add(tile);
            }
        }

        int barArea = this.tileAreaBottom() - this.layout.tileTop();
        this.scrollBar.update(windowX + this.layout.width() - 10, startY, barArea, this.visibleRows(), this.rowCount(), this.scrollOffset);
        this.scrollBar.extract(this.style, graphics);

        super.extractRenderState(graphics, mouseX, mouseY, a);
        this.extractFocusedHint(graphics);
    }

    // EditBox only draws its hint while unfocused, but the search field is focused right away
    private void extractFocusedHint(GuiGraphicsExtractor graphics) {
        if (!this.searchField.isFocused() || !this.searchField.getValue().isEmpty()) {
            return;
        }
        int x = this.searchField.getX() + (this.style.borderedSearch() ? 4 : 0) + this.font.width("_") + 1;
        int y = this.searchField.getY() + (this.style.borderedSearch() ? (SEARCH_HEIGHT - 8) / 2 : 0);
        graphics.text(this.font, SEARCH_HINT, x, y, 0xFF808080, false);
    }

    private void extractTile(GuiGraphicsExtractor graphics, Tile tile, boolean hovered, int mouseX, int mouseY) {
        int size = tile.size();
        this.style.tile(graphics, tile.x(), tile.y(), size, hovered);

        LivingEntity preview = this.previews.get(tile.merchant());
        if (preview != null) {
            int boxHalf = size * 28 / TILE_SIZE;
            int boxTop = tile.y() + size / 9;
            int centerX = tile.x() + size / 2;
            VillagerPreview.extractFacingFront(graphics, preview, centerX - boxHalf, boxTop, centerX + boxHalf, boxTop + boxHalf * 2, 30.0F * size / TILE_SIZE);
        }

        String name = tile.merchant().displayName();
        int labelX = tile.x() + size / 2;
        int labelY = tile.y() + size - 18;
        int maxWidth = size - 6;
        if (this.font.width(name) > maxWidth) {
            float scale = (float) maxWidth / this.font.width(name);
            graphics.pose().pushMatrix();
            graphics.pose().translate(labelX, labelY + (1.0F - scale) * 4.0F);
            graphics.pose().scale(scale, scale);
            this.style.tileLabel(graphics, this.font, name, 0, 0, hovered);
            graphics.pose().popMatrix();
        } else {
            this.style.tileLabel(graphics, this.font, name, labelX, labelY, hovered);
        }

        ItemStack matched = this.matchedItems.get(tile.merchant());
        if (matched != null) {
            int iconX = tile.x() + size - 22;
            int iconY = tile.y() + 4;
            this.style.slot(graphics, iconX, iconY, 18);
            graphics.item(matched, iconX + 1, iconY + 1);
            if (mouseX >= iconX && mouseX < iconX + 18 && mouseY >= iconY && mouseY < iconY + 18) {
                graphics.setTooltipForNextFrame(this.font, matched, mouseX, mouseY);
            }
        }
    }

    private record Tile(int x, int y, int size, Merchant merchant) {
        boolean contains(double mouseX, double mouseY) {
            return mouseX >= this.x && mouseX < this.x + this.size && mouseY >= this.y && mouseY < this.y + this.size;
        }
    }
}
