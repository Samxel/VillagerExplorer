package com.samxel.villagerexplorer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.render.entity.EntityRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.village.VillagerProfession;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VillagerExplorerScreen extends Screen {
    private final List<String> allVillagers =
            List.of(
                    "Farmer",
                    "Librarian",
                    "Cleric",
                    "Armorer",
                    "Butcher",
                    "Cartographer",
                    "Fisherman",
                    "Fletcher",
                    "Leatherworker",
                    "Mason",
                    "Shepherd",
                    "Toolsmith",
                    "Weaponsmith");
    private final int windowWidth = 420;
    private final int windowHeight = 340;
    private final int tileSize = 90;
    private final int tileSpacing = 18;
    private final int tilesPerRow = 3;
    private final int tileAreaTop = 140;
    private final int tileAreaBottom = windowHeight - 20;
    private final List<VillagerTile> visibleTiles = new ArrayList<>();
    private TextFieldWidget searchField;
    private List<String> filteredVillagers = new ArrayList<>();
    private int scrollOffset = 0;
    private int maxScroll = 0;

    public VillagerExplorerScreen() {
        super(Text.of("Villager Explorer"));
    }

    public static void renderVillagerInBox(
            DrawContext drawer,
            int x1, int y1, int x2, int y2,
            float scale,
            Vector3f translation,
            float yaw, float pitch,
            @Nullable Quaternionf overrideCameraAngle,
            LivingEntity entity
    ) {
        EntityRenderManager entityRenderManager = MinecraftClient.getInstance().getEntityRenderDispatcher();
        EntityRenderer<? super LivingEntity, ?> entityRenderer = entityRenderManager.getRenderer(entity);
        EntityRenderState entityRenderState = entityRenderer.getAndUpdateRenderState(entity, 1.0F);

        if (entityRenderState instanceof LivingEntityRenderState state) {
            state.bodyYaw = yaw;
            state.pitch = pitch;
            state.relativeHeadYaw = yaw;
        }


        entityRenderState.light = 15728880;
        entityRenderState.hitbox = null;
        entityRenderState.shadowPieces.clear();
        entityRenderState.outlineColor = 0;


        Quaternionf rotation = new Quaternionf()
                .rotateZ((float) Math.PI)
                .rotateY((float) Math.toRadians(180.0f - yaw))
                .rotateX((float) Math.toRadians(pitch));


        drawer.addEntity(entityRenderState, scale, translation, rotation, overrideCameraAngle, x1, y1, x2, y2);
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int windowX = centerX - windowWidth / 2;
        int windowY = this.height / 2 - windowHeight / 2;

        this.searchField =
                new TextFieldWidget(
                        this.textRenderer, windowX + 20, windowY + 85, windowWidth - 40, 20,
                        Text.of("Search Villager"));
        this.searchField.setChangedListener(this::updateFilter);
        this.addDrawableChild(this.searchField);
        this.setInitialFocus(this.searchField);

        this.updateFilter("");
    }

    private void updateFilter(String filter) {
        this.filteredVillagers =
                this.allVillagers.stream()
                        .filter(name -> name.toLowerCase().contains(filter.toLowerCase()))
                        .collect(Collectors.toList());
        updateScroll();
    }

    private void updateScroll() {
        int rows = (int) Math.ceil(filteredVillagers.size() / (float) tilesPerRow);
        int visibleRows = (tileAreaBottom - tileAreaTop) / (tileSize + tileSpacing);
        maxScroll = Math.max(0, rows - visibleRows);
        scrollOffset = Math.min(scrollOffset, maxScroll);
        scrollOffset = Math.max(scrollOffset, 0);
    }

    @Override
    public boolean mouseScrolled(
            double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        double amount = verticalAmount != 0.0 ? verticalAmount : horizontalAmount;
        if (maxScroll > 0 && amount != 0.0D) {
            scrollOffset -= (int) Math.signum(amount);
            if (scrollOffset < 0) scrollOffset = 0;
            else if (scrollOffset > maxScroll) scrollOffset = maxScroll;
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public boolean keyPressed(KeyInput input) {
        if (this.searchField.keyPressed(input)) {
            return true;
        }


        if (input.key() == 264) {
            if (scrollOffset < maxScroll) {
                scrollOffset++;
                return true;
            }
        }

        if (input.key() == 265) {
            if (scrollOffset > 0) {
                scrollOffset--;
                return true;
            }
        }
        return super.keyPressed(input);
    }

    @Override
    public boolean charTyped(CharInput input) {
        if (this.searchField.charTyped(input)) {
            return true;
        }
        return super.charTyped(input);
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        if (super.mouseClicked(click, doubled)) {
            return true;
        }

        double mouseX = click.x();
        double mouseY = click.y();
        for (VillagerTile tile : visibleTiles) {
            if (tile.contains(mouseX, mouseY)) {
                MinecraftClient.getInstance().setScreen(new VillagerInfoScreen(tile.name));
                return true;
            }
        }

        return false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int windowX = centerX - windowWidth / 2;
        int windowY = centerY - windowHeight / 2;
        visibleTiles.clear();


        context.fill(windowX, windowY, windowX + windowWidth, windowY + windowHeight, 0xCC222222);
        context.drawStrokedRectangle(windowX, windowY, windowWidth, windowHeight, 0xFF000000);


        String titleText = "Villager Explorer";
        int titleWidth = this.textRenderer.getWidth(titleText);
        int titleX = windowX + (windowWidth - titleWidth) / 2;
        int titleY = windowY + 55;
        context.drawText(this.textRenderer, Text.of(titleText), titleX, titleY, 0xFFFFFFFF, false);


        int startY = windowY + tileAreaTop;
        int totalTiles = filteredVillagers.size();
        int rows = (int) Math.ceil(totalTiles / (float) tilesPerRow);

        int visibleRows = (tileAreaBottom - tileAreaTop) / (tileSize + tileSpacing);

        int firstRow = scrollOffset;
        int lastRow = Math.min(rows, firstRow + visibleRows);

        for (int row = firstRow; row < lastRow; row++) {
            int tilesInThisRow = Math.min(tilesPerRow, totalTiles - row * tilesPerRow);
            int rowWidth = tilesInThisRow * tileSize + (tilesInThisRow - 1) * tileSpacing;
            int rowStartX = windowX + (windowWidth - rowWidth) / 2;

            for (int col = 0; col < tilesInThisRow; col++) {
                int idx = row * tilesPerRow + col;
                if (idx >= totalTiles) break;

                int x = rowStartX + col * (tileSize + tileSpacing);
                int y = startY + (row - firstRow) * (tileSize + tileSpacing);


                context.fill(x, y, x + tileSize, y + tileSize, 0x10FFFFFF);
                context.drawStrokedRectangle(x, y, tileSize, tileSize, 0xFFFFFFFF);

                String villagerName = filteredVillagers.get(idx);


                MinecraftClient client = MinecraftClient.getInstance();
                if (client.world == null) continue;

                VillagerEntity villager =
                        EntityType.VILLAGER.create(client.world, SpawnReason.TRIGGERED);
                if (villager == null) continue;


                VillagerProfession prof = VillagerUtils.getProfessionByName(villagerName);
                RegistryEntry<VillagerProfession> entry =
                        Registries.VILLAGER_PROFESSION.getEntry(prof);
                villager.setVillagerData(villager.getVillagerData().withProfession(entry));


                int boxHalf = 28;
                int x1 = x + tileSize / 2 - boxHalf;
                int y1 = y + 10;
                int x2 = x + tileSize / 2 + boxHalf;
                int y2 = y + 10 + boxHalf * 2;


                float yaw = 0f;
                float pitch = -3f;


                renderVillagerInBox(
                        context,
                        x1, y1, x2, y2,
                        30.0f,
                        new Vector3f(0, 1, 0),
                        yaw, pitch,
                        null,
                        villager
                );


                int nWidth = this.textRenderer.getWidth(villagerName);
                int NameTextX = x + (tileSize - nWidth) / 2;
                context.drawText(
                        this.textRenderer, Text.of(villagerName),
                        NameTextX, y + tileSize - 18, 0xFFFFFFFF, false);

                visibleTiles.add(new VillagerTile(x, y, tileSize, tileSize, idx, villagerName));
            }
        }


        if (maxScroll > 0) {
            int barArea = tileAreaBottom - tileAreaTop;
            int barHeight = Math.max(20, (int) ((float) visibleRows / rows * barArea));
            int barY =
                    windowY
                            + tileAreaTop
                            + (int) ((float) scrollOffset / maxScroll * (barArea - barHeight));
            int barX = windowX + windowWidth - 8;
            context.fill(barX, windowY + tileAreaTop, barX + 6, windowY + tileAreaBottom, 0x22000000);
            context.fill(barX, barY, barX + 6, barY + barHeight, 0xFF888888);
        }


        this.searchField.render(context, mouseX, mouseY, delta);
    }

    private static class VillagerTile {
        int x, y, width, height, index;
        String name;

        VillagerTile(int x, int y, int width, int height, int index, String name) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.index = index;
            this.name = name;
        }

        boolean contains(double mouseX, double mouseY) {
            return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
        }
    }
}