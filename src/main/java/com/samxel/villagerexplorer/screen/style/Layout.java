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
        int tradesVisible,
        int levelWidth,
        int previewHalfWidth,
        int previewTop,
        int previewBottom,
        float previewOffset,
        int jobSiteY
) {
    public static final Layout CLASSIC = new Layout(420, 340, 55, 85, 140, 30, 70, 50, 110, 7, 60, 25, -40, 240, 1.0F, 226);
    public static final Layout COMPACT = new Layout(420, 300, 14, 32, 64, 17, 14, 22, 46, 10, 68, 45, 28, 152, 0.0625F, 216);
}
