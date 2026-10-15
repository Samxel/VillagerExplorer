package com.samxel.villagerexplorer.villager;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static com.samxel.villagerexplorer.villager.TradeLevel.APPRENTICE;
import static com.samxel.villagerexplorer.villager.TradeLevel.EXPERT;
import static com.samxel.villagerexplorer.villager.TradeLevel.JOURNEYMAN;
import static com.samxel.villagerexplorer.villager.TradeLevel.MASTER;
import static com.samxel.villagerexplorer.villager.TradeLevel.NOVICE;

public final class VillagerTrades {
    private static final Map<Profession, List<Trade>> TRADES = new EnumMap<>(Profession.class);

    static {
        TRADES.put(Profession.ARMORER, List.of(
                buy(NOVICE, Items.COAL, 15),
                sell(NOVICE, 5, Items.IRON_HELMET),
                sell(NOVICE, 9, Items.IRON_CHESTPLATE),
                sell(NOVICE, 7, Items.IRON_LEGGINGS),
                sell(NOVICE, 4, Items.IRON_BOOTS),
                buy(APPRENTICE, Items.IRON_INGOT, 4),
                sell(APPRENTICE, 36, Items.BELL),
                sell(APPRENTICE, 3, Items.CHAINMAIL_LEGGINGS),
                sell(APPRENTICE, 1, Items.CHAINMAIL_BOOTS),
                buy(JOURNEYMAN, Items.LAVA_BUCKET, 1),
                buy(JOURNEYMAN, Items.DIAMOND, 1),
                sell(JOURNEYMAN, 1, Items.CHAINMAIL_HELMET),
                sell(JOURNEYMAN, 4, Items.CHAINMAIL_CHESTPLATE),
                sell(JOURNEYMAN, 5, Items.SHIELD),
                enchanted(EXPERT, 19, Items.DIAMOND_LEGGINGS),
                enchanted(EXPERT, 13, Items.DIAMOND_BOOTS),
                enchanted(MASTER, 13, Items.DIAMOND_HELMET),
                enchanted(MASTER, 21, Items.DIAMOND_CHESTPLATE)
        ));

        TRADES.put(Profession.BUTCHER, List.of(
                buy(NOVICE, Items.CHICKEN, 14),
                buy(NOVICE, Items.PORKCHOP, 7),
                buy(NOVICE, Items.RABBIT, 4),
                sell(NOVICE, 1, Items.RABBIT_STEW),
                buy(APPRENTICE, Items.COAL, 15),
                sell(APPRENTICE, 1, Items.COOKED_PORKCHOP, 5),
                sell(APPRENTICE, 1, Items.COOKED_CHICKEN, 8),
                buy(JOURNEYMAN, Items.MUTTON, 7),
                buy(JOURNEYMAN, Items.BEEF, 10),
                buy(EXPERT, Items.DRIED_KELP_BLOCK, 10),
                buy(MASTER, Items.SWEET_BERRIES, 10)
        ));

        TRADES.put(Profession.CARTOGRAPHER, List.of(
                buy(NOVICE, Items.PAPER, 24),
                sell(NOVICE, 7, Items.MAP),
                buy(APPRENTICE, Items.GLASS_PANE, 11),
                explorerMap(APPRENTICE, 8, Items.PLAINS_VILLAGE_MAP),
                explorerMap(APPRENTICE, 8, Items.DESERT_VILLAGE_MAP),
                explorerMap(APPRENTICE, 8, Items.SAVANNA_VILLAGE_MAP),
                explorerMap(APPRENTICE, 8, Items.SNOWY_VILLAGE_MAP),
                explorerMap(APPRENTICE, 8, Items.TAIGA_VILLAGE_MAP),
                explorerMap(APPRENTICE, 8, Items.SWAMP_HUT_MAP),
                explorerMap(APPRENTICE, 8, Items.JUNGLE_PYRAMID_MAP),
                buy(JOURNEYMAN, Items.COMPASS, 1),
                explorerMap(JOURNEYMAN, 13, Items.OCEAN_MONUMENT_MAP),
                explorerMap(JOURNEYMAN, 12, Items.BURIED_TRIAL_CHAMBERS_MAP),
                sell(EXPERT, 7, Items.ITEM_FRAME),
                sell(EXPERT, 2, Items.BANNER.white()),
                sell(MASTER, 8, Items.GLOBE_BANNER_PATTERN),
                explorerMap(MASTER, 14, Items.WOODLAND_MANSION_MAP)
        ));

        TRADES.put(Profession.CLERIC, List.of(
                buy(NOVICE, Items.ROTTEN_FLESH, 32),
                sell(NOVICE, 1, Items.REDSTONE, 2),
                buy(APPRENTICE, Items.GOLD_INGOT, 3),
                sell(APPRENTICE, 1, Items.LAPIS_LAZULI),
                buy(JOURNEYMAN, Items.RABBIT_FOOT, 2),
                sell(JOURNEYMAN, 4, Items.GLOWSTONE),
                buy(EXPERT, Items.TURTLE_SCUTE, 4),
                buy(EXPERT, Items.GLASS_BOTTLE, 9),
                sell(EXPERT, 5, Items.ENDER_PEARL),
                buy(MASTER, Items.NETHER_WART, 22),
                sell(MASTER, 3, Items.EXPERIENCE_BOTTLE)
        ));

        TRADES.put(Profession.FARMER, List.of(
                buy(NOVICE, Items.WHEAT, 20),
                buy(NOVICE, Items.POTATO, 26),
                buy(NOVICE, Items.CARROT, 22),
                buy(NOVICE, Items.BEETROOT, 15),
                sell(NOVICE, 1, Items.BREAD, 6),
                buy(APPRENTICE, Items.PUMPKIN, 6),
                sell(APPRENTICE, 1, Items.PUMPKIN_PIE, 4),
                sell(APPRENTICE, 1, Items.APPLE, 4),
                buy(JOURNEYMAN, Items.MELON, 4),
                sell(JOURNEYMAN, 3, Items.COOKIE, 18),
                sell(EXPERT, 3, Items.CAKE),
                sell(EXPERT, 1, Items.SUSPICIOUS_STEW),
                sell(MASTER, 3, Items.GOLDEN_CARROT, 3),
                sell(MASTER, 4, Items.GLISTERING_MELON_SLICE, 3)
        ));

        TRADES.put(Profession.FISHERMAN, List.of(
                buy(NOVICE, Items.STRING, 20),
                buy(NOVICE, Items.COAL, 10),
                exchange(NOVICE, Items.COD, 6, Items.COOKED_COD, 6),
                sell(NOVICE, 3, Items.COD_BUCKET),
                buy(APPRENTICE, Items.COD, 15),
                exchange(APPRENTICE, Items.SALMON, 6, Items.COOKED_SALMON, 6),
                sell(APPRENTICE, 2, Items.CAMPFIRE),
                buy(JOURNEYMAN, Items.SALMON, 13),
                enchanted(JOURNEYMAN, 8, Items.FISHING_ROD),
                buy(EXPERT, Items.TROPICAL_FISH, 6),
                buy(MASTER, Items.PUFFERFISH, 4),
                buy(MASTER, Items.OAK_BOAT, 1),
                buy(MASTER, Items.SPRUCE_BOAT, 1),
                buy(MASTER, Items.JUNGLE_BOAT, 1),
                buy(MASTER, Items.ACACIA_BOAT, 1),
                buy(MASTER, Items.DARK_OAK_BOAT, 1)
        ));

        TRADES.put(Profession.FLETCHER, List.of(
                buy(NOVICE, Items.STICK, 32),
                sell(NOVICE, 1, Items.ARROW, 16),
                exchange(NOVICE, Items.GRAVEL, 10, Items.FLINT, 10),
                buy(APPRENTICE, Items.FLINT, 26),
                sell(APPRENTICE, 2, Items.BOW),
                buy(JOURNEYMAN, Items.STRING, 14),
                sell(JOURNEYMAN, 3, Items.CROSSBOW),
                buy(EXPERT, Items.FEATHER, 24),
                enchanted(EXPERT, 7, Items.BOW),
                buy(MASTER, Items.TRIPWIRE_HOOK, 8),
                enchanted(MASTER, 8, Items.CROSSBOW),
                trade(MASTER, List.of(emeralds(2), new ItemStack(Items.ARROW, 5)), new ItemStack(Items.TIPPED_ARROW, 5))
        ));

        TRADES.put(Profession.LEATHERWORKER, List.of(
                buy(NOVICE, Items.LEATHER, 6),
                sell(NOVICE, 3, Items.LEATHER_LEGGINGS),
                sell(NOVICE, 7, Items.LEATHER_CHESTPLATE),
                buy(APPRENTICE, Items.FLINT, 26),
                sell(APPRENTICE, 5, Items.LEATHER_HELMET),
                sell(APPRENTICE, 4, Items.LEATHER_BOOTS),
                buy(JOURNEYMAN, Items.RABBIT_HIDE, 9),
                sell(JOURNEYMAN, 7, Items.LEATHER_CHESTPLATE),
                buy(EXPERT, Items.TURTLE_SCUTE, 4),
                sell(EXPERT, 6, Items.LEATHER_HORSE_ARMOR),
                sell(MASTER, 6, Items.SADDLE),
                sell(MASTER, 5, Items.LEATHER_HELMET)
        ));

        TRADES.put(Profession.LIBRARIAN, List.of(
                buy(NOVICE, Items.PAPER, 24),
                enchantedBook(NOVICE),
                sell(NOVICE, 9, Items.BOOKSHELF),
                buy(APPRENTICE, Items.BOOK, 4),
                enchantedBook(APPRENTICE),
                sell(APPRENTICE, 1, Items.LANTERN),
                buy(JOURNEYMAN, Items.INK_SAC, 5),
                enchantedBook(JOURNEYMAN),
                sell(JOURNEYMAN, 1, Items.GLASS, 4),
                buy(EXPERT, Items.WRITABLE_BOOK, 2),
                enchantedBook(EXPERT),
                sell(EXPERT, 5, Items.CLOCK),
                sell(EXPERT, 4, Items.COMPASS),
                sell(MASTER, 3, Items.DYED_CANDLE.yellow()),
                sell(MASTER, 3, Items.DYED_CANDLE.red())
        ));

        TRADES.put(Profession.MASON, List.of(
                buy(NOVICE, Items.CLAY_BALL, 10),
                sell(NOVICE, 1, Items.BRICK, 10),
                buy(APPRENTICE, Items.STONE, 20),
                sell(APPRENTICE, 1, Items.CHISELED_STONE_BRICKS, 4),
                buy(JOURNEYMAN, Items.GRANITE, 16),
                buy(JOURNEYMAN, Items.ANDESITE, 16),
                buy(JOURNEYMAN, Items.DIORITE, 16),
                sell(JOURNEYMAN, 1, Items.DRIPSTONE_BLOCK, 4),
                sell(JOURNEYMAN, 1, Items.POLISHED_ANDESITE, 4),
                sell(JOURNEYMAN, 1, Items.POLISHED_DIORITE, 4),
                sell(JOURNEYMAN, 1, Items.POLISHED_GRANITE, 4),
                buy(EXPERT, Items.QUARTZ, 12),
                sell(EXPERT, 1, Items.DYED_TERRACOTTA.white()),
                sell(EXPERT, 1, Items.GLAZED_TERRACOTTA.white()),
                sell(MASTER, 1, Items.QUARTZ_PILLAR),
                sell(MASTER, 1, Items.QUARTZ_BLOCK)
        ));

        TRADES.put(Profession.SHEPHERD, List.of(
                buy(NOVICE, Items.WOOL.white(), 18),
                buy(NOVICE, Items.WOOL.brown(), 18),
                buy(NOVICE, Items.WOOL.gray(), 18),
                buy(NOVICE, Items.WOOL.black(), 18),
                sell(NOVICE, 2, Items.SHEARS),
                buy(APPRENTICE, Items.DYE.white(), 12),
                buy(APPRENTICE, Items.DYE.gray(), 12),
                buy(APPRENTICE, Items.DYE.black(), 12),
                buy(APPRENTICE, Items.DYE.lightBlue(), 12),
                buy(APPRENTICE, Items.DYE.lime(), 12),
                sell(APPRENTICE, 1, Items.WOOL.white()),
                sell(APPRENTICE, 1, Items.CARPET.white(), 4),
                buy(JOURNEYMAN, Items.DYE.yellow(), 12),
                buy(JOURNEYMAN, Items.DYE.lightGray(), 12),
                buy(JOURNEYMAN, Items.DYE.orange(), 12),
                buy(JOURNEYMAN, Items.DYE.red(), 12),
                buy(JOURNEYMAN, Items.DYE.pink(), 12),
                sell(JOURNEYMAN, 3, Items.BED.white()),
                buy(EXPERT, Items.DYE.brown(), 12),
                buy(EXPERT, Items.DYE.purple(), 12),
                buy(EXPERT, Items.DYE.blue(), 12),
                buy(EXPERT, Items.DYE.green(), 12),
                buy(EXPERT, Items.DYE.magenta(), 12),
                buy(EXPERT, Items.DYE.cyan(), 12),
                sell(EXPERT, 3, Items.BANNER.white()),
                sell(MASTER, 2, Items.PAINTING, 3)
        ));

        TRADES.put(Profession.TOOLSMITH, List.of(
                buy(NOVICE, Items.COAL, 15),
                sell(NOVICE, 1, Items.STONE_AXE),
                sell(NOVICE, 1, Items.STONE_SHOVEL),
                sell(NOVICE, 1, Items.STONE_PICKAXE),
                sell(NOVICE, 1, Items.STONE_HOE),
                buy(APPRENTICE, Items.IRON_INGOT, 4),
                sell(APPRENTICE, 36, Items.BELL),
                buy(JOURNEYMAN, Items.FLINT, 30),
                enchanted(JOURNEYMAN, 6, Items.IRON_AXE),
                enchanted(JOURNEYMAN, 7, Items.IRON_SHOVEL),
                enchanted(JOURNEYMAN, 8, Items.IRON_PICKAXE),
                sell(JOURNEYMAN, 4, Items.DIAMOND_HOE),
                buy(EXPERT, Items.DIAMOND, 1),
                enchanted(EXPERT, 17, Items.DIAMOND_AXE),
                enchanted(EXPERT, 10, Items.DIAMOND_SHOVEL),
                enchanted(MASTER, 18, Items.DIAMOND_PICKAXE)
        ));

        TRADES.put(Profession.WEAPONSMITH, List.of(
                buy(NOVICE, Items.COAL, 15),
                sell(NOVICE, 3, Items.IRON_AXE),
                enchanted(NOVICE, 7, Items.IRON_SWORD),
                buy(APPRENTICE, Items.IRON_INGOT, 4),
                sell(APPRENTICE, 36, Items.BELL),
                buy(JOURNEYMAN, Items.FLINT, 24),
                buy(EXPERT, Items.DIAMOND, 1),
                enchanted(EXPERT, 17, Items.DIAMOND_AXE),
                enchanted(MASTER, 13, Items.DIAMOND_SWORD)
        ));
    }

