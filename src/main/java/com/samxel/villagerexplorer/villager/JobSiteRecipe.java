package com.samxel.villagerexplorer.villager;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record JobSiteRecipe(List<Ingredient> ingredients, ItemStack result) {

    public static JobSiteRecipe shaped(Item result, Map<Character, Item> key, String... rows) {
        List<Ingredient> ingredients = new ArrayList<>();
        for (int row = 0; row < rows.length; row++) {
            for (int col = 0; col < rows[row].length(); col++) {
                Item item = key.get(rows[row].charAt(col));
                if (item != null) {
                    ingredients.add(new Ingredient(new ItemStack(item), row, col));
                }
            }
        }
        return new JobSiteRecipe(List.copyOf(ingredients), new ItemStack(result));
    }

    public record Ingredient(ItemStack stack, int row, int col) {
    }
}
