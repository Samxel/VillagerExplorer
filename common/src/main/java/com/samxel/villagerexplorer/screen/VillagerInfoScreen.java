package com.samxel.villagerexplorer.screen;

import com.samxel.villagerexplorer.screen.style.GuiStyle;
import com.samxel.villagerexplorer.screen.style.GuiStyles;
import com.samxel.villagerexplorer.screen.style.Layout;
import com.samxel.villagerexplorer.villager.JobSiteRecipe;
import com.samxel.villagerexplorer.villager.Merchant;
import com.samxel.villagerexplorer.villager.Trade;
import com.samxel.villagerexplorer.villager.TradeGroup;
import com.samxel.villagerexplorer.villager.TradeSearch;
import com.samxel.villagerexplorer.villager.TradeStack;
import com.samxel.villagerexplorer.villager.VillagerBiome;
import com.samxel.villagerexplorer.villager.VillagerTrades;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VillagerInfoScreen extends Screen {
    private static final int SLOT_SIZE = 18;
    private static final int SLOT_SPACING = 4;
    private static final int ROW_HEIGHT = SLOT_SIZE + 6;
    private static final int TABLE_WIDTH = 225;
    private static final int PREVIEW_SIZE = 45;
    private static final int BIOME_BUTTON = 12;
    private static final int BIOME_WIDTH = 100;

    private final Merchant merchant;
    private final TradeSearch search;
    private final Screen parent;
    private final List<TradeGroup> groups;
    private final ScrollBar scrollBar = new ScrollBar();
    private final Motion motion = new Motion();
    private float scrollPixels;
    private int clipTop;
    private int clipBottom;
    private List<Row> rows = List.of();
    private GuiStyle style;
    private Layout layout;
    private @Nullable LivingEntity preview;
    private @Nullable VillagerBiome biome;
    private int scrollOffset;
    private boolean scrolledToMatch;

    public VillagerInfoScreen(Merchant merchant, TradeSearch search, Screen parent) {
        super(Component.literal(merchant.displayName()));
        this.merchant = merchant;
        this.search = search;
        this.parent = parent;
        this.groups = VillagerTrades.forMerchant(merchant);
    }

    @Override
    protected void init() {
        this.style = GuiStyles.current().style();
        this.layout = this.style.layout().fit(this.height);
        this.preview = VillagerPreview.create(this.merchant);
        if (this.preview != null) {
            VillagerPreview.setBiome(this.preview, this.biome);
        }
        this.rebuildRows();

        if (!this.scrolledToMatch) {
            this.scrolledToMatch = true;
            for (int i = 0; i < this.rows.size(); i++) {
                if (this.search.matches(this.rows.get(i).trade())) {
                    this.setScroll(i);
                    break;
                }
            }
        }
    }

    private void rebuildRows() {
        List<Row> result = new ArrayList<>();
        for (TradeGroup group : this.groups) {
            for (Trade trade : group.trades()) {
                if (trade.availableIn(this.biome)) {
                    result.add(new Row(group, trade));
                }
            }
        }
        this.rows = List.copyOf(result);
        this.setScroll(this.scrollOffset);
    }

    private void setScroll(int offset) {
        this.scrollOffset = Math.clamp(offset, 0, Math.max(0, this.rows.size() - this.layout.tradesVisible()));
    }

    private void cycleBiome(int direction) {
        VillagerBiome[] biomes = VillagerBiome.values();
        int index = this.biome == null ? -1 : this.biome.ordinal();
        index = Math.floorMod(index + 1 + direction, biomes.length + 1) - 1;
        this.biome = index < 0 ? null : biomes[index];
        if (this.preview != null) {
            VillagerPreview.setBiome(this.preview, this.biome);
        }
        this.rebuildRows();
    }

    private int windowX() {
        return (this.width - this.layout.width()) / 2;
    }

    private int windowY() {
        return (this.height - this.layout.height()) / 2;
    }

    private int biomeX() {
        return this.windowX() + this.layout.width() - 70 - BIOME_WIDTH / 2;
    }

    @Override
    public void onClose() {
        this.minecraft.gui.setScreen(this.parent);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        double amount = scrollY != 0.0 ? scrollY : scrollX;
        if (amount != 0.0) {
            this.setScroll(this.scrollOffset - (int) Math.signum(amount));
        }
        return true;
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        if (this.scrollBar.press(event.x(), event.y())) {
            this.setScroll(this.scrollBar.drag(event.y()));
            return true;
        }
        if (this.merchant.isVillager()) {
            int y = this.windowY() + this.layout.biomeY();
            if (inside(event.x(), event.y(), this.biomeX(), y, BIOME_BUTTON, BIOME_BUTTON)) {
                Motion.click();
                this.cycleBiome(-1);
                return true;
            }
            if (inside(event.x(), event.y(), this.biomeX() + BIOME_WIDTH - BIOME_BUTTON, y, BIOME_BUTTON, BIOME_BUTTON)) {
                Motion.click();
                this.cycleBiome(1);
                return true;
            }
        }
        return super.mouseClicked(event, doubleClick);
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
        this.motion.frame();
        int windowX = this.windowX();
        int windowY = this.windowY();
        int right = windowX + this.layout.width();

        this.style.panel(graphics, windowX, windowY, this.layout.width(), this.layout.height());
        this.style.name(graphics, this.font, this.merchant.displayName(), windowX + this.layout.nameX(), windowY + this.layout.nameY());

        if (this.preview != null) {
            int centerX = right - 70;
            InventoryScreen.extractEntityInInventoryFollowsMouse(graphics,
                    centerX - this.layout.previewHalfWidth(), windowY + this.layout.previewTop(),
                    centerX + this.layout.previewHalfWidth(), windowY + this.layout.previewBottom(),
                    PREVIEW_SIZE, this.layout.previewOffset(), mouseX, mouseY, this.preview);
        }

        this.extractTrades(graphics, windowX + this.layout.tradesX(), windowY + this.layout.tradesY(), mouseX, mouseY);
        if (this.merchant.isVillager()) {
            this.extractBiomeSelector(graphics, this.biomeX(), windowY + this.layout.biomeY(), mouseX, mouseY);
        }

        JobSiteRecipe jobSite = this.merchant.jobSite();
        if (jobSite != null) {
            this.extractJobSite(graphics, jobSite, right - 120, windowY + this.layout.jobSiteY(), mouseX, mouseY);
        }

        super.extractRenderState(graphics, mouseX, mouseY, a);
    }

    private void extractTrades(GuiGraphicsExtractor graphics, int tableX, int tableY, int mouseX, int mouseY) {
        this.style.sectionLabel(graphics, this.font, "Trades", tableX - 5, tableY - 14);

        int visible = this.layout.tradesVisible();
        this.scrollPixels = this.motion.approach(this.scrollPixels, this.scrollOffset * ROW_HEIGHT, 50.0F);
        int first = (int) (this.scrollPixels / ROW_HEIGHT);
        int end = Math.min(this.rows.size(), first + visible + 1);
        this.clipTop = tableY - 3;
        this.clipBottom = tableY + visible * ROW_HEIGHT - 3;
        graphics.enableScissor(tableX - 6, this.clipTop, tableX + TABLE_WIDTH, this.clipBottom);

        for (int index = first; index < end; index++) {
            Row row = this.rows.get(index);
            Trade trade = row.trade();
            int y = tableY + Math.round(index * ROW_HEIGHT - this.scrollPixels);

            int rowX = tableX + this.layout.levelWidth() - 3;
            int rowWidth = TABLE_WIDTH - this.layout.levelWidth() - 2;
            if (this.visibleAt(mouseY) && inside(mouseX, mouseY, rowX, y - 2, rowWidth, ROW_HEIGHT - 2)) {
                this.style.rowHover(graphics, rowX, y - 2, rowWidth, ROW_HEIGHT - 2);
            }
            if (this.search.matches(trade)) {
                this.style.rowHighlight(graphics, tableX + this.layout.levelWidth() - 3, y - 2, TABLE_WIDTH - this.layout.levelWidth() - 2, ROW_HEIGHT - 2);
            }

            if (index > 0 && this.rows.get(index - 1).group() != row.group()) {
                this.style.levelDivider(graphics, tableX - 5, y - 3, TABLE_WIDTH);
            }

            int slotX = tableX + this.layout.levelWidth();
            for (TradeStack want : trade.wants()) {
                this.extractTradeSlot(graphics, trade, want, true, slotX, y, mouseX, mouseY);
                slotX += SLOT_SIZE + SLOT_SPACING;
            }

            int arrowX = slotX + 2;
            this.style.arrow(graphics, this.font, arrowX, y);
            int givenX = arrowX + 15;
            this.extractTradeSlot(graphics, trade, trade.gives(), false, givenX, y, mouseX, mouseY);
            this.extractBiomeIcons(graphics, trade, givenX + SLOT_SIZE + 4, y + 5, mouseX, mouseY);
        }

        this.extractGroupLabels(graphics, tableX, tableY, mouseX, mouseY);
        graphics.disableScissor();

        if (this.rows.isEmpty()) {
            this.style.bodyText(graphics, this.font, "No trades for this biome", tableX, tableY + 4);
        }

        this.scrollBar.update(tableX + 230, tableY, visible * ROW_HEIGHT, visible, this.rows.size(), this.scrollPixels / ROW_HEIGHT);
        this.scrollBar.extract(this.style, graphics);
    }

    // labels stick to the top of the list until the last row of their group scrolls past
    private void extractGroupLabels(GuiGraphicsExtractor graphics, int tableX, int tableY, int mouseX, int mouseY) {
        int start = 0;
        while (start < this.rows.size()) {
            TradeGroup group = this.rows.get(start).group();
            int end = start;
            while (end + 1 < this.rows.size() && this.rows.get(end + 1).group() == group) {
                end++;
            }

            int firstY = tableY + Math.round(start * ROW_HEIGHT - this.scrollPixels);
            int lastY = tableY + Math.round(end * ROW_HEIGHT - this.scrollPixels);
            int y = Math.min(Math.max(firstY, tableY), lastY);
            if (y + ROW_HEIGHT > this.clipTop && y < this.clipBottom) {
                this.style.levelLabel(graphics, this.font, group.displayName(), group.color(), tableX - 5, y);
                if (this.visibleAt(mouseY) && inside(mouseX, mouseY, tableX - 5, y, this.layout.levelWidth() - 4, ROW_HEIGHT)) {
                    graphics.setTooltipForNextFrame(this.font, Component.literal(this.offerText(group)), mouseX, mouseY);
                }
            }
            start = end + 1;
        }
    }

    private String offerText(TradeGroup group) {
        int count = (int) group.trades().stream().filter(trade -> trade.availableIn(this.biome)).count();
        return count > group.amount()
                ? "Offers " + group.amount() + " of these " + count + " trades"
                : "Offers all of these trades";
    }

    private void extractTradeSlot(GuiGraphicsExtractor graphics, Trade trade, TradeStack stack, boolean wanted, int x, int y, int mouseX, int mouseY) {
        ItemStack current = stack.current();
        this.style.slot(graphics, x, y, SLOT_SIZE);
        this.style.item(graphics, this.font, current, x, y);
        if (!this.visibleAt(mouseY) || !inside(mouseX, mouseY, x, y, SLOT_SIZE, SLOT_SIZE) || this.scrollBar.dragging()) {
            return;
        }

        List<Component> lines = new ArrayList<>(getTooltipFromItem(this.minecraft, current));
        if (stack.hasVariants()) {
            lines.add(Component.literal("One of " + stack.variants().size() + " variants").withStyle(ChatFormatting.DARK_GRAY));
        }
        if (wanted && trade.price() != null && current.getItem() == trade.wants().getFirst().current().getItem()) {
            lines.add(Component.literal("Price: " + trade.price().min() + "-" + trade.price().max() + " emeralds").withStyle(ChatFormatting.GREEN));
            lines.add(Component.literal("Depends on the rolled enchantment").withStyle(ChatFormatting.DARK_GREEN));
        }
        if (!wanted && trade.modifier() != null) {
            lines.add(Component.literal(trade.modifier().description()).withStyle(ChatFormatting.LIGHT_PURPLE));
        }
        lines.add(Component.literal("Max uses: " + trade.maxUses() + "  Villager XP: +" + trade.xp()).withStyle(ChatFormatting.GRAY));
        if (!trade.biomes().isEmpty()) {
            lines.add(Component.literal("Only " + biomeNames(trade) + " villagers").withStyle(ChatFormatting.GOLD));
        }
        graphics.setComponentTooltipForNextFrame(this.font, lines, mouseX, mouseY);
    }

    private void extractBiomeIcons(GuiGraphicsExtractor graphics, Trade trade, int x, int y, int mouseX, int mouseY) {
        if (trade.biomes().isEmpty()) {
            return;
        }
        for (int i = 0; i < trade.biomes().size(); i++) {
            graphics.pose().pushMatrix();
            graphics.pose().translate(x + i * 9, y);
            graphics.pose().scale(0.5F, 0.5F);
            graphics.item(trade.biomes().get(i).icon(), 0, 0);
            graphics.pose().popMatrix();
        }
        if (this.visibleAt(mouseY) && inside(mouseX, mouseY, x, y, trade.biomes().size() * 9, 9)) {
            graphics.setTooltipForNextFrame(this.font, Component.literal("Only " + biomeNames(trade) + " villagers"), mouseX, mouseY);
        }
    }

    private static String biomeNames(Trade trade) {
        return trade.biomes().stream().map(VillagerBiome::displayName).collect(Collectors.joining(", "));
    }

    private void extractBiomeSelector(GuiGraphicsExtractor graphics, int x, int y, int mouseX, int mouseY) {
        boolean leftHovered = inside(mouseX, mouseY, x, y, BIOME_BUTTON, BIOME_BUTTON);
        boolean rightHovered = inside(mouseX, mouseY, x + BIOME_WIDTH - BIOME_BUTTON, y, BIOME_BUTTON, BIOME_BUTTON);
        this.style.button(graphics, x, y, BIOME_BUTTON, BIOME_BUTTON, leftHovered);
        this.style.button(graphics, x + BIOME_WIDTH - BIOME_BUTTON, y, BIOME_BUTTON, BIOME_BUTTON, rightHovered);
        this.style.buttonLabel(graphics, this.font, "<", x + BIOME_BUTTON / 2 + 1, y + 2, leftHovered);
        this.style.buttonLabel(graphics, this.font, ">", x + BIOME_WIDTH - BIOME_BUTTON / 2 + 1, y + 2, rightHovered);

        String label = this.biome == null ? "All biomes" : this.biome.displayName();
        int labelWidth = this.font.width(label) + (this.biome == null ? 0 : 10);
        int labelX = x + (BIOME_WIDTH - labelWidth) / 2;
        if (this.biome != null) {
            graphics.pose().pushMatrix();
            graphics.pose().translate(labelX, y + 2);
            graphics.pose().scale(0.5F, 0.5F);
            graphics.item(this.biome.icon(), 0, 0);
            graphics.pose().popMatrix();
            labelX += 10;
        }
        this.style.bodyText(graphics, this.font, label, labelX, y + 2);
    }

    private void extractJobSite(GuiGraphicsExtractor graphics, JobSiteRecipe recipe, int gridX, int gridY, int mouseX, int mouseY) {
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
            this.itemTooltip(graphics, ingredient.stack(), slotX, slotY, mouseX, mouseY);
        }

        this.style.arrow(graphics, this.font, gridX + SLOT_SIZE * 4 - 10, gridY + SLOT_SIZE);

        int resultX = gridX + SLOT_SIZE * 4 + 4;
        int resultY = gridY + SLOT_SIZE;
        if (this.style.framedResult()) {
            this.style.slot(graphics, resultX, resultY, SLOT_SIZE);
        }
        this.style.item(graphics, this.font, recipe.result(), resultX, resultY);
        this.itemTooltip(graphics, recipe.result(), resultX, resultY, mouseX, mouseY);
    }

    private void itemTooltip(GuiGraphicsExtractor graphics, ItemStack stack, int x, int y, int mouseX, int mouseY) {
        if (inside(mouseX, mouseY, x, y, SLOT_SIZE, SLOT_SIZE)) {
            graphics.setTooltipForNextFrame(this.font, stack, mouseX, mouseY);
        }
    }

    private boolean visibleAt(int mouseY) {
        return mouseY >= this.clipTop && mouseY < this.clipBottom;
    }

    private static boolean inside(double mouseX, double mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }

    private record Row(TradeGroup group, Trade trade) {
    }
}
