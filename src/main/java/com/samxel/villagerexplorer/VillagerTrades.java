package com.samxel.villagerexplorer;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.List;
import java.util.Map;

public class VillagerTrades {
    public record Trade(String level, List<ItemStack> wanted, List<ItemStack> given) {
    }

    public static final Map<String, List<Trade>> TRADES = Map.ofEntries(


            Map.entry("armorer", List.of(

                    new Trade("Novice", List.of(new ItemStack(Items.COAL, 15)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Novice", List.of(new ItemStack(Items.EMERALD, 5)), List.of(new ItemStack(Items.IRON_HELMET))),
                    new Trade("Novice", List.of(new ItemStack(Items.EMERALD, 9)), List.of(new ItemStack(Items.IRON_CHESTPLATE))),
                    new Trade("Novice", List.of(new ItemStack(Items.EMERALD, 7)), List.of(new ItemStack(Items.IRON_LEGGINGS))),
                    new Trade("Novice", List.of(new ItemStack(Items.EMERALD, 4)), List.of(new ItemStack(Items.IRON_BOOTS))),

                    new Trade("Apprentice", List.of(new ItemStack(Items.IRON_INGOT, 4)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Apprentice", List.of(new ItemStack(Items.EMERALD, 36)), List.of(new ItemStack(Items.BELL))),
                    new Trade("Apprentice", List.of(new ItemStack(Items.EMERALD, 3)), List.of(new ItemStack(Items.CHAINMAIL_LEGGINGS))),
                    new Trade("Apprentice", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.CHAINMAIL_BOOTS))),

                    new Trade("Journeyman", List.of(new ItemStack(Items.LAVA_BUCKET)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Journeyman", List.of(new ItemStack(Items.DIAMOND)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Journeyman", List.of(new ItemStack(Items.EMERALD, 5)), List.of(new ItemStack(Items.CHAINMAIL_HELMET))),
                    new Trade("Journeyman", List.of(new ItemStack(Items.EMERALD, 4)), List.of(new ItemStack(Items.CHAINMAIL_CHESTPLATE))),
                    new Trade("Journeyman", List.of(new ItemStack(Items.EMERALD, 5)), List.of(new ItemStack(Items.SHIELD))),

                    new Trade("Expert", List.of(new ItemStack(Items.EMERALD, 19)), List.of(enchantedGlint(new ItemStack(Items.DIAMOND_LEGGINGS)))),
                    new Trade("Expert", List.of(new ItemStack(Items.EMERALD, 13)), List.of(enchantedGlint(new ItemStack(Items.DIAMOND_BOOTS)))),

                    new Trade("Master", List.of(new ItemStack(Items.EMERALD, 13)), List.of(enchantedGlint(new ItemStack(Items.DIAMOND_HELMET)))),
                    new Trade("Master", List.of(new ItemStack(Items.EMERALD, 21)), List.of(enchantedGlint(new ItemStack(Items.DIAMOND_CHESTPLATE))))
            )),


            Map.entry("butcher", List.of(

                    new Trade("Novice", List.of(new ItemStack(Items.CHICKEN, 14)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Novice", List.of(new ItemStack(Items.RABBIT, 4)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Novice", List.of(new ItemStack(Items.PORKCHOP, 7)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Novice", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.RABBIT_STEW))),

                    new Trade("Apprentice", List.of(new ItemStack(Items.COAL, 15)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Apprentice", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.COOKED_CHICKEN, 8))),
                    new Trade("Apprentice", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.COOKED_PORKCHOP, 5))),

                    new Trade("Journeyman", List.of(new ItemStack(Items.BEEF, 10)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Journeyman", List.of(new ItemStack(Items.MUTTON, 7)), List.of(new ItemStack(Items.EMERALD))),

                    new Trade("Expert", List.of(new ItemStack(Items.DRIED_KELP_BLOCK, 10)), List.of(new ItemStack(Items.EMERALD))),

                    new Trade("Master", List.of(new ItemStack(Items.SWEET_BERRIES, 10)), List.of(new ItemStack(Items.EMERALD)))
            )),


            Map.entry("cartographer", List.of(

                    new Trade("Novice", List.of(new ItemStack(Items.PAPER, 24)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Novice", List.of(new ItemStack(Items.EMERALD, 7)), List.of(new ItemStack(Items.MAP))),

                    new Trade("Apprentice", List.of(new ItemStack(Items.GLASS_PANE, 11)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Apprentice", List.of(new ItemStack(Items.EMERALD, 8), new ItemStack(Items.COMPASS)), List.of(new ItemStack(Items.MAP))),

                    new Trade("Journeyman", List.of(new ItemStack(Items.COMPASS)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Journeyman", List.of(new ItemStack(Items.EMERALD, 13), new ItemStack(Items.COMPASS)), List.of(new ItemStack(Items.MAP))),
                    new Trade("Journeyman", List.of(new ItemStack(Items.EMERALD, 12), new ItemStack(Items.COMPASS)), List.of(new ItemStack(Items.MAP))),

                    new Trade("Expert", List.of(new ItemStack(Items.EMERALD, 7)), List.of(new ItemStack(Items.ITEM_FRAME))),
                    new Trade("Expert", List.of(new ItemStack(Items.EMERALD, 3)), List.of(new ItemStack(Items.WHITE_BANNER))),

                    new Trade("Master", List.of(new ItemStack(Items.EMERALD, 8)), List.of(new ItemStack(Items.FLOWER_BANNER_PATTERN))),
                    new Trade("Master", List.of(new ItemStack(Items.EMERALD, 14), new ItemStack(Items.COMPASS)), List.of(new ItemStack(Items.MAP)))
            )),


            Map.entry("cleric", List.of(

                    new Trade("Novice", List.of(new ItemStack(Items.ROTTEN_FLESH, 32)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Novice", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.REDSTONE, 2))),

                    new Trade("Apprentice", List.of(new ItemStack(Items.GOLD_INGOT, 3)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Apprentice", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.LAPIS_LAZULI))),

                    new Trade("Journeyman", List.of(new ItemStack(Items.RABBIT_FOOT, 2)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Journeyman", List.of(new ItemStack(Items.EMERALD, 4)), List.of(new ItemStack(Items.GLOWSTONE))),

                    new Trade("Expert", List.of(new ItemStack(Items.TURTLE_SCUTE, 4)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Expert", List.of(new ItemStack(Items.GLASS_BOTTLE, 9)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Expert", List.of(new ItemStack(Items.EMERALD, 5)), List.of(new ItemStack(Items.ENDER_PEARL))),

                    new Trade("Master", List.of(new ItemStack(Items.NETHER_WART, 22)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Master", List.of(new ItemStack(Items.EMERALD, 3)), List.of(new ItemStack(Items.EXPERIENCE_BOTTLE)))
            )),


            Map.entry("farmer", List.of(

                    new Trade("Novice", List.of(new ItemStack(Items.WHEAT, 20)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Novice", List.of(new ItemStack(Items.POTATO, 26)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Novice", List.of(new ItemStack(Items.CARROT, 22)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Novice", List.of(new ItemStack(Items.BEETROOT, 15)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Novice", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.BREAD, 6))),

                    new Trade("Apprentice", List.of(new ItemStack(Items.PUMPKIN, 6)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Apprentice", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.PUMPKIN_PIE, 4))),
                    new Trade("Apprentice", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.APPLE, 4))),

                    new Trade("Journeyman", List.of(new ItemStack(Items.MELON, 4)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Journeyman", List.of(new ItemStack(Items.EMERALD, 3)), List.of(new ItemStack(Items.COOKIE, 18))),

                    new Trade("Expert", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.SUSPICIOUS_STEW))),
                    new Trade("Expert", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.SUSPICIOUS_STEW))),
                    new Trade("Expert", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.SUSPICIOUS_STEW))),
                    new Trade("Expert", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.SUSPICIOUS_STEW))),
                    new Trade("Expert", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.SUSPICIOUS_STEW))),
                    new Trade("Expert", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.SUSPICIOUS_STEW))),
                    new Trade("Expert", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.CAKE))),

                    new Trade("Master", List.of(new ItemStack(Items.EMERALD, 3)), List.of(new ItemStack(Items.GOLDEN_CARROT, 3))),
                    new Trade("Master", List.of(new ItemStack(Items.EMERALD, 4)), List.of(new ItemStack(Items.GLISTERING_MELON_SLICE, 3)))
            )),

            Map.entry("fisherman", List.of(

                    new Trade("Novice", List.of(new ItemStack(Items.STRING, 20)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Novice", List.of(new ItemStack(Items.COAL, 10)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Novice", List.of(new ItemStack(Items.EMERALD, 3)), List.of(new ItemStack(Items.COD_BUCKET))),
                    new Trade("Novice", List.of(new ItemStack(Items.COD, 6), new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.COOKED_COD, 6))),

                    new Trade("Apprentice", List.of(new ItemStack(Items.COD, 15)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Apprentice", List.of(new ItemStack(Items.EMERALD, 2)), List.of(new ItemStack(Items.CAMPFIRE))),
                    new Trade("Apprentice", List.of(new ItemStack(Items.SALMON, 6), new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.COOKED_SALMON, 6))),

                    new Trade("Journeyman", List.of(new ItemStack(Items.SALMON, 13)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Journeyman", List.of(new ItemStack(Items.EMERALD, 8)), List.of(enchantedGlint(new ItemStack(Items.FISHING_ROD)))),

                    new Trade("Expert", List.of(new ItemStack(Items.TROPICAL_FISH, 6)), List.of(new ItemStack(Items.EMERALD))),

                    new Trade("Master", List.of(new ItemStack(Items.PUFFERFISH, 4)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Master", List.of(new ItemStack(Items.OAK_BOAT)), List.of(new ItemStack(Items.EMERALD)))
            )),


            Map.entry("fletcher", List.of(

                    new Trade("Novice", List.of(new ItemStack(Items.STICK, 32)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Novice", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.ARROW, 16))),
                    new Trade("Novice", List.of(new ItemStack(Items.GRAVEL, 10), new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.FLINT, 10))),

                    new Trade("Apprentice", List.of(new ItemStack(Items.FLINT, 26)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Apprentice", List.of(new ItemStack(Items.EMERALD, 2)), List.of(new ItemStack(Items.BOW))),

                    new Trade("Journeyman", List.of(new ItemStack(Items.STRING, 14)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Journeyman", List.of(new ItemStack(Items.EMERALD, 3)), List.of(new ItemStack(Items.CROSSBOW))),

                    new Trade("Expert", List.of(new ItemStack(Items.FEATHER, 24)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Expert", List.of(new ItemStack(Items.EMERALD, 7)), List.of(enchantedGlint(new ItemStack(Items.BOW)))),

                    new Trade("Master", List.of(new ItemStack(Items.TRIPWIRE_HOOK, 8)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Master", List.of(new ItemStack(Items.EMERALD, 8)), List.of(enchantedGlint(new ItemStack(Items.CROSSBOW)))),
                    new Trade("Master", List.of(new ItemStack(Items.EMERALD, 2), new ItemStack(Items.ARROW, 5)), List.of(new ItemStack(Items.TIPPED_ARROW, 5)))
            )),


            Map.entry("leatherworker", List.of(

                    new Trade("Novice", List.of(new ItemStack(Items.LEATHER, 6)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Novice", List.of(new ItemStack(Items.EMERALD, 3)), List.of(new ItemStack(Items.LEATHER_LEGGINGS))),
                    new Trade("Novice", List.of(new ItemStack(Items.EMERALD, 7)), List.of(new ItemStack(Items.LEATHER_CHESTPLATE))),

                    new Trade("Apprentice", List.of(new ItemStack(Items.FLINT, 26)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Apprentice", List.of(new ItemStack(Items.EMERALD, 5)), List.of(new ItemStack(Items.LEATHER_HELMET))),
                    new Trade("Apprentice", List.of(new ItemStack(Items.EMERALD, 4)), List.of(new ItemStack(Items.LEATHER_BOOTS))),

                    new Trade("Journeyman", List.of(new ItemStack(Items.RABBIT_HIDE, 9)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Journeyman", List.of(new ItemStack(Items.EMERALD, 7)), List.of(new ItemStack(Items.LEATHER_CHESTPLATE))),

                    new Trade("Expert", List.of(new ItemStack(Items.TURTLE_SCUTE, 4)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Expert", List.of(new ItemStack(Items.EMERALD, 6)), List.of(new ItemStack(Items.LEATHER_HORSE_ARMOR))),

                    new Trade("Master", List.of(new ItemStack(Items.EMERALD, 6)), List.of(new ItemStack(Items.LEATHER_HELMET))),
                    new Trade("Master", List.of(new ItemStack(Items.EMERALD, 6)), List.of(new ItemStack(Items.SADDLE)))
            )),

            Map.entry("librarian", List.of(

                    new Trade("Novice", List.of(new ItemStack(Items.PAPER, 24)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Novice", List.of(new ItemStack(Items.EMERALD, 9)), List.of(new ItemStack(Items.BOOKSHELF))),
                    new Trade("Novice", List.of(new ItemStack(Items.EMERALD, 5), new ItemStack(Items.BOOK)), List.of(new ItemStack(Items.ENCHANTED_BOOK))),

                    new Trade("Apprentice", List.of(new ItemStack(Items.BOOK, 4)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Apprentice", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.LANTERN))),
                    new Trade("Apprentice", List.of(new ItemStack(Items.EMERALD, 5), new ItemStack(Items.BOOK)), List.of(new ItemStack(Items.ENCHANTED_BOOK))),

                    new Trade("Journeyman", List.of(new ItemStack(Items.INK_SAC, 5)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Journeyman", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.GLASS, 4))),
                    new Trade("Journeyman", List.of(new ItemStack(Items.EMERALD, 5), new ItemStack(Items.BOOK)), List.of(new ItemStack(Items.ENCHANTED_BOOK))),

                    new Trade("Expert", List.of(new ItemStack(Items.WRITABLE_BOOK, 2)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Expert", List.of(new ItemStack(Items.EMERALD, 4)), List.of(new ItemStack(Items.COMPASS))),
                    new Trade("Expert", List.of(new ItemStack(Items.EMERALD, 5)), List.of(new ItemStack(Items.CLOCK))),
                    new Trade("Expert", List.of(new ItemStack(Items.EMERALD, 5), new ItemStack(Items.BOOK)), List.of(new ItemStack(Items.ENCHANTED_BOOK))),

                    new Trade("Master", List.of(new ItemStack(Items.EMERALD, 20)), List.of(new ItemStack(Items.NAME_TAG)))
            )),


            Map.entry("mason", List.of(

                    new Trade("Novice", List.of(new ItemStack(Items.CLAY_BALL, 10)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Novice", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.BRICK, 10))),

                    new Trade("Apprentice", List.of(new ItemStack(Items.STONE, 20)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Apprentice", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.CHISELED_STONE_BRICKS, 4))),

                    new Trade("Journeyman", List.of(new ItemStack(Items.GRANITE, 16)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Journeyman", List.of(new ItemStack(Items.ANDESITE, 16)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Journeyman", List.of(new ItemStack(Items.DIORITE, 16)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Journeyman", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.DRIPSTONE_BLOCK, 4))),
                    new Trade("Journeyman", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.POLISHED_ANDESITE, 4))),
                    new Trade("Journeyman", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.POLISHED_DIORITE, 4))),
                    new Trade("Journeyman", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.POLISHED_GRANITE, 4))),

                    new Trade("Expert", List.of(new ItemStack(Items.QUARTZ, 12)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Expert", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.WHITE_TERRACOTTA))),
                    new Trade("Expert", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.WHITE_GLAZED_TERRACOTTA))),

                    new Trade("Master", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.QUARTZ_PILLAR))),
                    new Trade("Master", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.QUARTZ_BLOCK)))
            )),


            Map.entry("shepherd", List.of(

                    new Trade("Novice", List.of(new ItemStack(Items.WHITE_WOOL, 18)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Novice", List.of(new ItemStack(Items.BROWN_WOOL, 18)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Novice", List.of(new ItemStack(Items.BLACK_WOOL, 18)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Novice", List.of(new ItemStack(Items.GRAY_WOOL, 18)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Novice", List.of(new ItemStack(Items.EMERALD, 2)), List.of(new ItemStack(Items.SHEARS))),

                    new Trade("Apprentice", List.of(new ItemStack(Items.WHITE_DYE, 12)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Apprentice", List.of(new ItemStack(Items.GRAY_DYE, 12)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Apprentice", List.of(new ItemStack(Items.BLACK_DYE, 12)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Apprentice", List.of(new ItemStack(Items.LIGHT_BLUE_DYE, 12)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Apprentice", List.of(new ItemStack(Items.LIME_DYE, 12)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Apprentice", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.WHITE_WOOL))),
                    new Trade("Apprentice", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.WHITE_CARPET, 4))),

                    new Trade("Journeyman", List.of(new ItemStack(Items.YELLOW_DYE, 12)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Journeyman", List.of(new ItemStack(Items.LIGHT_GRAY_DYE, 12)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Journeyman", List.of(new ItemStack(Items.ORANGE_DYE, 12)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Journeyman", List.of(new ItemStack(Items.RED_DYE, 12)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Journeyman", List.of(new ItemStack(Items.PINK_DYE, 12)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Journeyman", List.of(new ItemStack(Items.EMERALD, 3)), List.of(new ItemStack(Items.WHITE_BED))),

                    new Trade("Expert", List.of(new ItemStack(Items.BROWN_DYE, 12)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Expert", List.of(new ItemStack(Items.PURPLE_DYE, 12)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Expert", List.of(new ItemStack(Items.BLUE_DYE, 12)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Expert", List.of(new ItemStack(Items.GREEN_DYE, 12)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Expert", List.of(new ItemStack(Items.MAGENTA_DYE, 12)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Expert", List.of(new ItemStack(Items.CYAN_DYE, 12)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Expert", List.of(new ItemStack(Items.EMERALD, 3)), List.of(new ItemStack(Items.WHITE_BANNER))),

                    new Trade("Master", List.of(new ItemStack(Items.EMERALD, 2)), List.of(new ItemStack(Items.PAINTING, 3)))
            )),


            Map.entry("toolsmith", List.of(

                    new Trade("Novice", List.of(new ItemStack(Items.COAL, 15)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Novice", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.STONE_AXE))),
                    new Trade("Novice", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.STONE_SHOVEL))),
                    new Trade("Novice", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.STONE_PICKAXE))),
                    new Trade("Novice", List.of(new ItemStack(Items.EMERALD)), List.of(new ItemStack(Items.STONE_HOE))),

                    new Trade("Apprentice", List.of(new ItemStack(Items.IRON_INGOT, 4)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Apprentice", List.of(new ItemStack(Items.EMERALD, 36)), List.of(new ItemStack(Items.BELL))),

                    new Trade("Journeyman", List.of(new ItemStack(Items.FLINT, 30)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Journeyman", List.of(new ItemStack(Items.EMERALD, 6)), List.of(enchantedGlint(new ItemStack(Items.IRON_AXE)))),
                    new Trade("Journeyman", List.of(new ItemStack(Items.EMERALD, 7)), List.of(enchantedGlint(new ItemStack(Items.IRON_SHOVEL)))),
                    new Trade("Journeyman", List.of(new ItemStack(Items.EMERALD, 8)), List.of(enchantedGlint(new ItemStack(Items.IRON_PICKAXE)))),
                    new Trade("Journeyman", List.of(new ItemStack(Items.EMERALD, 4)), List.of(enchantedGlint(new ItemStack(Items.DIAMOND_HOE)))),

                    new Trade("Expert", List.of(new ItemStack(Items.DIAMOND)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Expert", List.of(new ItemStack(Items.EMERALD, 17)), List.of(enchantedGlint(new ItemStack(Items.DIAMOND_AXE)))),
                    new Trade("Expert", List.of(new ItemStack(Items.EMERALD, 10)), List.of(enchantedGlint(new ItemStack(Items.DIAMOND_SHOVEL)))),

                    new Trade("Master", List.of(new ItemStack(Items.EMERALD, 18)), List.of(enchantedGlint(new ItemStack(Items.DIAMOND_PICKAXE))))
            )),
            Map.entry("weaponsmith", List.of(

                    new Trade("Novice", List.of(new ItemStack(Items.COAL, 15)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Novice", List.of(new ItemStack(Items.EMERALD, 3)), List.of(new ItemStack(Items.IRON_AXE))),
                    new Trade("Novice", List.of(new ItemStack(Items.EMERALD, 7)), List.of(enchantedGlint(new ItemStack(Items.IRON_SWORD)))),

                    new Trade("Apprentice", List.of(new ItemStack(Items.IRON_INGOT, 4)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Apprentice", List.of(new ItemStack(Items.EMERALD, 36)), List.of(new ItemStack(Items.BELL))),

                    new Trade("Journeyman", List.of(new ItemStack(Items.FLINT, 24)), List.of(new ItemStack(Items.EMERALD))),
                    new Trade("Journeyman", List.of(new ItemStack(Items.DIAMOND)), List.of(new ItemStack(Items.EMERALD))),

                    new Trade("Expert", List.of(new ItemStack(Items.EMERALD, 17)), List.of(enchantedGlint(new ItemStack(Items.DIAMOND_AXE)))),

                    new Trade("Master", List.of(new ItemStack(Items.EMERALD, 13)), List.of(enchantedGlint(new ItemStack(Items.DIAMOND_SWORD))))
            ))

    );


    public static List<Trade> getTradesForVillager(String villagerName) {
        return TRADES.getOrDefault(villagerName.toLowerCase(), List.of());
    }

    public static ItemStack enchantedGlint(ItemStack stack) {
        stack.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);
        return stack;
    }
}


