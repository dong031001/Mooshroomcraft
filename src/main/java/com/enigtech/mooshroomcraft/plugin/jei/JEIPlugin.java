package com.enigtech.mooshroomcraft.plugin.jei;

import com.enigtech.mooshroomcraft.IConfigHandler;
import com.enigtech.mooshroomcraft.Mooshroomcraft;
import com.enigtech.mooshroomcraft.item.ItemRegistry;
import com.enigtech.mooshroomcraft.recipe.crafting.RealShapedRecipe;
import com.enigtech.mooshroomcraft.recipe.distiller.DistillerRecipe;
import com.enigtech.mooshroomcraft.recipe.distiller.DistillerRecipeCategory;
import com.enigtech.mooshroomcraft.tile.container.ContainerStewDistiller;
import com.enigtech.mooshroomcraft.tile.container.ScreenStewDistiller;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.recipe.category.extensions.IExtendableRecipeCategory;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import mezz.jei.api.registration.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    private static ISubtypeInterpreter MOOSHROOMCRAFT_ITEM_TYPE_INTERPRETER = new SubTypeInterpreter();

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Mooshroomcraft.MOD_ID,Mooshroomcraft.MOD_ID);
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(ItemRegistry.MUSHROOM, MOOSHROOMCRAFT_ITEM_TYPE_INTERPRETER);
        registration.registerSubtypeInterpreter(ItemRegistry.MOOSHER, MOOSHROOMCRAFT_ITEM_TYPE_INTERPRETER);
        registration.registerSubtypeInterpreter(ItemRegistry.MUSHROOM_STEW, MOOSHROOMCRAFT_ITEM_TYPE_INTERPRETER);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(RealShapedRecipe.getRecipes(), VanillaRecipeCategoryUid.CRAFTING);
        registration.addRecipes(DistillerRecipe.getRecipeManager(), DistillerRecipeCategory.UID);
    }

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
        IExtendableRecipeCategory<ICraftingRecipe, ICraftingCategoryExtension> craftingCategory = registration.getCraftingCategory();
        craftingCategory.addCategoryExtension(RealShapedRecipe.class, RealShapedRecipeWrapper::new);

    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new DistillerRecipeCategory(helper));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ItemRegistry.ITEM_STEW_DISTILLER), DistillerRecipeCategory.UID);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(ScreenStewDistiller.class, 78,32,28,23, DistillerRecipeCategory.UID, VanillaRecipeCategoryUid.FUEL);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(ContainerStewDistiller.class, DistillerRecipeCategory.UID, 0, 1, 4, 36);
        registration.addRecipeTransferHandler(ContainerStewDistiller.class, VanillaRecipeCategoryUid.FUEL, 1,1, 4,36);
    }

    private static class SubTypeInterpreter implements ISubtypeInterpreter{
        @Override
        public String apply(ItemStack itemStack) {
            return IConfigHandler.getNameFromItemStack(itemStack);
        }
    }

    private static class RealShapedRecipeWrapper implements ICraftingCategoryExtension {
        private RealShapedRecipe recipe;

        public RealShapedRecipeWrapper(RealShapedRecipe recipe) {
            this.recipe = recipe;
        }

        @Override
        public void setIngredients(IIngredients ingredients) {
            List<Ingredient> inputs = new ArrayList<>();
            Ingredient[][] grid = recipe.getRecipe();
            for(int i = 0; i < 3; i++) inputs.addAll(Arrays.asList(grid[i]).subList(0, 3));
            ingredients.setInputIngredients(inputs);
            ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
        }


    }
}
