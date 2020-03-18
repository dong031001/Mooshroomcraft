package com.enigtech.mooshroomcraft.recipe;

import com.enigtech.mooshroomcraft.item.ItemRegistry;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.Tag;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.thread.EffectiveSide;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nullable;
import java.util.List;

public class IShapedRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<IShapedRecipe> {

    @ObjectHolder("mooshroomcraft:mooshroomcraft_recipe")
        public static final IRecipeSerializer<?> SERIALIZER = null;

    public static boolean recordRecipes = EffectiveSide.get() == LogicalSide.CLIENT && ModList.get().isLoaded("jei");
    public static List<IShapedRecipe> recipes = recordRecipes ? Lists.newArrayList() : null;

    @Override
    public IShapedRecipe read(ResourceLocation recipeId, JsonObject json) {
        String recipeType = JSONUtils.getString(json,"recipeType");
        RecipeType parsedType = recipeType.equals("mushroom")? RecipeType.RESOURCE_TO_MUSHROOM  : recipeType.equals("moosher")? RecipeType.MUSHROOM_TO_MOOSHER : null;
        if(parsedType==null) throw new IllegalStateException("MOOSHROOMCRAFT RECIPE ILLEGAL");

        return new IShapedRecipe(parsedType, recipeId);
    }

    @Nullable
    @Override
    public IShapedRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
        return new IShapedRecipe(RecipeType.fromInt(buffer.readInt()), recipeId);
    }

    @Override
    public void write(PacketBuffer buffer, IShapedRecipe recipe) {
        buffer.writeInt(recipe.type.toInt());
    }
}
