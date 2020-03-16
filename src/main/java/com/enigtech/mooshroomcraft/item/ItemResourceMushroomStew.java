package com.enigtech.mooshroomcraft.item;

import com.enigtech.mooshroomcraft.IConfigHandler;
import com.enigtech.mooshroomcraft.util.ItemUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class ItemResourceMushroomStew extends Item {

    String resource;

    public ItemResourceMushroomStew() {
        super(new Properties().group(ItemRegistry.ITEM_GROUP).food((new Food.Builder().hunger(6).saturation(6).build())));
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        ItemStack itemstack = super.onItemUseFinish(stack, worldIn, entityLiving);
        if(entityLiving instanceof PlayerEntity){
            ((PlayerEntity)entityLiving).addItemStackToInventory(new ItemStack(Items.BOWL));
        }
        return itemstack;
    }

    @Override
    public boolean updateItemStackNBT(CompoundNBT nbt) {
        if(nbt.contains("resource")) return false;
        nbt.putString("resource", resource);

        return true;
    }

    public ITextComponent getDisplayName(ItemStack stack) {
        return ItemUtil.getDisplayName(stack, "mushroom_stew");
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
