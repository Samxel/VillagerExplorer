package com.samxel.villagerexplorer;

import net.minecraft.world.entity.EntityTypes;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.villager.VillagerProfession;

public class VillagerExplorerScreen extends Screen {
    private final List<String> allVillagers =
            List.of(
                    "Armorer",
                    "Butcher",
                    "Cartographer",
                    "Cleric",
                    "Farmer",
                    "Fisherman",
                    "Fletcher",
                    "Leatherworker",
                    "Librarian",
                    "Mason",
                    "Shepherd",
                    "Toolsmith",
                    "Weaponsmith");
    private final int windowWidth = 420;
    private final int windowHeight = 320;
    private final int tileSize = 65;
    private final int tileSpacing = 5;
    private final int tilesPerRow = 5;
    private final int tileAreaTop = 80;
    private final int tileAreaBottom = windowHeight - 20;
    private final List<VillagerTile> visibleTiles = new ArrayList<>();
    private EditBox searchField;
    private List<String> filteredVillagers = new ArrayList<>();
    private int scrollOffset = 0;
    private int maxScroll = 0;

    public VillagerExplorerScreen() {
        super(Component.nullToEmpty("Villager kutss Explorer"));
    }

    public static void renderVillagerInBox(
            GuiGraphicsExtractor drawer,
            int x1, int y1, int x2, int y2,
            float scale,
            Vector3f translation,
            float yaw, float pitch,
            @Nullable Quaternionf overrideCameraAngle,
            LivingEntity entity
    ) {
        EntityRenderDispatcher entityRenderManager = Minecraft.getInstance().getEntityRenderDispatcher();
        EntityRenderer<? super LivingEntity, ?> entityRenderer = entityRenderManager.getRenderer(entity);
        EntityRenderState entityRenderState = entityRenderer.createRenderState(entity, 1.0F);

        if (entityRenderState instanceof LivingEntityRenderState state) {
            state.bodyRot = yaw;
            state.xRot = pitch;
            state.yRot = yaw;
        }


        entityRenderState.lightCoords = 15728880;
        entityRenderState.shadowPieces.clear();
        entityRenderState.outlineColor = 0;


        Quaternionf rotation = new Quaternionf()
                .rotateZ((float) Math.PI)
                .rotateY((float) Math.toRadians(180.0f - yaw))
                .rotateX((float) Math.toRadians(pitch));


        drawer.entity(entityRenderState, scale, translation, rotation, overrideCameraAngle, x1, y1, x2, y2);
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int windowX = centerX - windowWidth / 2;
        int windowY = this.height / 2 - windowHeight / 2;

        this.searchField =
                new EditBox(
                        this.font, windowX + 30, windowY + 45, windowWidth - 60, 20,
                        Component.nullToEmpty("Search Villager"));
        this.searchField.setResponder(this::updateFilter);
        this.addRenderableWidget(this.searchField);
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
    public boolean keyPressed(KeyEvent input) {
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
    public boolean charTyped(CharacterEvent input) {
        if (this.searchField.charTyped(input)) {
            return true;
        }
        return super.charTyped(input);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent click, boolean doubled) {
        if (super.mouseClicked(click, doubled)) {
            return true;
        }

        double mouseX = click.x();
        double mouseY = click.y();
        for (VillagerTile tile : visibleTiles) {
            if (tile.contains(mouseX, mouseY)) {
                Minecraft.getInstance().gui.setScreen(new VillagerInfoScreen(tile.name));
                return true;
            }
        }

        return false;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        super.extractRenderState(context, mouseX, mouseY, delta);

        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int windowX = centerX - windowWidth / 2;
        int windowY = centerY - windowHeight / 2;
        visibleTiles.clear();


        context.fill(windowX, windowY, windowX + windowWidth, windowY + windowHeight, 0xCC222222);
        context.outline(windowX, windowY, windowWidth, windowHeight, 0xFF000000);;


        String titleText = "Villager Explorer";
        int titleWidth = this.font.width(titleText);
        int titleX = windowX + (windowWidth - titleWidth) / 2;
        int titleY = windowY + 25;
        context.text(this.font, Component.nullToEmpty(titleText), titleX, titleY, 0xFFFFFFFF, false);


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
                context.outline(x, y, tileSize, tileSize, 0xFFFFFFFF);

                String villagerName = filteredVillagers.get(idx);


                Minecraft client = Minecraft.getInstance();
                if (client.level == null) continue;

                Villager villager =
                        EntityTypes.VILLAGER.create(client.level, EntitySpawnReason.TRIGGERED);

                if (villager != null) {
                    villager.setId(client.level.getEntityCount());
                }


                VillagerProfession prof = VillagerUtils.getProfessionByName(villagerName);
                Holder<VillagerProfession> entry =
                        BuiltInRegistries.VILLAGER_PROFESSION.wrapAsHolder(prof);
                villager.setVillagerData(villager.getVillagerData().withProfession(entry));


                int boxHalf = 28;
                int x1 = x + tileSize / 2 - boxHalf;
                int y1 = y - 10;
                int x2 = x + tileSize / 2 + boxHalf;
                int y2 = y + 10 + boxHalf * 2;


                float yaw = 0f;
                float pitch = -3f;


                renderVillagerInBox(
                        context,
                        x1, y1, x2, y2,
                        28.0f,
                        new Vector3f(0, 1.2f, 0),
                        yaw, pitch,
                        null,
                        villager
                );


                float textScale = 0.8f;

                int nWidth = (int)(this.font.width(villagerName) * textScale);
                int NameTextX = x + (tileSize - nWidth) / 2;

                context.pose().pushMatrix();
                context.pose().scale(textScale, textScale);

                context.text(
                        this.font,
                        Component.nullToEmpty(villagerName),
                        (int)(NameTextX / textScale),
                        (int)((y + tileSize - 18) / textScale),
                        0xFFFFFFFF,
                        false
                );

                context.pose().popMatrix();
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


        this.searchField.extractRenderState(context, mouseX, mouseY, delta);
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