package com.enigtech.mooshroomcraft;

import com.enigtech.mooshroomcraft.item.ColoredItem;
import com.enigtech.mooshroomcraft.item.ItemMoosher;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.HashMap;
import java.util.Set;

public class IConfigHandler {
    /**
     * 待之后实现配置文件或ZS控制增删资源
     */
    private static HashMap<String, IResource> resourceMap = new HashMap<>();

    static void init(){
        registerResource("iron", new ItemStack(Items.IRON_INGOT), new ItemStack(Items.IRON_INGOT), 0x0F0F0F);
    }

    public static int getColor(String name){
        return resourceMap.get(name).color;
    }

    public static ItemMoosher getMoosher(String name){
        return resourceMap.get(name).moosher;
    }

    public static Item getMushroom(String name){
        return resourceMap.get(name).mushroom;
    }

    public static ColoredItem getMushroomStew(String name){
        return resourceMap.get(name).mushroomStew;
    }

    public static EntityType getMooshroom(String name){
        return resourceMap.get(name).mooshroom;
    }

    public static Block getMushroomBlock(String name){ return resourceMap.get(name).mushroomBlock;}

    public static Set<String> getResourceNames(){
        return resourceMap.keySet();
    }

    private static void registerResource(String name, ItemStack itemIn, ItemStack itemOut, int color){
        resourceMap.put(name, new IResource(name, itemIn, itemOut, color));
    }

}
