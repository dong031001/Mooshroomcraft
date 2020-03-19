package com.enigtech.mooshroomcraft.recipe.crafting;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.thread.EffectiveSide;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nullable;
import java.util.List;

public class ShapedRecipeShellSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ShapedRecipeShell> {

    @ObjectHolder("mooshroomcraft:mooshroomcraft_recipe")
        public static final IRecipeSerializer<?> SERIALIZER = null;


    @Override
    public ShapedRecipeShell read(ResourceLocation recipeId, JsonObject json) {
        return new ShapedRecipeShell(recipeId);
    }

    @Nullable
    @Override
    public ShapedRecipeShell read(ResourceLocation recipeId, PacketBuffer buffer) {
        return new ShapedRecipeShell(recipeId);
    }

    @Override
    public void write(PacketBuffer buffer, ShapedRecipeShell recipe) { }
}
