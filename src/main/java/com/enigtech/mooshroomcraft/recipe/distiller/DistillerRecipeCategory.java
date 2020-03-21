package com.enigtech.mooshroomcraft.recipe.distiller;

import com.enigtech.mooshroomcraft.Mooshroomcraft;
import com.enigtech.mooshroomcraft.item.ItemRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public class DistillerRecipeCategory implements IRecipeCategory<DistillerRecipe> {

    public static ResourceLocation UID = new ResourceLocation(Mooshroomcraft.MOD_ID, "distiller_recipe");

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableStatic staticFlame;
    private final IDrawableAnimated animatedFlame;
    private final IDrawableAnimated arrow;

    public DistillerRecipeCategory(IGuiHelper helper){
        ResourceLocation base = new ResourceLocation(Mooshroomcraft.MOD_ID, "textures/gui/jei_distiller_recipe.png");
        background = helper.createDrawable(base, 0,0,105,33);
        icon = helper.createDrawableIngredient(new ItemStack(ItemRegistry.ITEM_STEW_DISTILLER));
        staticFlame = helper.createDrawable(base, 105, 0, 14,14);
        animatedFlame = helper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);
        arrow = helper.drawableBuilder(base, 105,14,24,17).buildAnimated(100, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, DistillerRecipe o, IIngredients iIngredients) {
        IGuiItemStackGroup itemStackGroup = iRecipeLayout.getItemStacks();
        itemStackGroup.init(0,true, 0,0);
        itemStackGroup.init(1, false, 60,8);
        itemStackGroup.init(2, false, 87,8);
        itemStackGroup.set(iIngredients);
    }

    @Override
    public void draw(DistillerRecipe recipe, double mouseX, double mouseY) {
        animatedFlame.draw(1,20);
        arrow.draw(24,9);
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class getRecipeClass() {
        return DistillerRecipe.class;
    }

    @Override
    public String getTitle() {
        return I18n.format("category."+Mooshroomcraft.MOD_ID+".distiller_recipe");
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
    public void setIngredients(DistillerRecipe o, IIngredients iIngredients) {
        iIngredients.setInput(VanillaTypes.ITEM,o.getInputStack());
        ArrayList<ItemStack> outputs = new ArrayList<>();
        outputs.add(o.getOutputStack());
        outputs.add(new ItemStack(Items.BOWL));
        iIngredients.setOutputs(VanillaTypes.ITEM,outputs);
    }
}
