package com.samxel.villagerexplorer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.village.VillagerProfession;

import java.util.ArrayList;
import java.util.List;

public class VillagerInfoScreen extends Screen {
    private static final int TRADES_VISIBLE = 7;
    private final int windowWidth = 420;
    private final int windowHeight = 340;
    private final String villagerName;
    private int tradeScrollOffset = 0;

    public VillagerInfoScreen(String villagerName) {
        super(Text.of("Villager Info"));
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

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {


        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int windowX = centerX - windowWidth / 2;
        int windowY = centerY - windowHeight / 2;


        context.fill(windowX, windowY, windowX + windowWidth, windowY + windowHeight, 0xCC222222);
        context.drawStrokedRectangle(windowX, windowY, windowWidth, windowHeight, 0xFF000000);


        context.drawText(this.textRenderer, Text.of(this.villagerName),
                windowX + 30, windowY + 70, 0xFFFFFFFF, false);


        int villagerX = windowX + windowWidth - 70;
        int villagerY = windowY + 150;
        int villagerScale = 45;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world != null) {
            VillagerEntity villager = EntityType.VILLAGER.create(client.world, SpawnReason.TRIGGERED);
            if (villager != null) {

                VillagerProfession prof = VillagerUtils.getProfessionByName(this.villagerName);
                RegistryEntry<VillagerProfession> profEntry = Registries.VILLAGER_PROFESSION.getEntry(prof);
                villager.setVillagerData(villager.getVillagerData().withProfession(profEntry));


                int x1 = villagerX - 25;
                int y1 = villagerY - 190;
                int x2 = villagerX + 25;
                int y2 = villagerY + 90;


                float mouseYaw = (float) Math.atan((villagerX - mouseX) / 40.0F);
                float mousePitch = (float) Math.atan((villagerY - mouseY) / 40.0F);


                villager.setBodyYaw(180.0F + mouseYaw * 20.0F);
                villager.setHeadYaw(180.0F + mouseYaw * 40.0F);
                villager.setPitch(mousePitch * 20.0F);

                villager.lastBodyYaw = villager.getBodyYaw();
                villager.lastHeadYaw = villager.getHeadYaw();
                villager.lastPitch = villager.getPitch();


                InventoryScreen.drawEntity(
                        context,
                        x1, y1, x2, y2,
                        villagerScale, 1.0f,
                        mouseX, mouseY,
                        villager
                );
            }
        }


        List<VillagerTrades.Trade> trades = VillagerTrades.getTradesForVillager(this.villagerName);

        int tableStartX = windowX + 50;
        int tableStartY = windowY + 110;
        int levelWidth = 60;
        int itemSlotSize = 18;
        int itemSpacing = 4;
        int rowHeight = itemSlotSize + 6;

        int start = tradeScrollOffset;
        int end = Math.min(trades.size(), start + TRADES_VISIBLE);

        String lastLevel = "";
        for (int row = start; row < end; row++) {
            VillagerTrades.Trade trade = trades.get(row);
            int y = tableStartY + (row - start) * rowHeight;

            if (!trade.level().equals(lastLevel)) {

                context.drawText(this.textRenderer, Text.of(trade.level()), tableStartX - 5, y + 4, 0xFFFFFFFF, false);
                lastLevel = trade.level();
            }


            for (int i = 0; i < trade.wanted().size(); i++) {
                int slotX = tableStartX + levelWidth + i * (itemSlotSize + itemSpacing);
                ItemStack stack = trade.wanted().get(i);

                context.fill(slotX, y, slotX + itemSlotSize, y + itemSlotSize, 0xFF444444);
                context.drawStrokedRectangle(slotX, y, itemSlotSize, itemSlotSize, 0xFF000000);
                context.drawItem(stack, slotX + 1, y + 1);


                if (stack.getCount() > 1) {
                    context.drawText(this.textRenderer,
                            Text.of(Integer.toString(stack.getCount())),
                            slotX + 2, y + itemSlotSize - 8, 0xFFFFFFFF, true);
                }


                if (mouseX >= slotX && mouseX < slotX + itemSlotSize && mouseY >= y && mouseY < y + itemSlotSize) {
                    context.drawItemTooltip(this.textRenderer, stack, slotX, y);
                }
            }

            int arrowX = tableStartX + levelWidth + trade.wanted().size() * (itemSlotSize + itemSpacing) + 2;
            context.drawText(this.textRenderer, Text.of("→"), arrowX, y + 5, 0xFFFFFFFF, false);


            for (int i = 0; i < trade.given().size(); i++) {
                int slotX = arrowX + 15 + i * (itemSlotSize + itemSpacing);
                ItemStack stack = trade.given().get(i);

                context.fill(slotX, y, slotX + itemSlotSize, y + itemSlotSize, 0xFF444444);
                context.drawStrokedRectangle(slotX, y, itemSlotSize, itemSlotSize, 0xFF000000);
                context.drawItem(stack, slotX + 1, y + 1);

                if (stack.getCount() > 1) {
                    context.drawText(this.textRenderer,
                            Text.of(Integer.toString(stack.getCount())),
                            slotX + 2, y + itemSlotSize - 8, 0xFFFFFFFF, true);
                }

                if (mouseX >= slotX && mouseX < slotX + itemSlotSize && mouseY >= y && mouseY < y + itemSlotSize) {
                    context.drawItemTooltip(this.textRenderer, stack, slotX, y);
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
                context.drawStrokedRectangle(slotX, slotY, gridSize, gridSize, 0xFF000000);
            }
        }

        List<GridItem> gridItems = getJobBlockRecipe(this.villagerName);
        for (GridItem gridItem : gridItems) {
            int slotX = gridX + gridItem.col * gridSize;
            int slotY = gridY + gridItem.row * gridSize;
            context.drawItem(gridItem.stack, slotX + 1, slotY + 1);

            if (mouseX >= slotX && mouseX < slotX + gridSize && mouseY >= slotY && mouseY < slotY + gridSize) {
                context.drawItemTooltip(this.textRenderer, gridItem.stack, slotX, slotY);
            }
        }

        int arrowX = gridX + gridSize * 4;
        int arrowY = gridY + gridSize;
        context.drawText(this.textRenderer, Text.of("→"), arrowX - 10, arrowY + 5, 0xFFFFFFFF, false);


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
            context.drawItem(resultStack, resultX, resultY);
            if (mouseX >= resultX && mouseX < resultX + gridSize && mouseY >= resultY && mouseY < resultY + gridSize) {
                context.drawItemTooltip(this.textRenderer, resultStack, resultX, resultY);
            }
        }


        super.render(context, mouseX, mouseY, delta);
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