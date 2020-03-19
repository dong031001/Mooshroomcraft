package com.enigtech.mooshroomcraft.recipe.crafting;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;

public class RealShapedRecipe implements ICraftingRecipe {

    private static ArrayList<RealShapedRecipe> recipes = new ArrayList<>();

    public static ArrayList<RealShapedRecipe> getRecipes() {
        return recipes;
    }

    public static void register(RealShapedRecipe recipe){
        recipes.add(recipe);
    }

    Ingredient[][] ingredients;
    ItemStack output;
    ResourceLocation id;

    public Ingredient[][] getRecipe() {
        return ingredients;
    }

    public RealShapedRecipe(Ingredient[][] ingredients, ItemStack output, ResourceLocation id){
        this.ingredients = ingredients;
        this.output = output;
        this.id = id;
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        return matches(inv);
    }

    public boolean matches(CraftingInventory inv) {
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                Ingredient ingredient = ingredients[i][j];
                ItemStack[] acceptedItems = ingredient.getMatchingStacks();
                boolean accepted = false;
                for(ItemStack itemStack:acceptedItems) if (Container.areItemsAndTagsEqual(itemStack, inv.getStackInSlot(i * 3 + j))) accepted = true;
                if(!accepted) return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        return output.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return width==3&&height==3;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return null;
    }
}
