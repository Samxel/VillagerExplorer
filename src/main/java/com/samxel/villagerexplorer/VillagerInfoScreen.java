package com.samxel.villagerexplorer;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class VillagerInfoScreen extends Screen {
    private static final int TRADES_VISIBLE = 13;
    private final int windowWidth = 420;
    private final int windowHeight = 320;
    private final String villagerName;
    private int tradeScrollOffset = 0;

    public VillagerInfoScreen(String villagerName) {
        super(Component.nullToEmpty("Villager Info"));
        this.villagerName = villagerName;
    }

    @Override
    protected void init() {
    }

    @Override
    public boolean mouseScrolled(
            double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        List<VillagerTrades.Trade> trades =
                VillagerTrades.getTradesForVillager(this.villagerName);
        int maxOffset = Math.max(0, trades.size() - TRADES_VISIBLE);

        double amount = verticalAmount != 0.0 ? verticalAmount : horizontalAmount;
        if (amount < 0) {
            tradeScrollOffset = Math.min(tradeScrollOffset + 1, maxOffset);
        } else if (amount > 0) {
            tradeScrollOffset = Math.max(tradeScrollOffset - 1, 0);
        }
        return true;
    }

    public static void renderVillagerInBoxx(
            GuiGraphicsExtractor drawer,
            int x1, int y1, int x2, int y2,
            float scale,
            Vector3f translation,
            float yaw,
            float pitch,
            @Nullable Quaternionf overrideCameraAngle,
            LivingEntity entity
    ) {
        EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        EntityRenderer<? super LivingEntity, ?> renderer = dispatcher.getRenderer(entity);

        EntityRenderState renderState = renderer.createRenderState(entity, 1.0F);

        if (renderState instanceof LivingEntityRenderState living) {
            living.bodyRot = 180.0F + yaw;
            living.yRot = yaw;
            living.xRot = -pitch;
        }

        renderState.lightCoords = 15728880;
        renderState.shadowPieces.clear();
        renderState.outlineColor = 0;

        Quaternionf rotation = new Quaternionf()
                .rotateZ((float)Math.PI);

        rotation.mul(
                new Quaternionf()
                        .rotateX((float)Math.toRadians(pitch))
        );

        drawer.entity(
                renderState,
                scale,
                translation,
                rotation,
                overrideCameraAngle,
                x1,
                y1,
                x2,
                y2
        );
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {


        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int windowX = centerX - windowWidth / 2;
        int windowY = centerY - windowHeight / 2;


        context.fill(windowX, windowY, windowX + windowWidth, windowY + windowHeight, 0xCC222222);
        context.outline(windowX, windowY, windowWidth, windowHeight, 0xFF000000);


        context.text(this.font, Component.nullToEmpty(this.villagerName),
                windowX + 10, windowY + 5, 0xFFFFFFFF, false);


        int villagerX = windowX + windowWidth - 70;
        int villagerY = windowY + 150;
        int villagerScale = 45;

        Minecraft client = Minecraft.getInstance();
        if (client.level != null) {
            Villager villager = EntityTypes.VILLAGER.create(client.level, EntitySpawnReason.TRIGGERED);

            if (villager != null) {
                villager.setId(-1);

                VillagerProfession prof = VillagerUtils.getProfessionByName(this.villagerName);
                Holder<VillagerProfession> profEntry = BuiltInRegistries.VILLAGER_PROFESSION.wrapAsHolder(prof);
                villager.setVillagerData(villager.getVillagerData().withProfession(profEntry));


                int x1 = villagerX - 25;
                int y1 = villagerY - 190;
                int x2 = villagerX + 25;
                int y2 = villagerY + 90;

                int centerBoxX = (x1 + x2) / 2;
                int centerBoxY = (y1 + y2) / 2;


                float yaw = (float)Math.atan((centerBoxX - mouseX) / 40.0F) * 20.0F;
                float pitch = (float)Math.atan((centerBoxY - mouseY) / 40.0F) * 20.0F;

                renderVillagerInBoxx(
                        context,
                        x1, y1, x2, y2,
                        villagerScale,
                        new Vector3f(0.0F, 1.0F, 0.0F),
                        yaw,
                        pitch,
                        null,
                        villager
                );
            }
        }


        List<VillagerTrades.Trade> trades = VillagerTrades.getTradesForVillager(this.villagerName);

        int tableStartX = windowX + 50;
        int tableStartY = windowY + 20;
        int levelWidth = 60;
        int itemSlotSize = 18;
        int itemSpacing = 4;
        int rowHeight = itemSlotSize + 5;

        int start = tradeScrollOffset;
        int end = Math.min(trades.size(), start + TRADES_VISIBLE);

        String lastLevel = "";
        for (int row = start; row < end; row++) {
            VillagerTrades.Trade trade = trades.get(row);
            int y = tableStartY + (row - start) * rowHeight;

            if (!trade.level().equals(lastLevel)) {

                context.text(this.font, Component.nullToEmpty(trade.level()), tableStartX - 5, y + 4, 0xFFFFFFFF, false);
                lastLevel = trade.level();
            }


            for (int i = 0; i < trade.wanted().size(); i++) {
                int slotX = tableStartX + levelWidth + i * (itemSlotSize + itemSpacing);
                ItemStack stack = trade.wanted().get(i);

                context.fill(slotX, y, slotX + itemSlotSize, y + itemSlotSize, 0xFF444444);
                context.outline(slotX, y, itemSlotSize, itemSlotSize, 0xFF000000);
                context.item(stack, slotX + 1, y + 1);


                if (stack.getCount() > 1) {
                    context.text(this.font,
                            Component.nullToEmpty(Integer.toString(stack.getCount())),
                            slotX + 2, y + itemSlotSize - 8, 0xFFFFFFFF, true);
                }


                if (mouseX >= slotX && mouseX < slotX + itemSlotSize && mouseY >= y && mouseY < y + itemSlotSize) {
                    context.setTooltipForNextFrame(this.font, stack, slotX, y);
                }
            }

            int arrowX = tableStartX + levelWidth + trade.wanted().size() * (itemSlotSize + itemSpacing) + 2;
            context.text(this.font, Component.nullToEmpty("→"), arrowX, y + 5, 0xFFFFFFFF, false);


            for (int i = 0; i < trade.given().size(); i++) {
                int slotX = arrowX + 15 + i * (itemSlotSize + itemSpacing);
                ItemStack stack = trade.given().get(i);

                context.fill(slotX, y, slotX + itemSlotSize, y + itemSlotSize, 0xFF444444);
                context.outline(slotX, y, itemSlotSize, itemSlotSize, 0xFF000000);
                context.item(stack, slotX + 1, y + 1);

                if (stack.getCount() > 1) {
                    context.text(this.font,
                            Component.nullToEmpty(Integer.toString(stack.getCount())),
                            slotX + 2, y + itemSlotSize - 8, 0xFFFFFFFF, true);
                }

                if (mouseX >= slotX && mouseX < slotX + itemSlotSize && mouseY >= y && mouseY < y + itemSlotSize) {
                    context.setTooltipForNextFrame(this.font, stack, slotX, y);
                }
            }
        }


        if (trades.size() > TRADES_VISIBLE) {
            int barArea = TRADES_VISIBLE * rowHeight;
            int barHeight = (int) ((float) TRADES_VISIBLE / trades.size() * barArea);
            int barY = tableStartY + (int) ((float) tradeScrollOffset / trades.size() * barArea);
            int barX = tableStartX + 230;
            context.fill(barX, tableStartY, barX + 6, tableStartY + barArea, 0xFF222222);
            context.fill(barX, barY, barX + 6, barY + barHeight, 0xFF888888);
        }


        int gridSize = 18;
        int gridX = windowX + windowWidth - 120;
        int gridY = windowY + windowHeight - (gridSize * 3) - 60;

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                int slotX = gridX + c * gridSize;
                int slotY = gridY + r * gridSize;
                context.fill(slotX, slotY, slotX + gridSize, slotY + gridSize, 0xFF444444);
                context.outline(slotX, slotY, gridSize, gridSize, 0xFF000000);
            }
        }

        List<GridItem> gridItems = getJobBlockRecipe(this.villagerName);
        for (GridItem gridItem : gridItems) {
            int slotX = gridX + gridItem.col * gridSize;
            int slotY = gridY + gridItem.row * gridSize;
            context.item(gridItem.stack, slotX + 1, slotY + 1);

            if (mouseX >= slotX && mouseX < slotX + gridSize && mouseY >= slotY && mouseY < slotY + gridSize) {
                context.setTooltipForNextFrame(this.font, gridItem.stack, slotX, slotY);
            }
        }

        int arrowX = gridX + gridSize * 4;
        int arrowY = gridY + gridSize;
        context.text(this.font, Component.nullToEmpty("→"), arrowX - 10, arrowY + 5, 0xFFFFFFFF, false);


        ItemStack resultStack = switch (villagerName.toLowerCase()) {
            case "farmer" -> new ItemStack(Items.COMPOSTER);
            case "librarian" -> new ItemStack(Items.LECTERN);
            case "cleric" -> new ItemStack(Items.BREWING_STAND);
            case "armorer" -> new ItemStack(Items.BLAST_FURNACE);
            case "butcher" -> new ItemStack(Items.SMOKER);
            case "cartographer" -> new ItemStack(Items.CARTOGRAPHY_TABLE);
            case "fisherman" -> new ItemStack(Items.BARREL);
            case "fletcher" -> new ItemStack(Items.FLETCHING_TABLE);
            case "leatherworker" -> new ItemStack(Items.CAULDRON);
            case "mason" -> new ItemStack(Items.STONECUTTER);
            case "shepherd" -> new ItemStack(Items.LOOM);
            case "toolsmith" -> new ItemStack(Items.SMITHING_TABLE);
            case "weaponsmith" -> new ItemStack(Items.GRINDSTONE);
            default -> ItemStack.EMPTY;
        };

        if (!resultStack.isEmpty()) {
            int resultX = gridX + gridSize * 4 + 5;
            int resultY = gridY + gridSize;
            context.item(resultStack, resultX, resultY);
            if (mouseX >= resultX && mouseX < resultX + gridSize && mouseY >= resultY && mouseY < resultY + gridSize) {
                context.setTooltipForNextFrame(this.font, resultStack, resultX, resultY);
            }
        }


        super.extractRenderState(context, mouseX, mouseY, delta);
    }
    @Override
    public void onClose() {
        Minecraft.getInstance().gui.setScreen(new VillagerExplorerScreen());
    }

    private List<GridItem> getJobBlockRecipe(String profession) {
        List<GridItem> items = new ArrayList<>();
        switch (profession.toLowerCase()) {
            case "farmer" -> {
                ItemStack slab = new ItemStack(Items.OAK_SLAB);
                items.add(new GridItem(slab, 0, 0));
                items.add(new GridItem(slab, 0, 2));
                items.add(new GridItem(slab, 1, 0));
                items.add(new GridItem(slab, 1, 2));
                items.add(new GridItem(slab, 2, 0));
                items.add(new GridItem(slab, 2, 1));
                items.add(new GridItem(slab, 2, 2));
            }
            case "librarian" -> {
                items.add(new GridItem(new ItemStack(Items.OAK_SLAB), 0, 0));
                items.add(new GridItem(new ItemStack(Items.OAK_SLAB), 0, 1));
                items.add(new GridItem(new ItemStack(Items.OAK_SLAB), 0, 2));
                items.add(new GridItem(new ItemStack(Items.BOOKSHELF), 1, 1));
                items.add(new GridItem(new ItemStack(Items.OAK_SLAB), 2, 1));
            }
            case "cleric" -> {
                items.add(new GridItem(new ItemStack(Items.BLAZE_ROD), 0, 1));
                items.add(new GridItem(new ItemStack(Items.COBBLESTONE), 1, 0));
                items.add(new GridItem(new ItemStack(Items.COBBLESTONE), 1, 1));
                items.add(new GridItem(new ItemStack(Items.COBBLESTONE), 1, 2));
            }
            case "armorer" -> {
                items.add(new GridItem(new ItemStack(Items.IRON_INGOT), 0, 0));
                items.add(new GridItem(new ItemStack(Items.IRON_INGOT), 0, 1));
                items.add(new GridItem(new ItemStack(Items.IRON_INGOT), 0, 2));
                items.add(new GridItem(new ItemStack(Items.IRON_INGOT), 1, 0));
                items.add(new GridItem(new ItemStack(Items.FURNACE), 1, 1));
                items.add(new GridItem(new ItemStack(Items.IRON_INGOT), 1, 2));
                items.add(new GridItem(new ItemStack(Items.SMOOTH_STONE), 2, 0));
                items.add(new GridItem(new ItemStack(Items.SMOOTH_STONE), 2, 1));
                items.add(new GridItem(new ItemStack(Items.SMOOTH_STONE), 2, 2));
            }
            case "butcher" -> {
                items.add(new GridItem(new ItemStack(Items.OAK_LOG), 0, 1));
                items.add(new GridItem(new ItemStack(Items.OAK_LOG), 1, 0));
                items.add(new GridItem(new ItemStack(Items.FURNACE), 1, 1));
                items.add(new GridItem(new ItemStack(Items.OAK_LOG), 1, 2));
                items.add(new GridItem(new ItemStack(Items.OAK_LOG), 2, 1));
            }
            case "cartographer" -> {
                items.add(new GridItem(new ItemStack(Items.PAPER), 0, 0));
                items.add(new GridItem(new ItemStack(Items.PAPER), 0, 1));
                items.add(new GridItem(new ItemStack(Items.OAK_PLANKS), 1, 0));
                items.add(new GridItem(new ItemStack(Items.OAK_PLANKS), 1, 1));
                items.add(new GridItem(new ItemStack(Items.OAK_PLANKS), 2, 0));
                items.add(new GridItem(new ItemStack(Items.OAK_PLANKS), 2, 1));
            }
            case "fisherman" -> {
                items.add(new GridItem(new ItemStack(Items.OAK_PLANKS), 0, 0));
                items.add(new GridItem(new ItemStack(Items.OAK_SLAB), 0, 1));
                items.add(new GridItem(new ItemStack(Items.OAK_PLANKS), 0, 2));
                items.add(new GridItem(new ItemStack(Items.OAK_PLANKS), 1, 0));
                items.add(new GridItem(new ItemStack(Items.OAK_PLANKS), 1, 2));
                items.add(new GridItem(new ItemStack(Items.OAK_PLANKS), 2, 0));
                items.add(new GridItem(new ItemStack(Items.OAK_SLAB), 2, 1));
                items.add(new GridItem(new ItemStack(Items.OAK_PLANKS), 2, 2));
            }
            case "fletcher" -> {
                items.add(new GridItem(new ItemStack(Items.FLINT), 0, 0));
                items.add(new GridItem(new ItemStack(Items.FLINT), 0, 1));
                items.add(new GridItem(new ItemStack(Items.OAK_PLANKS), 1, 0));
                items.add(new GridItem(new ItemStack(Items.OAK_PLANKS), 1, 1));
                items.add(new GridItem(new ItemStack(Items.OAK_PLANKS), 2, 0));
                items.add(new GridItem(new ItemStack(Items.OAK_PLANKS), 2, 1));
            }
            case "leatherworker" -> {
                items.add(new GridItem(new ItemStack(Items.IRON_INGOT), 0, 0));
                items.add(new GridItem(new ItemStack(Items.IRON_INGOT), 0, 2));
                items.add(new GridItem(new ItemStack(Items.IRON_INGOT), 1, 0));
                items.add(new GridItem(new ItemStack(Items.IRON_INGOT), 1, 2));
                items.add(new GridItem(new ItemStack(Items.IRON_INGOT), 2, 0));
                items.add(new GridItem(new ItemStack(Items.IRON_INGOT), 2, 1));
                items.add(new GridItem(new ItemStack(Items.IRON_INGOT), 2, 2));
            }
            case "mason" -> {
                items.add(new GridItem(new ItemStack(Items.IRON_INGOT), 0, 1));
                items.add(new GridItem(new ItemStack(Items.STONE), 1, 0));
                items.add(new GridItem(new ItemStack(Items.STONE), 1, 1));
                items.add(new GridItem(new ItemStack(Items.STONE), 1, 2));
            }
            case "shepherd" -> {
                items.add(new GridItem(new ItemStack(Items.STRING), 0, 0));
                items.add(new GridItem(new ItemStack(Items.STRING), 0, 1));
                items.add(new GridItem(new ItemStack(Items.OAK_PLANKS), 1, 0));
                items.add(new GridItem(new ItemStack(Items.OAK_PLANKS), 1, 1));
            }
            case "toolsmith" -> {
                items.add(new GridItem(new ItemStack(Items.IRON_INGOT), 0, 0));
                items.add(new GridItem(new ItemStack(Items.IRON_INGOT), 0, 1));
                items.add(new GridItem(new ItemStack(Items.OAK_PLANKS), 1, 0));
                items.add(new GridItem(new ItemStack(Items.OAK_PLANKS), 1, 1));
                items.add(new GridItem(new ItemStack(Items.OAK_PLANKS), 2, 0));
                items.add(new GridItem(new ItemStack(Items.OAK_PLANKS), 2, 1));
            }
            case "weaponsmith" -> {
                items.add(new GridItem(new ItemStack(Items.STICK), 0, 0));
                items.add(new GridItem(new ItemStack(Items.STONE_SLAB), 0, 1));
                items.add(new GridItem(new ItemStack(Items.STICK), 0, 2));
                items.add(new GridItem(new ItemStack(Items.OAK_PLANKS), 1, 0));
                items.add(new GridItem(new ItemStack(Items.OAK_PLANKS), 1, 2));
            }
            default -> {
            }
        }
        return items;
    }

    public static class GridItem {
        public final ItemStack stack;
        public final int row;
        public final int col;

        public GridItem(ItemStack stack, int row, int col) {
            this.stack = stack;
            this.row = row;
            this.col = col;
        }
    }
}