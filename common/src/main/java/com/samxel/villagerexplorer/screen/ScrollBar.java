package com.samxel.villagerexplorer.screen;

import com.samxel.villagerexplorer.screen.style.GuiStyle;
import net.minecraft.client.gui.GuiGraphicsExtractor;

final class ScrollBar {
    private static final int WIDTH = 6;
    private static final int MIN_THUMB = 12;

    private int x;
    private int y;
    private int height;
    private int thumbY;
    private int thumbHeight;
    private int max;
    private boolean dragging;
    private double grabOffset;

    void update(int x, int y, int height, int visible, int total, double offset) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.max = Math.max(0, total - visible);
        this.thumbHeight = total <= 0 ? height : Math.max(MIN_THUMB, Math.min(height, height * visible / total));
        this.thumbY = this.max == 0 ? y : y + (int) Math.round((height - this.thumbHeight) * Math.clamp(offset / this.max, 0.0, 1.0));
    }

    boolean visible() {
        return this.max > 0;
    }

    boolean dragging() {
        return this.dragging;
    }

    boolean press(double mouseX, double mouseY) {
        if (!this.visible() || mouseX < this.x - 2 || mouseX >= this.x + WIDTH + 2 || mouseY < this.y || mouseY >= this.y + this.height) {
            return false;
        }
        this.dragging = true;
        boolean onThumb = mouseY >= this.thumbY && mouseY < this.thumbY + this.thumbHeight;
        this.grabOffset = onThumb ? mouseY - this.thumbY : this.thumbHeight / 2.0;
        return true;
    }

    int drag(double mouseY) {
        int track = this.height - this.thumbHeight;
        if (track <= 0) {
            return 0;
        }
        double progress = (mouseY - this.grabOffset - this.y) / track;
        return (int) Math.round(Math.clamp(progress, 0.0, 1.0) * this.max);
    }

    void release() {
        this.dragging = false;
    }

    void extract(GuiStyle style, GuiGraphicsExtractor graphics) {
        if (this.visible()) {
            style.scrollbar(graphics, this.x, this.y, this.height, this.thumbY, this.thumbHeight);
        }
    }
}
