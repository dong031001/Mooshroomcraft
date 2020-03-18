package com.enigtech.mooshroomcraft.recipe;

import com.enigtech.mooshroomcraft.item.ItemRegistry;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class IShapedRecipe implements ICraftingRecipe {

    RecipeType type;
    ResourceLocation id;

    public IShapedRecipe(RecipeType parsedType, ResourceLocation recipeId) {
        this.type = parsedType;
        this.id = recipeId;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        if(type == RecipeType.RESOURCE_TO_MUSHROOM){
            return RecipeType.getResourceRecipeOutput(inv) != null;
        } else {
            return RecipeType.getMoosherRecipeOutput(inv) != null;
        }
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        if(type == RecipeType.MUSHROOM_TO_MOOSHER){
            return RecipeType.getMoosherRecipeOutput(inv);
        } else {
            return RecipeType.getResourceRecipeOutput(inv);
        }
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
        return IShapedRecipeSerializer.SERIALIZER;
    }
}
