package com.enigtech.mooshroomcraft.recipe.milking;

import com.enigtech.mooshroomcraft.Mooshroomcraft;
import com.enigtech.mooshroomcraft.item.ItemRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public class MilkingRecipeCategory implements IRecipeCategory<MilkingRecipe> {

    public static ResourceLocation UID = new ResourceLocation(Mooshroomcraft.MOD_ID, "milking_recipe");

    private final IDrawable background;
    private final IDrawable icon;

    public MilkingRecipeCategory(IGuiHelper helper){
        ResourceLocation base = new ResourceLocation(Mooshroomcraft.MOD_ID, "textures/gui/jei_milking_recipe.png");
        background = helper.createDrawable(base, 0,0,110,65);
        icon = helper.createDrawableIngredient(new ItemStack(Items.MILK_BUCKET));
    }


    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends MilkingRecipe> getRecipeClass() {
        return MilkingRecipe.class;
    }

    @Override
    public String getTitle() {
        return I18n.format("category."+Mooshroomcraft.MOD_ID+".milking_recipe");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(MilkingRecipe milkingRecipe, IIngredients iIngredients) {
        ArrayList<ItemStack> list = new ArrayList<ItemStack>(){{
            add(milkingRecipe.getBucket());
            add(milkingRecipe.getMoosher());
        }};
        iIngredients.setInputs(VanillaTypes.ITEM, list);
        iIngredients.setOutput(VanillaTypes.ITEM, milkingRecipe.getMilk());
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, MilkingRecipe milkingRecipe, IIngredients iIngredients) {
        IGuiItemStackGroup itemStackGroup = iRecipeLayout.getItemStacks();
        itemStackGroup.init(0,true, 3,8);
        itemStackGroup.init(1, true, 46,47);
        itemStackGroup.init(2, false, 90,8);
        itemStackGroup.set(iIngredients);
    }
}
