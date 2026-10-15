package com.samxel.villagerexplorer.villager;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.Nullable;

import java.util.Map;

public enum Merchant {
    FARMER("farmer", VillagerProfession.FARMER,
            JobSiteRecipe.shaped(Items.COMPOSTER, Map.of('S', Items.OAK_SLAB), "S S", "S S", "SSS")),
    LIBRARIAN("librarian", VillagerProfession.LIBRARIAN,
            JobSiteRecipe.shaped(Items.LECTERN, Map.of('S', Items.OAK_SLAB, 'B', Items.BOOKSHELF), "SSS", " B ", " S ")),
    CLERIC("cleric", VillagerProfession.CLERIC,
            JobSiteRecipe.shaped(Items.BREWING_STAND, Map.of('R', Items.BLAZE_ROD, 'C', Items.COBBLESTONE), " R ", "CCC")),
    ARMORER("armorer", VillagerProfession.ARMORER,
            JobSiteRecipe.shaped(Items.BLAST_FURNACE, Map.of('I', Items.IRON_INGOT, 'F', Items.FURNACE, 'S', Items.SMOOTH_STONE), "III", "IFI", "SSS")),
    BUTCHER("butcher", VillagerProfession.BUTCHER,
            JobSiteRecipe.shaped(Items.SMOKER, Map.of('L', Items.OAK_LOG, 'F', Items.FURNACE), " L ", "LFL", " L ")),
    CARTOGRAPHER("cartographer", VillagerProfession.CARTOGRAPHER,
            JobSiteRecipe.shaped(Items.CARTOGRAPHY_TABLE, Map.of('P', Items.PAPER, 'W', Items.OAK_PLANKS), "PP", "WW", "WW")),
    FISHERMAN("fisherman", VillagerProfession.FISHERMAN,
            JobSiteRecipe.shaped(Items.BARREL, Map.of('W', Items.OAK_PLANKS, 'S', Items.OAK_SLAB), "WSW", "W W", "WSW")),
    FLETCHER("fletcher", VillagerProfession.FLETCHER,
            JobSiteRecipe.shaped(Items.FLETCHING_TABLE, Map.of('F', Items.FLINT, 'W', Items.OAK_PLANKS), "FF", "WW", "WW")),
    LEATHERWORKER("leatherworker", VillagerProfession.LEATHERWORKER,
            JobSiteRecipe.shaped(Items.CAULDRON, Map.of('I', Items.IRON_INGOT), "I I", "I I", "III")),
    MASON("mason", VillagerProfession.MASON,
            JobSiteRecipe.shaped(Items.STONECUTTER, Map.of('I', Items.IRON_INGOT, 'S', Items.STONE), " I ", "SSS")),
    SHEPHERD("shepherd", VillagerProfession.SHEPHERD,
            JobSiteRecipe.shaped(Items.LOOM, Map.of('S', Items.STRING, 'W', Items.OAK_PLANKS), "SS", "WW")),
    TOOLSMITH("toolsmith", VillagerProfession.TOOLSMITH,
            JobSiteRecipe.shaped(Items.SMITHING_TABLE, Map.of('I', Items.IRON_INGOT, 'W', Items.OAK_PLANKS), "II", "WW", "WW")),
    WEAPONSMITH("weaponsmith", VillagerProfession.WEAPONSMITH,
            JobSiteRecipe.shaped(Items.GRINDSTONE, Map.of('S', Items.STICK, 'L', Items.STONE_SLAB, 'W', Items.OAK_PLANKS), "SLS", "W W")),
    WANDERING_TRADER("wandering_trader", null, null);

    private final String id;
    private final @Nullable ResourceKey<VillagerProfession> profession;
    private final @Nullable JobSiteRecipe jobSite;

    Merchant(String id, @Nullable ResourceKey<VillagerProfession> profession, @Nullable JobSiteRecipe jobSite) {
        this.id = id;
        this.profession = profession;
        this.jobSite = jobSite;
    }

    public String id() {
        return this.id;
    }

    public String displayName() {
        String key = this.profession == null ? "entity.minecraft." + this.id : "entity.minecraft.villager." + this.id;
        return Component.translatable(key).getString();
    }

    public static @Nullable Merchant byProfession(ResourceKey<VillagerProfession> profession) {
        for (Merchant merchant : values()) {
            if (profession.equals(merchant.profession)) {
                return merchant;
            }
        }
        return null;
    }

    public static @Nullable Merchant byId(String id) {
        for (Merchant merchant : values()) {
            if (merchant.id.equals(id)) {
                return merchant;
            }
        }
        return null;
    }

    public @Nullable ResourceKey<VillagerProfession> profession() {
        return this.profession;
    }

    public @Nullable JobSiteRecipe jobSite() {
        return this.jobSite;
    }

    public boolean isVillager() {
        return this.profession != null;
    }
}
