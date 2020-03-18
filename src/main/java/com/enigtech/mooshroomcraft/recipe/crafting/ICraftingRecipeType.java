package com.enigtech.mooshroomcraft.recipe.crafting;

import com.enigtech.mooshroomcraft.IConfigHandler;
import com.enigtech.mooshroomcraft.item.ItemRegistry;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Tags;

public enum ICraftingRecipeType {
    RESOURCE_TO_MUSHROOM, MUSHROOM_TO_MOOSHER;

    public int toInt(){
        return this==RESOURCE_TO_MUSHROOM? 0:1;
    }

    public static ICraftingRecipeType fromInt(int i){
        return i==0? RESOURCE_TO_MUSHROOM : MUSHROOM_TO_MOOSHER;
    }

    public static ItemStack getResourceRecipeOutput(CraftingInventory inventory){

        if(inventory.getStackInSlot(0).getItem()!=inventory.getStackInSlot(2).getItem()
        ||inventory.getStackInSlot(2).getItem()!=inventory.getStackInSlot(6).getItem()
        ||inventory.getStackInSlot(6).getItem()!=inventory.getStackInSlot(8).getItem()) return null;

        int[] otherSlots = new int[]{1,3,4,5,7};

        for(int i: otherSlots){
            if(!Tags.Items.MUSHROOMS.contains(inventory.getStackInSlot(i).getItem())) return null;
        }

        for(String resourceName : IConfigHandler.getResourceNames()){
            ItemStack constructor = IConfigHandler.getConstructor(resourceName);
            Item constructorItem = constructor.getItem();
            Item itemInInv = inventory.getStackInSlot(0).getItem();
            if(constructorItem==itemInInv) return IConfigHandler.getMushroom(resourceName);
        }

        return null;
    }

    public static ItemStack getMoosherRecipeOutput(CraftingInventory inventory) {
        if(inventory.getSizeInventory()!=9) return null;
        if(inventory.getStackInSlot(0).getItem()!= ItemRegistry.MUSHROOM) return null;
        if(!inventory.getStackInSlot(0).hasTag()) return null;
        if(inventory.getStackInSlot(0).getChildTag("BlockEntityTag")==null) return null;
        if(!inventory.getStackInSlot(0).getChildTag("BlockEntityTag").contains("resource")) return null;

        if(inventory.getStackInSlot(0).getItem()!=inventory.getStackInSlot(2).getItem()
                ||inventory.getStackInSlot(2).getItem()!=inventory.getStackInSlot(6).getItem()
                ||inventory.getStackInSlot(6).getItem()!=inventory.getStackInSlot(8).getItem()) return null;

        int[] otherSlots = new int[]{1,3,4,5,7};

        for(int i: otherSlots){
            if(!Tags.Items.MUSHROOMS.contains(inventory.getStackInSlot(i).getItem())) return null;
        }

        for(String resourceName : IConfigHandler.getResourceNames()){
            ItemStack itemStack = inventory.getStackInSlot(0);
            if(itemStack.getChildTag("BlockEntityTag").getString("resource").equals(resourceName)) return IConfigHandler.getMoosher(resourceName);
        }
        return null;
    }
}
