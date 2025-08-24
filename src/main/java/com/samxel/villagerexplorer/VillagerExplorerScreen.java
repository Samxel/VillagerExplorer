package com.samxel.villagerexplorer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.village.VillagerProfession;
import org.joml.Quaternionf;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VillagerExplorerScreen extends Screen {
    private TextFieldWidget searchField;
    private final List<String> allVillagers = List.of(
            "Farmer", "Librarian", "Cleric", "Armorer", "Butcher", "Cartographer", "Fisherman", "Fletcher", "Leatherworker", "Mason", "Shepherd", "Toolsmith", "Weaponsmith"
    );
    private List<String> filteredVillagers = new ArrayList<>();


    private final int windowWidth = 420;
    private final int windowHeight = 340;


    private final int tileSize = 90;
    private final int tileSpacing = 18;
    private final int tilesPerRow = 3;
    private final int tileAreaTop = 140;
    private final int tileAreaBottom = windowHeight - 20;


    private int scrollOffset = 0;
    private int maxScroll = 0;

    public VillagerExplorerScreen() {
        super(Text.of("Villager Explorer"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int windowX = centerX - windowWidth / 2;
        int windowY = this.height / 2 - windowHeight / 2;


        this.searchField = new TextFieldWidget(
                this.textRenderer,
                windowX + 20,
                windowY + 85,
                windowWidth - 40,
                20,
                Text.of("Search Villager")
        );
        this.searchField.setChangedListener(this::updateFilter);
        this.addDrawableChild(this.searchField);
        this.setInitialFocus(this.searchField);


        this.updateFilter("");
    }

    private void updateFilter(String filter) {
        this.filteredVillagers = this.allVillagers.stream()
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
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if (maxScroll > 0 && amount != 0.0D) {
            scrollOffset -= (int) Math.signum(amount);
            if (scrollOffset < 0) scrollOffset = 0;
            else if (scrollOffset > maxScroll) scrollOffset = maxScroll;
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, amount);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.searchField.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }

        if (keyCode == 264) {
            if (scrollOffset < maxScroll) {
                scrollOffset++;
                return true;
            }
        }
        if (keyCode == 265) {
            if (scrollOffset > 0) {
                scrollOffset--;
                return true;
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        if (this.searchField.charTyped(chr, modifiers)) {
            return true;
        }
        return super.charTyped(chr, modifiers);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.searchField.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }
        for (VillagerTile tile : visibleTiles) {
            if (tile.contains(mouseX, mouseY)) {
                this.close();
                MinecraftClient.getInstance().setScreen(new VillagerInfoScreen(tile.name));
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
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


        context.drawBorder(windowX, windowY, windowWidth, windowHeight, 0xFF000000);


        int textX = windowX + windowWidth / 2 - this.textRenderer.getWidth(this.title) / 2;
        context.drawText(
                this.textRenderer,
                this.title,
                textX,
                windowY + 48,
                0xFFFFFF,
                false
        );


        context.drawText(
                this.textRenderer,
                Text.of("Search:"),
                windowX + 20,
                windowY + 75,
                0xAAAAAA,
                false
        );


        int startY = windowY + tileAreaTop;
        int availableWidth = windowWidth - 2 * 20;
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
                context.drawBorder(x, y, tileSize, tileSize, 0xFFFFFFFF);

                String villagerName = filteredVillagers.get(idx);


                renderVillagerInBox(x + tileSize / 2, y + 65, 28, villagerName);

                int textWidth = this.textRenderer.getWidth(villagerName);
                textX = x + (tileSize - textWidth) / 2;

                context.drawText(
                        this.textRenderer,
                        Text.of(villagerName),
                        textX,
                        y + tileSize - 18,
                        0xFFFFFFFF,
                        false
                );
                visibleTiles.add(new VillagerTile(x, y, tileSize, tileSize, idx, villagerName));

            }
        }


        if (maxScroll > 0) {
            int barHeight = Math.max(20, (int) ((float) visibleRows / rows * (tileAreaBottom - tileAreaTop)));
            int barY = startY + (int) ((float) scrollOffset / maxScroll * ((tileAreaBottom - tileAreaTop) - barHeight));
            int barX = windowX + windowWidth - 8;
            context.fill(barX, startY, barX + 6, tileAreaBottom, 0x22000000);
            context.fill(barX, barY, barX + 6, barY + barHeight, 0xFF888888);
        }
    }

    private void renderVillagerInBox(int x, int y, int scale, String villagerName) {
        MinecraftClient client = MinecraftClient.getInstance();
        VillagerEntity villager = EntityType.VILLAGER.create(client.world);
        if (villager == null) return;

        villager.setVillagerData(
                villager.getVillagerData().withProfession(VillagerUtils.getProfessionByName(villagerName))
        );


        float yaw = 180f;
        float pitch = -4f;

        renderEntityInGui(x, y, scale, yaw, pitch, villager);
    }

    public static void renderEntityInGui(
            int x, int y, int scale, float yaw, float pitch, VillagerEntity entity
    ) {
        MinecraftClient client = MinecraftClient.getInstance();
        EntityRenderDispatcher dispatcher = client.getEntityRenderDispatcher();
        MatrixStack matrices = new MatrixStack();


        matrices.translate(x, y, 1050.0);
        matrices.scale(1.0F, 1.0F, -1.0F);

        matrices.push();
        matrices.scale((float) scale, (float) scale, (float) scale);


        Quaternionf rotationZ = new Quaternionf().rotateZ((float) Math.PI);
        matrices.multiply(rotationZ);
        matrices.multiply(new Quaternionf().rotateX((float) Math.toRadians(pitch)));
        matrices.multiply(new Quaternionf().rotateY((float) Math.toRadians(yaw)));

        dispatcher.setRenderShadows(false);

        VertexConsumerProvider.Immediate vertexConsumers = client.getBufferBuilders().getEntityVertexConsumers();

        @SuppressWarnings("unchecked")
        EntityRenderer<? super VillagerEntity> renderer =
                (EntityRenderer<? super VillagerEntity>) dispatcher.getRenderer(entity);

        dispatcher.render(
                entity,
                0., 0., 0.,
                0.0f,
                client.getTickDelta(),
                matrices,
                vertexConsumers,
                15728880
        );

        vertexConsumers.draw();
        dispatcher.setRenderShadows(true);
        matrices.pop();
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

    private final List<VillagerTile> visibleTiles = new ArrayList<>();

}
