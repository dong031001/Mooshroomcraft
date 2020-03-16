package com.enigtech.mooshroomcraft.item;

import com.enigtech.mooshroomcraft.IConfigHandler;
import com.enigtech.mooshroomcraft.block.BlockRegistry;
import com.enigtech.mooshroomcraft.util.ItemUtil;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

import static com.enigtech.mooshroomcraft.item.ItemRegistry.ITEM_GROUP;

public class ItemMushroom extends BlockItem {

    String resource;

    public ItemMushroom() {
        super(BlockRegistry.BLOCK_RESOURCE_MUSHROOM, new Item.Properties().group(ITEM_GROUP));
    }

    @Override
    public boolean updateItemStackNBT(CompoundNBT nbt) {
        if(nbt.contains("resource")||nbt.contains("BlockEntityTag")) return false;
        nbt.putString("resource", resource);
        return true;
    }

    public ITextComponent getDisplayName(ItemStack stack) {
        return ItemUtil.getDisplayName(stack, "mushroom");
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            items.add(new ItemStack(this));
            for(String name : IConfigHandler.getResourceNames()){
                ItemStack itemStack = new ItemStack(this);
                itemStack.setTag(new CompoundNBT());
                itemStack.getOrCreateChildTag("BlockEntityTag").putString("resource", name);
                items.add(itemStack);
            }
        }
    }
}
