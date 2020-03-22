package com.enigtech.mooshroomcraft;

import com.enigtech.mooshroomcraft.entity.EntityResourceMooshroom;
import com.enigtech.mooshroomcraft.item.*;
import com.enigtech.mooshroomcraft.recipe.crafting.RealShapedRecipe;
import com.enigtech.mooshroomcraft.recipe.distiller.DistillerRecipe;
import com.enigtech.mooshroomcraft.recipe.milking.MilkingRecipe;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import java.util.ArrayList;

public class IResource {

    String name;
    String displayName;
    private ItemStack moosher;
    private ItemStack mushroom;
    private ItemStack mushroomStew;

    public ItemStack getMoosher() {
        return moosher.copy();
    }

    public ItemStack getMushroom() {
        return mushroom.copy();
    }

    public ItemStack getMushroomStew() {
        return mushroomStew.copy();
    }

    EffectInstance[] stewEffect;
    int color;
    EntityType<EntityResourceMooshroom> mooshroomEntityType;
    Block mushroomBlock;

    ItemStack constructor;
    ItemStack result;

    public ItemStack getConstructor() {
        return constructor.copy();
    }

    public ItemStack getResult() {
        return result.copy();
    }



    public IResource(String resourceName, int color, String displayName, ArrayList<EffectInstance> stewEffects, ItemStack constructor, ItemStack result){
        this.displayName = displayName;
        this.name = resourceName;
        this.color = color;
        this.stewEffect = stewEffects.toArray(new EffectInstance[0]);
        this.constructor = constructor;
        this.result = result;
        ItemStack mushroom = new ItemStack(ItemRegistry.MUSHROOM);
        mushroom.getOrCreateChildTag("BlockEntityTag").putString("resource", name);
        this.mushroom = mushroom;
        ItemStack moosher = new ItemStack(ItemRegistry.MOOSHER);
        moosher.getOrCreateTag().putString("resource", resourceName);
        this.moosher = moosher;
        ItemStack mushroomStew = new ItemStack(ItemRegistry.MUSHROOM_STEW);
        moosher.getOrCreateTag().putString("resource", resourceName);
        this.mushroomStew = mushroomStew;

        Ingredient[][] mushroomRecipe = new Ingredient[][]{
                new Ingredient[]{Ingredient.fromStacks(getConstructor()), Ingredient.fromTag(Tags.Items.MUSHROOMS), Ingredient.fromStacks(getConstructor())},
                new Ingredient[]{Ingredient.fromTag(Tags.Items.MUSHROOMS), Ingredient.fromTag(Tags.Items.MUSHROOMS), Ingredient.fromTag(Tags.Items.MUSHROOMS)},
                new Ingredient[]{Ingredient.fromStacks(getConstructor()), Ingredient.fromTag(Tags.Items.MUSHROOMS), Ingredient.fromStacks(getConstructor())}
        };
        RealShapedRecipe.register(new RealShapedRecipe(mushroomRecipe, this.mushroom.copy(), new ResourceLocation(Mooshroomcraft.MOD_ID, name+"_mushroom")));

        Ingredient[][] moosherRecipe = new Ingredient[][]{
                new Ingredient[]{Ingredient.fromStacks(getMushroom()), Ingredient.fromTag(Tags.Items.MUSHROOMS), Ingredient.fromStacks(getMushroom())},
                new Ingredient[]{Ingredient.fromTag(Tags.Items.MUSHROOMS), Ingredient.fromTag(Tags.Items.MUSHROOMS), Ingredient.fromTag(Tags.Items.MUSHROOMS)},
                new Ingredient[]{Ingredient.fromStacks(getMushroom()), Ingredient.fromTag(Tags.Items.MUSHROOMS), Ingredient.fromStacks(getMushroom())}
        };
        RealShapedRecipe.register(new RealShapedRecipe(moosherRecipe, getMoosher(), new ResourceLocation(Mooshroomcraft.MOD_ID, name+"_moosher")));
        System.out.println(getMushroomStew()+"->"+getResult());
        DistillerRecipe.register(new DistillerRecipe(getMushroomStew(), getResult(), name, name+"_distiller_recipe"));
        MilkingRecipe.register(new MilkingRecipe(getMoosher(), new ItemStack(Items.BOWL), getMushroomStew(), name, name+"milking_recipe", true));
    }


}
