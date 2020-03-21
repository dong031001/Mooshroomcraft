package com.enigtech.mooshroomcraft.recipe;

import com.enigtech.mooshroomcraft.Mooshroomcraft;
import com.enigtech.mooshroomcraft.recipe.crafting.ShapedRecipeShellSerializer;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Mooshroomcraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RecipeRegistry {
    @SubscribeEvent
    public static void onRecipeRegister(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        event.getRegistry().register(new ShapedRecipeShellSerializer().setRegistryName(Mooshroomcraft.MOD_ID, "mooshroomcraft_recipe"));
    }

    @SubscribeEvent
    public static void registerRegistry(RegistryEvent.NewRegistry event){

    }
}
