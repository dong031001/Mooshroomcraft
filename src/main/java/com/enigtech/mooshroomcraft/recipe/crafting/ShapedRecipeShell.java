package com.enigtech.mooshroomcraft.recipe.crafting;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ShapedRecipeShell implements ICraftingRecipe {

    ResourceLocation id;

    public ShapedRecipeShell(ResourceLocation recipeId) {
        this.id = recipeId;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        for(RealShapedRecipe recipe : RealShapedRecipe.getRecipes()){
            if(recipe.matches(inv, worldIn)) return true;
        }
        return false;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        for(RealShapedRecipe recipe : RealShapedRecipe.getRecipes()){
            if(recipe.matches(inv)) return recipe.getCraftingResult(inv);
        }
        return null;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width==3 && height==3;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ShapedRecipeShellSerializer.SERIALIZER;
    }
}
