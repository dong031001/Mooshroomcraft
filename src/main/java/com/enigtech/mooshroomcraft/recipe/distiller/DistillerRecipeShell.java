package com.enigtech.mooshroomcraft.recipe.distiller;

import com.enigtech.mooshroomcraft.Mooshroomcraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

public class DistillerRecipeShell extends AbstractCookingRecipe {

    @ObjectHolder("mooshroomcraft:distiller_recipe")
    public static final IRecipeSerializer<?> DISTILLER_RECIPE_SERIALIZER = null;

    public static final IForgeRegistry DISTILLER_RECIPE_TYPE = new RegistryBuilder().setType(DistillerRecipeShell.class).setName(new ResourceLocation(Mooshroomcraft.MOD_ID, "distiller_recipe")).create();



    public DistillerRecipeShell(IRecipeType<?> typeIn, ResourceLocation idIn, String groupIn, Ingredient ingredientIn, ItemStack resultIn, float experienceIn, int cookTimeIn) {
        //TODO
        super(typeIn, idIn, groupIn, ingredientIn, resultIn, experienceIn, cookTimeIn);
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }

    @Override
    public ResourceLocation getId() {
        return null;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return DISTILLER_RECIPE_SERIALIZER;
    }

    @Override
    public IRecipeType<?> getType() {
        return null;
    }
}