    private VillagerTrades() {
    }

    public static List<Trade> forProfession(Profession profession) {
        return TRADES.getOrDefault(profession, List.of());
    }

    private static Trade trade(TradeLevel level, List<ItemStack> wanted, ItemStack given) {
        return new Trade(level, wanted, given);
    }

    private static Trade buy(TradeLevel level, Item item, int count) {
        return trade(level, List.of(new ItemStack(item, count)), emeralds(1));
    }

    private static Trade sell(TradeLevel level, int price, Item item) {
        return sell(level, price, item, 1);
    }

    private static Trade sell(TradeLevel level, int price, Item item, int count) {
        return trade(level, List.of(emeralds(price)), new ItemStack(item, count));
    }

    private static Trade exchange(TradeLevel level, Item item, int count, Item result, int resultCount) {
        return trade(level, List.of(new ItemStack(item, count), emeralds(1)), new ItemStack(result, resultCount));
    }

    private static Trade enchanted(TradeLevel level, int minPrice, Item item) {
        return trade(level, List.of(emeralds(minPrice)), glint(new ItemStack(item)));
    }

    private static Trade enchantedBook(TradeLevel level) {
        return trade(level, List.of(emeralds(5), new ItemStack(Items.BOOK)), new ItemStack(Items.ENCHANTED_BOOK));
    }

    private static Trade explorerMap(TradeLevel level, int price, Item map) {
        return trade(level, List.of(emeralds(price), new ItemStack(Items.COMPASS)), new ItemStack(map));
    }

    private static ItemStack emeralds(int count) {
        return new ItemStack(Items.EMERALD, count);
    }

    private static ItemStack glint(ItemStack stack) {
        stack.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
        return stack;
    }
}
