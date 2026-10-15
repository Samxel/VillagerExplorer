package com.samxel.villagerexplorer.villager;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.Items;

import java.util.Map;

public enum Profession {
    FARMER("Farmer", VillagerProfession.FARMER,
            JobSiteRecipe.shaped(Items.COMPOSTER, Map.of('S', Items.OAK_SLAB), "S S", "S S", "SSS")),
    LIBRARIAN("Librarian", VillagerProfession.LIBRARIAN,
            JobSiteRecipe.shaped(Items.LECTERN, Map.of('S', Items.OAK_SLAB, 'B', Items.BOOKSHELF), "SSS", " B ", " S ")),
    CLERIC("Cleric", VillagerProfession.CLERIC,
            JobSiteRecipe.shaped(Items.BREWING_STAND, Map.of('R', Items.BLAZE_ROD, 'C', Items.COBBLESTONE), " R ", "CCC")),
    ARMORER("Armorer", VillagerProfession.ARMORER,
            JobSiteRecipe.shaped(Items.BLAST_FURNACE, Map.of('I', Items.IRON_INGOT, 'F', Items.FURNACE, 'S', Items.SMOOTH_STONE), "III", "IFI", "SSS")),
    BUTCHER("Butcher", VillagerProfession.BUTCHER,
            JobSiteRecipe.shaped(Items.SMOKER, Map.of('L', Items.OAK_LOG, 'F', Items.FURNACE), " L ", "LFL", " L ")),
    CARTOGRAPHER("Cartographer", VillagerProfession.CARTOGRAPHER,
            JobSiteRecipe.shaped(Items.CARTOGRAPHY_TABLE, Map.of('P', Items.PAPER, 'W', Items.OAK_PLANKS), "PP", "WW", "WW")),
    FISHERMAN("Fisherman", VillagerProfession.FISHERMAN,
            JobSiteRecipe.shaped(Items.BARREL, Map.of('W', Items.OAK_PLANKS, 'S', Items.OAK_SLAB), "WSW", "W W", "WSW")),
    FLETCHER("Fletcher", VillagerProfession.FLETCHER,
            JobSiteRecipe.shaped(Items.FLETCHING_TABLE, Map.of('F', Items.FLINT, 'W', Items.OAK_PLANKS), "FF", "WW", "WW")),
    LEATHERWORKER("Leatherworker", VillagerProfession.LEATHERWORKER,
            JobSiteRecipe.shaped(Items.CAULDRON, Map.of('I', Items.IRON_INGOT), "I I", "I I", "III")),
    MASON("Mason", VillagerProfession.MASON,
            JobSiteRecipe.shaped(Items.STONECUTTER, Map.of('I', Items.IRON_INGOT, 'S', Items.STONE), " I ", "SSS")),
    SHEPHERD("Shepherd", VillagerProfession.SHEPHERD,
            JobSiteRecipe.shaped(Items.LOOM, Map.of('S', Items.STRING, 'W', Items.OAK_PLANKS), "SS", "WW")),
    TOOLSMITH("Toolsmith", VillagerProfession.TOOLSMITH,
            JobSiteRecipe.shaped(Items.SMITHING_TABLE, Map.of('I', Items.IRON_INGOT, 'W', Items.OAK_PLANKS), "II", "WW", "WW")),
    WEAPONSMITH("Weaponsmith", VillagerProfession.WEAPONSMITH,
            JobSiteRecipe.shaped(Items.GRINDSTONE, Map.of('S', Items.STICK, 'L', Items.STONE_SLAB, 'W', Items.OAK_PLANKS), "SLS", "W W"));

    private final String displayName;
    private final ResourceKey<VillagerProfession> key;
    private final JobSiteRecipe jobSite;

    Profession(String displayName, ResourceKey<VillagerProfession> key, JobSiteRecipe jobSite) {
        this.displayName = displayName;
        this.key = key;
        this.jobSite = jobSite;
    }

    public String displayName() {
        return this.displayName;
    }

    public ResourceKey<VillagerProfession> key() {
        return this.key;
    }

    public JobSiteRecipe jobSite() {
        return this.jobSite;
    }
}
