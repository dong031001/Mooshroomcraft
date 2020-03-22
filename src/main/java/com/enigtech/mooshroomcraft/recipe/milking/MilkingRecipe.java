package com.enigtech.mooshroomcraft.recipe.milking;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;

public class MilkingRecipe {

    private static ArrayList<MilkingRecipe> recipes = new ArrayList<>();

    public static ArrayList<MilkingRecipe> getRecipes(){
        return recipes;
    }

    private ItemStack moosher;
    private ItemStack milk;
    private ItemStack bucket;
    private String resource;
    private String RecipeID;

    public MilkingRecipe(ItemStack moosher, ItemStack bucket, ItemStack milk, String resource, String id, boolean isCow){
        this.moosher = moosher;
        this.milk = milk;
        this.bucket = bucket;
        this.resource = resource;
        this.RecipeID = id;
    }

    public ItemStack getMoosher(){
        ItemStack moosherCopy = moosher.copy();
        if(!moosherCopy.hasTag()&&this.resource!=null){
            moosherCopy.getOrCreateTag().putString("resource", resource);
        }
        return moosherCopy;
    }

    public ItemStack getMilk(){
        ItemStack milkCopy = milk.copy();
        if(!milkCopy.hasTag()&&this.resource!=null){
            milkCopy.getOrCreateTag().putString("resource", resource);
        }
        return milkCopy;
    }

    public ItemStack getBucket() {
        return bucket.copy();
    }

    public static void register(MilkingRecipe recipe){ recipes.add(recipe); }

    public static void registerVanillaMilkingRecipes(){
        register(new MilkingRecipe(ItemStack.EMPTY, new ItemStack(Items.BUCKET), new ItemStack(Items.MILK_BUCKET), null, "cow_milk", true));
    }
}
