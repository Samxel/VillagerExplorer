package com.samxel.villagerexplorer.screen.style;

public record Layout(
        int width,
        int height,
        int titleY,
        int searchY,
        int tileTop,
        int nameX,
        int nameY,
        int tradesX,
        int tradesY,
        int maxTradesVisible,
        int levelWidth,
        int previewHalfWidth,
        int previewTop,
        int previewBottom,
        float previewOffset,
        int biomeY,
        int jobSiteY
) {
    public static final Layout CLASSIC = new Layout(420, 340, 55, 85, 140, 30, 70, 50, 110, 7, 60, 25, -40, 240, 1.0F, 198, 226);
    public static final Layout COMPACT = new Layout(420, 300, 14, 32, 64, 17, 14, 22, 46, 10, 68, 45, 28, 152, 0.0625F, 158, 216);

    private static final int SCREEN_MARGIN = 8;
    private static final int ROW_HEIGHT = 24;
    private static final int BOTTOM_PADDING = 14;

    public Layout fit(int screenHeight) {
        int available = screenHeight - SCREEN_MARGIN;
        if (this.height <= available) {
            return this;
        }
        // the classic layout wastes too much space at the top, so it falls back to the compact metrics
        Layout base = this == CLASSIC ? COMPACT : this;
        return base.height <= available ? base : base.shrinkTo(available);
    }

    private Layout shrinkTo(int available) {
        int shrink = this.height - available;
        int take = Math.min(shrink, this.titleY - 10);
        int jobSite = this.jobSiteY - shrink;
        int biome = Math.min(this.biomeY - take, jobSite - 30);
        int previewBottom = Math.min(this.previewBottom - take, biome - 4);
        return new Layout(this.width, available, this.titleY - take, this.searchY - take, this.tileTop - take,
                this.nameX, this.nameY - take, this.tradesX, this.tradesY - take, this.maxTradesVisible, this.levelWidth,
                this.previewHalfWidth, this.previewTop - take, previewBottom, this.previewOffset, biome, jobSite);
    }

    public int tradesVisible() {
        return Math.max(1, Math.min(this.maxTradesVisible, (this.height - BOTTOM_PADDING - this.tradesY) / ROW_HEIGHT));
    }
}
