package com.enigtech.mooshroomcraft.item;

import com.enigtech.mooshroomcraft.IConfigHandler;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.util.NonNullList;

public class ItemResourceMushroomStew extends Item {

    String resource;

    public ItemResourceMushroomStew() {
        super(new Properties().group(ItemRegistry.ITEM_GROUP).food((new Food.Builder()
                .hunger(6)
                .saturation(6)
                .effect(new EffectInstance(Effects.SLOWNESS,100),100)
                .effect(new EffectInstance(Effects.ABSORPTION,200),100)
                .build()
        )));
    }

    @Override
    public boolean updateItemStackNBT(CompoundNBT nbt) {
        if(nbt.contains("resource")) return false;
        nbt.putString("resource", resource);
        return true;
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        if(stack.getOrCreateTag().contains("resource")) return this.getTranslationKey()+"_"+stack.getTag().getString("resource");
        return getTranslationKey();
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            items.add(new ItemStack(this));
            for(String name : IConfigHandler.getResourceNames()){
                ItemStack itemStack = new ItemStack(this);
                itemStack.setTag(new CompoundNBT());
                itemStack.getTag().putString("resource", name);
                items.add(itemStack);
            }
        }
    }
}
