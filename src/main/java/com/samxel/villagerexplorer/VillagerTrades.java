package com.samxel.villagerexplorer;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.SequencedSet;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public class VillagerTrades {
    private static ItemStack namedTerracotta() {
        ItemStack stack = new ItemStack(Items.GLAZED_TERRACOTTA.pick(DyeColor.WHITE));
        stack.set(DataComponents.CUSTOM_NAME, Component.literal("Any Color Glazed Terracota"));
        return stack;
    }
    private static ItemStack namedTerracottar() {
        ItemStack stack = new ItemStack(Items.TERRACOTTA);
        stack.set(DataComponents.CUSTOM_NAME, Component.literal("Any Color Terracota"));
        return stack;
    }
    private static ItemStack Emeraldcount() {
        ItemStack stack = new ItemStack(Items.EMERALD, 88);
        stack.set(DataComponents.CUSTOM_NAME, Component.literal("Price Is Dependend on what variant of the item it is"));
        return stack;
    }
    private static ItemStack Stef() {
        ItemStack stack = new ItemStack(Items.SUSPICIOUS_STEW);
        stack.set(DataComponents.CUSTOM_NAME, Component.literal("Can give Blindness (00:06), Jump Boost (00:08), Night Vision (00:05), Poison (00:14), Saturation (00:00.350), or Weakness (00:07)"));
        return stack;
    }
    private static ItemStack exp() {
        ItemStack stack = new ItemStack(Items.FILLED_MAP);
        stack.set(DataComponents.CUSTOM_NAME, Component.literal("Explorer Map"));
        stack.set(
                DataComponents.LORE,
                new ItemLore(List.of(
                        Component.literal("Villager Type - Structures"),
                        Component.literal("Desert - Savanna Village, Plains Village, Jungle Temple"),
                        Component.literal("Jungle - Savanna Village, Desert Village, Swamp Hut"),
                        Component.literal("Plains - Savanna Village, Taiga Village"),
                        Component.literal("Savanna - Desert Village, Plains Village, Jungle Temple"),
                        Component.literal("Snowy - Plains Village, Taiga Village, Swamp Hut"),
                        Component.literal("Taiga - Plains Village, Snowy Village, Swamp Hut"),
                        Component.literal("Swamp - Snowy Village, Taiga Village, Jungle Temple")
                )));
        return stack;
    }
    private static ItemStack oexp() {
        ItemStack stack = new ItemStack(Items.FILLED_MAP);
        stack.set(DataComponents.CUSTOM_NAME, Component.literal("Ocean Explorer Map"));
        return stack;
    }
    private static ItemStack texp() {
        ItemStack stack = new ItemStack(Items.FILLED_MAP);
        stack.set(DataComponents.CUSTOM_NAME, Component.literal("Trial Explorer Map"));
        return stack;
    }
    private static ItemStack mexp() {
        ItemStack stack = new ItemStack(Items.FILLED_MAP);
        stack.set(DataComponents.CUSTOM_NAME, Component.literal("Woodland Explorer Map"));
        return stack;
    }
    private static ItemStack bann() {
        ItemStack stack = new ItemStack(Items.BANNER.pick(DyeColor.WHITE));
        stack.set(DataComponents.CUSTOM_NAME, Component.literal("Any Color Banner"));
        return stack;
    }
    private static ItemStack ban() {
        ItemStack stack = new ItemStack(Items.BANNER.pick(DyeColor.WHITE));
        stack.set(DataComponents.CUSTOM_NAME, Component.literal("Any Color Banner"));
        stack.set(
                DataComponents.LORE,
                new ItemLore(List.of(
                        Component.literal("Villager Type - Banner Color"),
                        Component.literal("Desert - Cyan, Gray, Green, Lime, Orange"),
                        Component.literal("Jungle - Brown, Green, Yellow"),
                        Component.literal("Plains - Brown, Pink, White, Yellow"),
                        Component.literal("Savanna - Green, Magenta, Orange, Red"),
                        Component.literal("Snowy - Blue, Cyan, Light Blue, Red, White"),
                        Component.literal("Taiga - Blue, Lime, Pink, Purple"),
                        Component.literal("Swamp - Black, Light Blue, Purple")
                )));

        return stack;
    }
    private static ItemStack boak() {
        ItemStack stack = new ItemStack(Items.OAK_BOAT);
        stack.set(
                DataComponents.CUSTOM_NAME,
                Component.literal("Boat wood depends on Villager Biome")
        );

        stack.set(
                DataComponents.LORE,
                new ItemLore(List.of(
                        Component.literal("Villager Type - Wood Type"),
                        Component.literal("Desert, Jungle - Jungle Wood"),
                        Component.literal("Plains - Oak Wood"),
                        Component.literal("Savanna - Acacia Wood"),
                        Component.literal("Snowy, Taiga - Spruce Wood"),
                        Component.literal("Swamp - Dark Oak Wood")
                ))
        );

        return stack;
    }
    private static ItemStack Arrowt() {
        ItemStack stack = new ItemStack(Items.TIPPED_ARROW);
        stack.set(DataComponents.CUSTOM_NAME, Component.literal("Any Craftable Tipped Arrow"));
        return stack;
    }
    private static ItemStack wool() {
        ItemStack stack = new ItemStack(Items.WOOL.pick(DyeColor.WHITE));
        stack.set(DataComponents.CUSTOM_NAME, Component.literal("Any Color Wool"));
        return stack;
    }
    private static ItemStack carpet() {
        ItemStack stack = new ItemStack(Items.CARPET.pick(DyeColor.WHITE), 4);
        stack.set(DataComponents.CUSTOM_NAME, Component.literal("Any Color Carpet"));
        return stack;
    }
    private static ItemStack bed() {
        ItemStack stack = new ItemStack(Items.BED.pick(DyeColor.WHITE));
        stack.set(DataComponents.CUSTOM_NAME, Component.literal("Any Color Bed"));
        return stack;
    }
    public static final Map<String, List<Trade>> TRADES =
            Map.ofEntries(

                    Map.entry(
                            "armorer",
                            List.of(
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.COAL, 15)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.EMERALD, 5)),
                                            List.of(new ItemStack(Items.IRON_HELMET))
                                    ),
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.EMERALD, 9)),
                                            List.of(new ItemStack(Items.IRON_CHESTPLATE))
                                    ),
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.EMERALD, 7)),
                                            List.of(new ItemStack(Items.IRON_LEGGINGS))
                                    ),
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.EMERALD, 4)),
                                            List.of(new ItemStack(Items.IRON_BOOTS))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.IRON_INGOT, 4)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.EMERALD, 36)),
                                            List.of(new ItemStack(Items.BELL))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.EMERALD, 3)),
                                            List.of(new ItemStack(Items.CHAINMAIL_LEGGINGS))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.EMERALD)),
                                            List.of(new ItemStack(Items.CHAINMAIL_BOOTS))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.LAVA_BUCKET)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.DIAMOND)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.EMERALD, 5)),
                                            List.of(new ItemStack(Items.CHAINMAIL_HELMET))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.EMERALD, 4)),
                                            List.of(new ItemStack(Items.CHAINMAIL_CHESTPLATE))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.EMERALD, 5)),
                                            List.of(new ItemStack(Items.SHIELD))
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(Emeraldcount()),
                                            List.of(
                                                    markGlint(
                                                            new ItemStack(
                                                                    Items.DIAMOND_LEGGINGS
                                                            )
                                                    )
                                            )
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(Emeraldcount()),
                                            List.of(
                                                    markGlint(
                                                            new ItemStack(
                                                                    Items.DIAMOND_BOOTS
                                                            )
                                                    )
                                            )
                                    ),
                                    new Trade(
                                            "Master",
                                            List.of(Emeraldcount()),
                                            List.of(
                                                    markGlint(
                                                            new ItemStack(
                                                                    Items.DIAMOND_HELMET
                                                            )
                                                    )
                                            )
                                    ),
                                    new Trade(
                                            "Master",
                                            List.of(Emeraldcount()),
                                            List.of(
                                                    markGlint(
                                                            new ItemStack(
                                                                    Items.DIAMOND_CHESTPLATE
                                                            )
                                                    )
                                            )
                                    )
                            )
                    ),


                    Map.entry(
                            "butcher",
                            List.of(
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.CHICKEN, 14)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.RABBIT, 4)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.PORKCHOP, 7)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.EMERALD)),
                                            List.of(new ItemStack(Items.RABBIT_STEW))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.COAL, 15)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.EMERALD)),
                                            List.of(
                                                    new ItemStack(Items.COOKED_CHICKEN, 8)
                                            )
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.EMERALD)),
                                            List.of(
                                                    new ItemStack(Items.COOKED_PORKCHOP, 5)
                                            )
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.BEEF, 10)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.MUTTON, 7)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(
                                                    new ItemStack(Items.DRIED_KELP_BLOCK, 10)
                                            ),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Master",
                                            List.of(new ItemStack(Items.SWEET_BERRIES, 10)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    )
                            )
                    ),


                    Map.entry(
                            "cartographer",
                            List.of(
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.PAPER, 24)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.EMERALD, 7)),
                                            List.of(new ItemStack(Items.MAP))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.GLASS_PANE, 11)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(
                                                    new ItemStack(Items.EMERALD, 8),
                                                    new ItemStack(Items.COMPASS)
                                            ),
                                            List.of(exp())
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(
                                                    new ItemStack(Items.EMERALD, 8),
                                                    new ItemStack(Items.COMPASS)
                                            ),
                                            List.of(exp())
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.COMPASS)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(
                                                    new ItemStack(Items.EMERALD, 13),
                                                    new ItemStack(Items.COMPASS)
                                            ),
                                            List.of(oexp())
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(
                                                    new ItemStack(Items.EMERALD, 12),
                                                    new ItemStack(Items.COMPASS)
                                            ),
                                            List.of(texp())
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(new ItemStack(Items.EMERALD, 7)),
                                            List.of(new ItemStack(Items.ITEM_FRAME))
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(new ItemStack(Items.EMERALD, 3)),
                                            List.of(ban())
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(new ItemStack(Items.EMERALD, 3)),
                                            List.of(ban())
                                    ),
                                    new Trade(
                                            "Master",
                                            List.of(new ItemStack(Items.EMERALD, 8)),
                                            List.of(
                                                    new ItemStack(
                                                            Items.GLOBE_BANNER_PATTERN
                                                    )
                                            )
                                    ),
                                    new Trade(
                                            "Master",
                                            List.of(
                                                    new ItemStack(Items.EMERALD, 14),
                                                    new ItemStack(Items.COMPASS)
                                            ),
                                            List.of(mexp())
                                    )
                            )
                    ),


                    Map.entry(
                            "cleric",
                            List.of(
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.ROTTEN_FLESH, 32)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.EMERALD)),
                                            List.of(new ItemStack(Items.REDSTONE, 2))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.GOLD_INGOT, 3)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.EMERALD)),
                                            List.of(new ItemStack(Items.LAPIS_LAZULI))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.RABBIT_FOOT, 2)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.EMERALD, 4)),
                                            List.of(new ItemStack(Items.GLOWSTONE))
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(new ItemStack(Items.TURTLE_SCUTE, 4)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(new ItemStack(Items.GLASS_BOTTLE, 9)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(new ItemStack(Items.EMERALD, 5)),
                                            List.of(new ItemStack(Items.ENDER_PEARL))
                                    ),
                                    new Trade(
                                            "Master",
                                            List.of(new ItemStack(Items.NETHER_WART, 22)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Master",
                                            List.of(new ItemStack(Items.EMERALD, 3)),
                                            List.of(new ItemStack(Items.EXPERIENCE_BOTTLE))
                                    )
                            )
                    ),


                    Map.entry(
                            "farmer",
                            List.of(
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.WHEAT, 20)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.POTATO, 26)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.CARROT, 22)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.BEETROOT, 15)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.EMERALD)),
                                            List.of(new ItemStack(Items.BREAD, 6))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.PUMPKIN, 6)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.EMERALD)),
                                            List.of(new ItemStack(Items.PUMPKIN_PIE, 4))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.EMERALD)),
                                            List.of(new ItemStack(Items.APPLE, 4))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.MELON, 4)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.EMERALD, 3)),
                                            List.of(new ItemStack(Items.COOKIE, 18))
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(new ItemStack(Items.EMERALD)),
                                            List.of(Stef())
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(new ItemStack(Items.EMERALD)),
                                            List.of(new ItemStack(Items.CAKE))
                                    ),
                                    new Trade(
                                            "Master",
                                            List.of(new ItemStack(Items.EMERALD, 3)),
                                            List.of(
                                                    new ItemStack(Items.GOLDEN_CARROT, 3)
                                            )
                                    ),
                                    new Trade(
                                            "Master",
                                            List.of(new ItemStack(Items.EMERALD, 4)),
                                            List.of(
                                                    new ItemStack(
                                                            Items.GLISTERING_MELON_SLICE,
                                                            3
                                                    )
                                            )
                                    )
                            )
                    ),


                    Map.entry(
                            "fisherman",
                            List.of(
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.STRING, 20)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.COAL, 10)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.EMERALD, 3)),
                                            List.of(new ItemStack(Items.COD_BUCKET))
                                    ),
                                    new Trade(
                                            "Novice",
                                            List.of(
                                                    new ItemStack(Items.COD, 6),
                                                    new ItemStack(Items.EMERALD)
                                            ),
                                            List.of(new ItemStack(Items.COOKED_COD, 6))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.COD, 15)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.EMERALD, 2)),
                                            List.of(new ItemStack(Items.CAMPFIRE))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(
                                                    new ItemStack(Items.SALMON, 6),
                                                    new ItemStack(Items.EMERALD)
                                            ),
                                            List.of(new ItemStack(Items.COOKED_SALMON, 6))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.SALMON, 13)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(Emeraldcount()),
                                            List.of(
                                                    markGlint(
                                                            new ItemStack(
                                                                    Items.FISHING_ROD
                                                            )
                                                    )
                                            )
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(new ItemStack(Items.TROPICAL_FISH, 6)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Master",
                                            List.of(new ItemStack(Items.PUFFERFISH, 4)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Master",
                                            List.of(boak()),
                                            List.of(new ItemStack(Items.EMERALD))
                                    )
                            )
                    ),


                    Map.entry(
                            "fletcher",
                            List.of(
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.STICK, 32)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.EMERALD)),
                                            List.of(new ItemStack(Items.ARROW, 16))
                                    ),
                                    new Trade(
                                            "Novice",
                                            List.of(
                                                    new ItemStack(Items.GRAVEL, 10),
                                                    new ItemStack(Items.EMERALD)
                                            ),
                                            List.of(new ItemStack(Items.FLINT, 10))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.FLINT, 26)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.EMERALD, 2)),
                                            List.of(new ItemStack(Items.BOW))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.STRING, 14)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.EMERALD, 3)),
                                            List.of(new ItemStack(Items.CROSSBOW))
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(new ItemStack(Items.FEATHER, 24)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(Emeraldcount()),
                                            List.of(
                                                    markGlint(
                                                            new ItemStack(Items.BOW)
                                                    )
                                            )
                                    ),
                                    new Trade(
                                            "Master",
                                            List.of(new ItemStack(Items.TRIPWIRE_HOOK, 8)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Master",
                                            List.of(Emeraldcount()),
                                            List.of(
                                                    markGlint(
                                                            new ItemStack(Items.CROSSBOW)
                                                    )
                                            )
                                    ),
                                    new Trade(
                                            "Master",
                                            List.of(
                                                    new ItemStack(Items.EMERALD, 2),
                                                    new ItemStack(Items.ARROW, 5)
                                            ),
                                            List.of(Arrowt())
                                    )
                            )
                    ),


                    Map.entry(
                            "leatherworker",
                            List.of(
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.LEATHER, 6)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.EMERALD, 3)),
                                            List.of(new ItemStack(Items.LEATHER_LEGGINGS))
                                    ),
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.EMERALD, 7)),
                                            List.of(new ItemStack(Items.LEATHER_CHESTPLATE))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.FLINT, 26)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.EMERALD, 5)),
                                            List.of(new ItemStack(Items.LEATHER_HELMET))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.EMERALD, 4)),
                                            List.of(new ItemStack(Items.LEATHER_BOOTS))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.RABBIT_HIDE, 9)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.EMERALD, 7)),
                                            List.of(new ItemStack(Items.LEATHER_CHESTPLATE))
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(new ItemStack(Items.TURTLE_SCUTE, 4)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(new ItemStack(Items.EMERALD, 6)),
                                            List.of(new ItemStack(Items.LEATHER_HORSE_ARMOR))
                                    ),
                                    new Trade(
                                            "Master",
                                            List.of(new ItemStack(Items.EMERALD, 6)),
                                            List.of(new ItemStack(Items.LEATHER_HELMET))
                                    ),
                                    new Trade(
                                            "Master",
                                            List.of(new ItemStack(Items.EMERALD, 6)),
                                            List.of(new ItemStack(Items.SADDLE))
                                    )
                            )
                    ),


                    Map.entry(
                            "librarian",
                            List.of(
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.PAPER, 24)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.EMERALD, 9)),
                                            List.of(new ItemStack(Items.BOOKSHELF))
                                    ),
                                    new Trade(
                                            "Novice",
                                            List.of(
                                                    Emeraldcount(),
                                                    new ItemStack(Items.BOOK)
                                            ),
                                            List.of(new ItemStack(Items.ENCHANTED_BOOK))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.BOOK, 4)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.EMERALD)),
                                            List.of(new ItemStack(Items.LANTERN))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(
                                                    Emeraldcount(),
                                                    new ItemStack(Items.BOOK)
                                            ),
                                            List.of(new ItemStack(Items.ENCHANTED_BOOK))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.INK_SAC, 5)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.EMERALD)),
                                            List.of(new ItemStack(Items.GLASS, 4))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(
                                                    Emeraldcount(),
                                                    new ItemStack(Items.BOOK)
                                            ),
                                            List.of(new ItemStack(Items.ENCHANTED_BOOK))
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(new ItemStack(Items.WRITABLE_BOOK, 1)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(new ItemStack(Items.EMERALD, 4)),
                                            List.of(new ItemStack(Items.COMPASS))
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(new ItemStack(Items.EMERALD, 5)),
                                            List.of(new ItemStack(Items.CLOCK))
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(
                                                    Emeraldcount(),
                                                    new ItemStack(Items.BOOK)
                                            ),
                                            List.of(new ItemStack(Items.ENCHANTED_BOOK))
                                    ),
                                    new Trade(
                                            "Master",
                                            List.of(new ItemStack(Items.EMERALD, 3)),
                                            List.of(new ItemStack(Items.DYED_CANDLE.pick(DyeColor.RED)))
                                    ),
                                    new Trade(
                                            "Master",
                                            List.of(new ItemStack(Items.EMERALD, 3)),
                                            List.of(new ItemStack(Items.DYED_CANDLE.pick(DyeColor.YELLOW)))
                                    )
                            )
                    ),


                    Map.entry(
                            "mason",
                            List.of(
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.CLAY_BALL, 10)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.EMERALD)),
                                            List.of(new ItemStack(Items.BRICK, 10))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.STONE, 20)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.EMERALD)),
                                            List.of(
                                                    new ItemStack(Items.CHISELED_STONE_BRICKS, 4)
                                            )
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.GRANITE, 16)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.ANDESITE, 16)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.DIORITE, 16)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.EMERALD)),
                                            List.of(new ItemStack(Items.DRIPSTONE_BLOCK, 4))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.EMERALD)),
                                            List.of(new ItemStack(Items.POLISHED_ANDESITE, 4))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.EMERALD)),
                                            List.of(new ItemStack(Items.POLISHED_DIORITE, 4))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.EMERALD)),
                                            List.of(new ItemStack(Items.POLISHED_GRANITE, 4))
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(new ItemStack(Items.QUARTZ, 12)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(new ItemStack(Items.EMERALD)),
                                            List.of(namedTerracottar())
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(new ItemStack(Items.EMERALD)),
                                            List.of(namedTerracotta())
                                    ),
                                    new Trade(
                                            "Master",
                                            List.of(new ItemStack(Items.EMERALD)),
                                            List.of(new ItemStack(Items.QUARTZ_PILLAR))
                                    ),
                                    new Trade(
                                            "Master",
                                            List.of(new ItemStack(Items.EMERALD)),
                                            List.of(new ItemStack(Items.QUARTZ_BLOCK))
                                    )
                            )
                    ),


                    Map.entry(
                            "shepherd",
                            List.of(
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.WOOL.pick(DyeColor.WHITE), 18)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.WOOL.pick(DyeColor.BROWN), 18)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.WOOL.pick(DyeColor.BLACK), 18)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.WOOL.pick(DyeColor.GRAY), 18)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.EMERALD, 2)),
                                            List.of(new ItemStack(Items.SHEARS))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.DYE.pick(DyeColor.WHITE), 12)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.DYE.pick(DyeColor.GRAY), 12)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.DYE.pick(DyeColor.BLACK), 12)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.DYE.pick(DyeColor.LIGHT_BLUE), 12)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.DYE.pick(DyeColor.LIME), 12)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.EMERALD)),
                                            List.of(wool())
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.EMERALD)),
                                            List.of(carpet())
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.DYE.pick(DyeColor.YELLOW), 12)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.DYE.pick(DyeColor.LIGHT_GRAY), 12)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.DYE.pick(DyeColor.ORANGE), 12)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.DYE.pick(DyeColor.RED), 12)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.DYE.pick(DyeColor.PINK), 12)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.EMERALD, 3)),
                                            List.of(bed())
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(new ItemStack(Items.DYE.pick(DyeColor.BROWN), 12)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(new ItemStack(Items.DYE.pick(DyeColor.PURPLE), 12)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(new ItemStack(Items.DYE.pick(DyeColor.BLUE), 12)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(new ItemStack(Items.DYE.pick(DyeColor.GREEN), 12)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(new ItemStack(Items.DYE.pick(DyeColor.GREEN), 12)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(new ItemStack(Items.DYE.pick(DyeColor.CYAN), 12)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(new ItemStack(Items.EMERALD, 3)),
                                            List.of(bann())
                                    ),
                                    new Trade(
                                            "Master",
                                            List.of(new ItemStack(Items.EMERALD, 2)),
                                            List.of(new ItemStack(Items.PAINTING, 3))
                                    )
                            )
                    ),


                    Map.entry(
                            "toolsmith",
                            List.of(
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.COAL, 15)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.EMERALD)),
                                            List.of(new ItemStack(Items.STONE_AXE))
                                    ),
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.EMERALD)),
                                            List.of(new ItemStack(Items.STONE_SHOVEL))
                                    ),
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.EMERALD)),
                                            List.of(new ItemStack(Items.STONE_PICKAXE))
                                    ),
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.EMERALD)),
                                            List.of(new ItemStack(Items.STONE_HOE))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.IRON_INGOT, 4)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.EMERALD, 36)),
                                            List.of(new ItemStack(Items.BELL))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.FLINT, 30)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(Emeraldcount()),
                                            List.of(
                                                    markGlint(
                                                            new ItemStack(Items.IRON_AXE)
                                                    )
                                            )
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(Emeraldcount()),
                                            List.of(
                                                    markGlint(
                                                            new ItemStack(Items.IRON_SHOVEL)
                                                    )
                                            )
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(Emeraldcount()),
                                            List.of(
                                                    markGlint(
                                                            new ItemStack(Items.IRON_PICKAXE)
                                                    )
                                            )
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.EMERALD, 4)),
                                            List.of(new ItemStack(Items.DIAMOND_HOE))
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(new ItemStack(Items.DIAMOND)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(Emeraldcount()),
                                            List.of(
                                                    markGlint(
                                                            new ItemStack(Items.DIAMOND_AXE)
                                                    )
                                            )
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(Emeraldcount()),
                                            List.of(
                                                    markGlint(
                                                            new ItemStack(
                                                                    Items.DIAMOND_SHOVEL
                                                            )
                                                    )
                                            )
                                    ),
                                    new Trade(
                                            "Master",
                                            List.of(Emeraldcount()),
                                            List.of(
                                                    markGlint(
                                                            new ItemStack(
                                                                    Items.DIAMOND_PICKAXE
                                                            )
                                                    )
                                            )
                                    )
                            )
                    ),


                    Map.entry(
                            "weaponsmith",
                            List.of(
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.COAL, 15)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Novice",
                                            List.of(new ItemStack(Items.EMERALD, 3)),
                                            List.of(new ItemStack(Items.IRON_AXE))
                                    ),
                                    new Trade(
                                            "Novice",
                                            List.of(Emeraldcount()),
                                            List.of(
                                                    markGlint(
                                                            new ItemStack(Items.IRON_SWORD)
                                                    )
                                            )
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.IRON_INGOT, 4)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Apprentice",
                                            List.of(new ItemStack(Items.EMERALD, 36)),
                                            List.of(new ItemStack(Items.BELL))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.FLINT, 24)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Journeyman",
                                            List.of(new ItemStack(Items.DIAMOND)),
                                            List.of(new ItemStack(Items.EMERALD))
                                    ),
                                    new Trade(
                                            "Expert",
                                            List.of(Emeraldcount()),
                                            List.of(
                                                    markGlint(
                                                            new ItemStack(Items.DIAMOND_AXE)
                                                    )
                                            )
                                    ),
                                    new Trade(
                                            "Master",
                                            List.of(Emeraldcount()),
                                            List.of(
                                                    markGlint(
                                                            new ItemStack(Items.DIAMOND_SWORD)
                                                    )
                                            )
                                    )
                            )
                    )
            );

    public static ItemStack markGlint(ItemStack stack) {
        stack.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
        return stack;
    }

    public static List<Trade> getTradesForVillager(String villagerName) {
        return TRADES.getOrDefault(villagerName.toLowerCase(), List.of());
    }

    public static ItemStack markGlint(
            ItemStack stack,
            HolderLookup.Provider registries
    ) {
        ItemEnchantments existing = stack.get(DataComponents.ENCHANTMENTS);
        ItemEnchantments.Mutable builder =
                new ItemEnchantments.Mutable(
                        existing != null ? existing : ItemEnchantments.EMPTY
                );


        ResourceKey<Enchantment> key = Enchantments.UNBREAKING;
        Holder<Enchantment> unbreakingEntry =
                registries.lookupOrThrow(Registries.ENCHANTMENT)
                        .get(key)
                        .orElseThrow();

        builder.set(unbreakingEntry, 1);
        stack.set(DataComponents.ENCHANTMENTS, builder.toImmutable());


        SequencedSet<DataComponentType<?>> hidden = new LinkedHashSet<>();
        hidden.add(DataComponents.ENCHANTMENTS);

        TooltipDisplay display = new TooltipDisplay(false, hidden);
        stack.set(DataComponents.TOOLTIP_DISPLAY, display);

        return stack;
    }

    public record Trade(String level, List<ItemStack> wanted, List<ItemStack> given) {
    }
}


