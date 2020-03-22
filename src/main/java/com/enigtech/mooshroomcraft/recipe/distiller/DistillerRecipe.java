package com.enigtech.mooshroomcraft.recipe.distiller;

import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class DistillerRecipe {

    static ArrayList<DistillerRecipe> recipes = new ArrayList<>();

    private ItemStack inputStack;
    private ItemStack outputStack;
    private String recipeID;
    private String name;

    public DistillerRecipe(ItemStack input, ItemStack output, String name, String id){
        this.inputStack = input;
        this.outputStack = output;
        this.recipeID = id;
        this.name = name;
    }

    public ItemStack getInputStack() {
        ItemStack getter = inputStack.copy();
        getter.getOrCreateTag().putString("resource", name);
        return getter;
    }

    public ItemStack getOutputStack() {
        return outputStack.copy();
    }

    public static ItemStack getOutputFromInput(ItemStack input){
        for(DistillerRecipe recipe:recipes){
            if(Container.areItemsAndTagsEqual(recipe.inputStack, input)) return recipe.getOutputStack();
        }
        return null;
    }

    public static void register(DistillerRecipe recipe){
        recipes.add(recipe);
    }

    public static ArrayList<DistillerRecipe> getRecipes(){
        return recipes;
    }
}
