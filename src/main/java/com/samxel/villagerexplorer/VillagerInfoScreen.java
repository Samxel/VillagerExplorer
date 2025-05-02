package com.samxel.villagerexplorer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.village.VillagerProfession;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class VillagerInfoScreen extends Screen {
    private final int windowWidth = 420;
    private final int windowHeight = 340;

    private final String villagerName;


    private int tradeScrollOffset = 0;
    private static final int TRADES_VISIBLE = 7;

    public VillagerInfoScreen(String villagerName) {
        super(Text.of("Villager Info"));
        this.villagerName = villagerName;
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

    @Override
    protected void init() {

    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        List<VillagerTrades.Trade> trades = VillagerTrades.getTradesForVillager(this.villagerName);
        int maxOffset = Math.max(0, trades.size() - TRADES_VISIBLE);

        if (verticalAmount < 0) {
            tradeScrollOffset = Math.min(tradeScrollOffset + 1, maxOffset);
        } else if (verticalAmount > 0) {
            tradeScrollOffset = Math.max(tradeScrollOffset - 1, 0);
        }
        return true;
    }


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int windowX = centerX - windowWidth / 2;
        int windowY = centerY - windowHeight / 2;


        context.fill(windowX, windowY, windowX + windowWidth, windowY + windowHeight, 0xCC222222);


        context.drawBorder(windowX, windowY, windowWidth, windowHeight, 0xFF000000);


        context.getMatrices().push();
        context.getMatrices().translate(windowX + 30, windowY + 70, 0);
        float scale = 2.0f;
        context.getMatrices().scale(scale, scale, 1.0f);
        context.drawText(
                this.textRenderer,
                Text.of(this.villagerName),
                0,
                0,
                0xFFFFFF,
                false
        );
        context.getMatrices().pop();


        int villagerX = windowX + windowWidth - 70;
        int villagerY = windowY + 120;
        int villagerScale = 45;


        MinecraftClient client = MinecraftClient.getInstance();
        VillagerEntity villager = EntityType.VILLAGER.create(client.world, SpawnReason.TRIGGERED);
        if (villager != null) {
            villager.setVillagerData(
                    villager.getVillagerData().withProfession(getProfessionByName(this.villagerName))
            );
            drawEntityFollowingMouse(context, villagerX, villagerY, villagerScale, mouseX, mouseY, villager);
        }


        List<VillagerTrades.Trade> trades = VillagerTrades.getTradesForVillager(this.villagerName);

        int tableStartX = windowX + 30;
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
                context.drawText(
                        this.textRenderer,
                        Text.of(trade.level()),
                        tableStartX,
                        y + 4,
                        0xFFFFFF,
                        false
                );
                lastLevel = trade.level();
            }


            for (int i = 0; i < trade.wanted().size(); i++) {
                int slotX = tableStartX + levelWidth + i * (itemSlotSize + itemSpacing);
                ItemStack stack = trade.wanted().get(i);


                context.fill(slotX, y, slotX + itemSlotSize, y + itemSlotSize, 0xFF444444);
                context.drawBorder(slotX, y, itemSlotSize, itemSlotSize, 0xFF000000);


                context.drawItem(stack, slotX + 1, y + 1, 1);


                if (stack.getCount() > 1) {
                    context.getMatrices().push();
                    context.getMatrices().translate(0, 0, 200);
                    context.drawText(
                            this.textRenderer,
                            Text.of(Integer.toString(stack.getCount())),
                            slotX + 2,
                            y + itemSlotSize - 8,
                            0xFFFFFF,
                            true
                    );
                    context.getMatrices().pop();
                }


                if (
                        mouseX >= slotX &&
                                mouseX < slotX + itemSlotSize &&
                                mouseY >= y &&
                                mouseY < y + itemSlotSize
                ) {
                    context.drawItemTooltip(this.textRenderer, stack, slotX, y);
                }
            }


            int arrowX = tableStartX + levelWidth + trade.wanted().size() * (itemSlotSize + itemSpacing) + 2;
            context.drawText(this.textRenderer, Text.of("→"), arrowX, y + 5, 0xFFFFFF, false);


            for (int i = 0; i < trade.given().size(); i++) {
                int slotX = arrowX + 15 + i * (itemSlotSize + itemSpacing);
                ItemStack stack = trade.given().get(i);


                context.fill(slotX, y, slotX + itemSlotSize, y + itemSlotSize, 0xFF444444);
                context.drawBorder(slotX, y, itemSlotSize, itemSlotSize, 0xFF000000);


                context.drawItem(stack, slotX + 1, y + 1, 1);


                if (stack.getCount() > 1) {
                    context.getMatrices().push();
                    context.getMatrices().translate(0, 0, 200);
                    context.drawText(
                            this.textRenderer,
                            Text.of(Integer.toString(stack.getCount())),
                            slotX + 2,
                            y + itemSlotSize - 8,
                            0xFFFFFF,
                            true
                    );
                    context.getMatrices().pop();
                }


                if (
                        mouseX >= slotX &&
                                mouseX < slotX + itemSlotSize &&
                                mouseY >= y &&
                                mouseY < y + itemSlotSize
                ) {
                    context.drawItemTooltip(this.textRenderer, stack, slotX, y);
                }
            }

        }

        if (trades.size() > TRADES_VISIBLE) {
            int barHeight = (int) ((float) TRADES_VISIBLE / trades.size() * (TRADES_VISIBLE * rowHeight));
            int barY = tableStartY + (int) ((float) tradeScrollOffset / trades.size() * (TRADES_VISIBLE * rowHeight));
            int barX = tableStartX + 230;
            context.fill(barX, tableStartY, barX + 6, tableStartY + TRADES_VISIBLE * rowHeight, 0xFF222222);
            context.fill(barX, barY, barX + 6, barY + barHeight, 0xFF888888);
        }


        int gridSize = 18;
        int gridX = windowX + windowWidth - 120;
        int gridY = windowY + windowHeight - (gridSize * 3) - 60;


        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int slotX = gridX + col * gridSize;
                int slotY = gridY + row * gridSize;
                context.fill(slotX, slotY, slotX + gridSize, slotY + gridSize, 0xFF444444);
                context.drawBorder(slotX, slotY, gridSize, gridSize, 0xFF000000);
            }
        }


        List<GridItem> gridItems = getJobBlockRecipe(this.villagerName);
        for (GridItem gridItem : gridItems) {
            int slotX = gridX + gridItem.col * gridSize;
            int slotY = gridY + gridItem.row * gridSize;
            context.drawItem(gridItem.stack, slotX + 1, slotY + 1, 1);

            if (mouseX >= slotX && mouseX < slotX + gridSize && mouseY >= slotY && mouseY < slotY + gridSize) {
                context.drawItemTooltip(this.textRenderer, gridItem.stack, slotX, slotY);
            }
        }
        int arrowX = gridX + gridSize * 4;
        int arrowY = gridY + gridSize;
        context.drawText(this.textRenderer, Text.of("→"), arrowX - 10, arrowY + 5, 0xFFFFFF, false);


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
            context.drawItem(resultStack, resultX, resultY, 1);
            if (mouseX >= resultX && mouseX < resultX + gridSize && mouseY >= resultY && mouseY < resultY + gridSize) {
                context.drawItemTooltip(this.textRenderer, resultStack, resultX, resultY);
            }
        }
    }

    public static void drawEntityFollowingMouse(
            DrawContext context,
            int x, int y, int size,
            float mouseX, float mouseY,
            LivingEntity entity
    ) {
        float g = x;
        float h = y;
        float i = (float) Math.atan((g - mouseX) / 40.0F);
        float j = (float) Math.atan((h - mouseY) / 40.0F);

        Quaternionf quaternionf = (new Quaternionf()).rotateZ((float) Math.PI);
        Quaternionf quaternionf2 = (new Quaternionf()).rotateX(j * 20.0F * ((float) Math.PI / 180F));
        quaternionf.mul(quaternionf2);

        float k = entity.bodyYaw;
        float l = entity.getYaw();
        float m = entity.getPitch();
        float n = entity.lastHeadYaw;
        float o = entity.headYaw;

        entity.bodyYaw = 180.0F + i * 20.0F;
        entity.setYaw(180.0F + i * 40.0F);
        entity.setPitch(-j * 20.0F);
        entity.headYaw = entity.getYaw();
        entity.lastHeadYaw = entity.getYaw();

        float p = entity.getScale();
        Vector3f vector3f = new Vector3f(0.0F, entity.getHeight() / 2.0F, 0.0F);
        float q = (float) size / p;


        context.getMatrices().push();
        context.getMatrices().translate(g, (double) h, 50.0F);
        context.getMatrices().scale(q, q, -q);
        context.getMatrices().translate(vector3f.x, vector3f.y, vector3f.z);
        context.getMatrices().multiply(quaternionf);
        context.draw();
        var dispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        dispatcher.setRenderShadows(false);
        context.draw((vertexConsumers) -> dispatcher.render(entity, 0.0, 0.0, 0.0, 1.0F, context.getMatrices(), vertexConsumers, 15728880));
        context.draw();
        dispatcher.setRenderShadows(true);
        context.getMatrices().pop();


        entity.bodyYaw = k;
        entity.setYaw(l);
        entity.setPitch(m);
        entity.lastHeadYaw = n;
        entity.headYaw = o;
    }

    private static RegistryEntry<VillagerProfession> getProfessionByName(String name) {
        return switch (name.toLowerCase()) {
            case "farmer" -> Registries.VILLAGER_PROFESSION.getEntry(VillagerProfession.FARMER.getValue())
                    .orElse(Registries.VILLAGER_PROFESSION.getEntry(VillagerProfession.NONE.getValue()).get());
            case "librarian" -> Registries.VILLAGER_PROFESSION.getEntry(VillagerProfession.LIBRARIAN.getValue())
                    .orElse(Registries.VILLAGER_PROFESSION.getEntry(VillagerProfession.NONE.getValue()).get());
            case "cleric" -> Registries.VILLAGER_PROFESSION.getEntry(VillagerProfession.CLERIC.getValue())
                    .orElse(Registries.VILLAGER_PROFESSION.getEntry(VillagerProfession.NONE.getValue()).get());
            case "armorer" -> Registries.VILLAGER_PROFESSION.getEntry(VillagerProfession.ARMORER.getValue())
                    .orElse(Registries.VILLAGER_PROFESSION.getEntry(VillagerProfession.NONE.getValue()).get());
            case "butcher" -> Registries.VILLAGER_PROFESSION.getEntry(VillagerProfession.BUTCHER.getValue())
                    .orElse(Registries.VILLAGER_PROFESSION.getEntry(VillagerProfession.NONE.getValue()).get());
            case "cartographer" -> Registries.VILLAGER_PROFESSION.getEntry(VillagerProfession.CARTOGRAPHER.getValue())
                    .orElse(Registries.VILLAGER_PROFESSION.getEntry(VillagerProfession.NONE.getValue()).get());
            case "fisherman" -> Registries.VILLAGER_PROFESSION.getEntry(VillagerProfession.FISHERMAN.getValue())
                    .orElse(Registries.VILLAGER_PROFESSION.getEntry(VillagerProfession.NONE.getValue()).get());
            case "fletcher" -> Registries.VILLAGER_PROFESSION.getEntry(VillagerProfession.FLETCHER.getValue())
                    .orElse(Registries.VILLAGER_PROFESSION.getEntry(VillagerProfession.NONE.getValue()).get());
            case "leatherworker" -> Registries.VILLAGER_PROFESSION.getEntry(VillagerProfession.LEATHERWORKER.getValue())
                    .orElse(Registries.VILLAGER_PROFESSION.getEntry(VillagerProfession.NONE.getValue()).get());
            case "mason" -> Registries.VILLAGER_PROFESSION.getEntry(VillagerProfession.MASON.getValue())
                    .orElse(Registries.VILLAGER_PROFESSION.getEntry(VillagerProfession.NONE.getValue()).get());
            case "shepherd" -> Registries.VILLAGER_PROFESSION.getEntry(VillagerProfession.SHEPHERD.getValue())
                    .orElse(Registries.VILLAGER_PROFESSION.getEntry(VillagerProfession.NONE.getValue()).get());
            case "toolsmith" -> Registries.VILLAGER_PROFESSION.getEntry(VillagerProfession.TOOLSMITH.getValue())
                    .orElse(Registries.VILLAGER_PROFESSION.getEntry(VillagerProfession.NONE.getValue()).get());
            case "weaponsmith" -> Registries.VILLAGER_PROFESSION.getEntry(VillagerProfession.WEAPONSMITH.getValue())
                    .orElse(Registries.VILLAGER_PROFESSION.getEntry(VillagerProfession.NONE.getValue()).get());
            default -> Registries.VILLAGER_PROFESSION.getEntry(VillagerProfession.NONE.getValue()).get();
        };
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
}
