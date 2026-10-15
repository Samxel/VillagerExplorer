package com.samxel.villagerexplorer.hud;

import com.samxel.villagerexplorer.Tr;
import com.samxel.villagerexplorer.config.ExplorerConfig;
import com.samxel.villagerexplorer.pin.Pins;
import com.samxel.villagerexplorer.screen.style.GuiStyle;
import com.samxel.villagerexplorer.screen.style.GuiStyles;
import com.samxel.villagerexplorer.villager.Trade;
import com.samxel.villagerexplorer.villager.TradeGroup;
import com.samxel.villagerexplorer.villager.TradeStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.ChatScreen;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class PinnedTradesHud {
    public static final float DEFAULT_X = 0.01F;
    public static final float DEFAULT_Y = 0.3F;

    private static final int PADDING = 7;
    private static final int TITLE_HEIGHT = 13;
    private static final int ROW_HEIGHT = 20;
    private static final int SLOT_SIZE = 18;
    private static final int SLOT_SPACING = 2;
    private static final int CLOSE_SIZE = 9;

    private static float x = DEFAULT_X;
    private static float y = DEFAULT_Y;
    private static @Nullable Bounds bounds;
    private static boolean dragging;
    private static double grabX;
    private static double grabY;

    private PinnedTradesHud() {
    }

    public static float x() {
        return x;
    }

    public static float y() {
        return y;
    }

    public static void setPosition(float newX, float newY) {
        x = Math.clamp(newX, 0.0F, 1.0F);
        y = Math.clamp(newY, 0.0F, 1.0F);
    }

    public static void extract(GuiGraphicsExtractor graphics) {
        bounds = null;
        Minecraft minecraft = Minecraft.getInstance();
        boolean chatOpen = minecraft.gui.screen() instanceof ChatScreen;
        if (minecraft.level == null || minecraft.gui.hud.isHidden() || minecraft.gui.screen() != null && !chatOpen) {
            return;
        }

        List<Entry> entries = entries();
        if (entries.isEmpty()) {
            return;
        }

        GuiStyle style = GuiStyles.current().style();
        Font font = minecraft.font;
        int maxWants = entries.stream().mapToInt(entry -> entry.trade().wants().size()).max().orElse(1);
        int maxLabel = entries.stream().mapToInt(entry -> font.width(entry.label())).max().orElse(0);
        String title = chatOpen ? Tr.get("pin.drag") : Tr.get("pin.title");
        int contentWidth = maxWants * (SLOT_SIZE + SLOT_SPACING) + 14 + SLOT_SIZE + 6 + maxLabel + (chatOpen ? CLOSE_SIZE + 4 : 0);
        int width = Math.max(contentWidth, font.width(title)) + PADDING * 2;
        int height = PADDING * 2 + TITLE_HEIGHT + entries.size() * ROW_HEIGHT;

        int screenWidth = graphics.guiWidth();
        int screenHeight = graphics.guiHeight();
        int left = Math.clamp(Math.round(x * screenWidth), 0, Math.max(0, screenWidth - width));
        int top = Math.clamp(Math.round(y * screenHeight), 0, Math.max(0, screenHeight - height));
        bounds = new Bounds(left, top, width, height, new ArrayList<>());

        double mouseX = minecraft.mouseHandler.getScaledXPos(minecraft.getWindow());
        double mouseY = minecraft.mouseHandler.getScaledYPos(minecraft.getWindow());

        style.panel(graphics, left, top, width, height);
        style.sectionLabel(graphics, font, title, left + PADDING, top + PADDING);

        int rowY = top + PADDING + TITLE_HEIGHT;
        for (Entry entry : entries) {
            boolean hovered = chatOpen && mouseX >= left && mouseX < left + width && mouseY >= rowY && mouseY < rowY + ROW_HEIGHT;
            if (hovered) {
                style.rowHover(graphics, left + PADDING - 2, rowY, width - PADDING * 2 + 4, ROW_HEIGHT);
            }

            int slotX = left + PADDING;
            for (TradeStack want : entry.trade().wants()) {
                extractSlot(graphics, style, font, want, slotX, rowY + 1);
                slotX += SLOT_SIZE + SLOT_SPACING;
            }
            int arrowX = left + PADDING + maxWants * (SLOT_SIZE + SLOT_SPACING);
            style.arrow(graphics, font, arrowX, rowY + 1);
            extractSlot(graphics, style, font, entry.trade().gives(), arrowX + 14, rowY + 1);
            style.bodyText(graphics, font, entry.label(), arrowX + 14 + SLOT_SIZE + 6, rowY + 6);

            if (chatOpen) {
                int closeX = left + width - PADDING - CLOSE_SIZE;
                int closeY = rowY + (ROW_HEIGHT - CLOSE_SIZE) / 2;
                boolean closeHovered = mouseX >= closeX && mouseX < closeX + CLOSE_SIZE && mouseY >= closeY && mouseY < closeY + CLOSE_SIZE;
                style.buttonLabel(graphics, font, "x", closeX + CLOSE_SIZE / 2 + 1, closeY + 1, closeHovered);
                bounds.closeButtons().add(new CloseButton(closeX, closeY, entry.pin()));
            }
            rowY += ROW_HEIGHT;
        }
    }

    private static void extractSlot(GuiGraphicsExtractor graphics, GuiStyle style, Font font, TradeStack stack, int slotX, int slotY) {
        style.slot(graphics, slotX, slotY, SLOT_SIZE);
        style.item(graphics, font, stack.current(), slotX, slotY);
    }

    private static List<Entry> entries() {
        List<Entry> entries = new ArrayList<>();
        for (Pins.Pin pin : Pins.all()) {
            Trade trade = pin.trade();
            TradeGroup group = pin.tradeGroup();
            if (trade != null && group != null) {
                entries.add(new Entry(pin, trade, pin.merchant().displayName() + " - " + group.displayName()));
            }
        }
        return entries;
    }

    public static boolean mouseClicked(double mouseX, double mouseY) {
        Bounds current = bounds;
        if (current == null || !current.contains(mouseX, mouseY)) {
            return false;
        }
        for (CloseButton button : current.closeButtons()) {
            if (mouseX >= button.x() && mouseX < button.x() + CLOSE_SIZE && mouseY >= button.y() && mouseY < button.y() + CLOSE_SIZE) {
                Pins.remove(button.pin());
                return true;
            }
        }
        dragging = true;
        grabX = mouseX - current.left();
        grabY = mouseY - current.top();
        return true;
    }

    public static boolean mouseDragged(double mouseX, double mouseY) {
        Bounds current = bounds;
        if (!dragging || current == null) {
            return false;
        }
        Minecraft minecraft = Minecraft.getInstance();
        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();
        double left = Math.clamp(mouseX - grabX, 0, Math.max(0, screenWidth - current.width()));
        double top = Math.clamp(mouseY - grabY, 0, Math.max(0, screenHeight - current.height()));
        setPosition((float) (left / screenWidth), (float) (top / screenHeight));
        return true;
    }

    public static boolean mouseReleased() {
        if (!dragging) {
            return false;
        }
        dragging = false;
        ExplorerConfig.save();
        return true;
    }

    private record Entry(Pins.Pin pin, Trade trade, String label) {
    }

    private record CloseButton(int x, int y, Pins.Pin pin) {
    }

    private record Bounds(int left, int top, int width, int height, List<CloseButton> closeButtons) {
        boolean contains(double mouseX, double mouseY) {
            return mouseX >= this.left && mouseX < this.left + this.width && mouseY >= this.top && mouseY < this.top + this.height;
        }
    }
}
