package com.enigtech.mooshroomcraft.recipe.crafting;

import com.enigtech.mooshroomcraft.recipe.crafting.ICraftingRecipeType;
import com.enigtech.mooshroomcraft.recipe.crafting.IShapedRecipeSerializer;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class IShapedRecipe implements ICraftingRecipe {

    ICraftingRecipeType type;
    ResourceLocation id;

    public IShapedRecipe(ICraftingRecipeType parsedType, ResourceLocation recipeId) {
        this.type = parsedType;
        this.id = recipeId;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        if(type == ICraftingRecipeType.RESOURCE_TO_MUSHROOM){
            return ICraftingRecipeType.getResourceRecipeOutput(inv) != null;
        } else {
            return ICraftingRecipeType.getMoosherRecipeOutput(inv) != null;
        }
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        if(type == ICraftingRecipeType.MUSHROOM_TO_MOOSHER){
            return ICraftingRecipeType.getMoosherRecipeOutput(inv);
        } else {
            return ICraftingRecipeType.getResourceRecipeOutput(inv);
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
