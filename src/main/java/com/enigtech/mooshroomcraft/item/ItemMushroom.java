package com.enigtech.mooshroomcraft.item;

import com.enigtech.mooshroomcraft.IConfigHandler;
import com.enigtech.mooshroomcraft.block.BlockRegistry;
import com.enigtech.mooshroomcraft.block.BlockResourceMushroom;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;

import static com.enigtech.mooshroomcraft.item.ItemRegistry.ITEM_GROUP;

public class ItemMushroom extends BlockItem {

    String resource;

    public ItemMushroom() {
        super(BlockRegistry.BLOCK_RESOURCE_MUSHROOM, new Item.Properties().group(ITEM_GROUP));
    }

    public ActionResultType onItemUse(ItemUseContext context) {
        ActionResultType type = super.onItemUse(context);
        BlockItemUseContext blockItemUseContext = new BlockItemUseContext(context);
        ((BlockResourceMushroom.TileEntityMushroom)context.getWorld().getTileEntity(blockItemUseContext.getPos())).refresh();
        return type;
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
        else if(stack.getTag().contains("BlockEntityData")) return this.getTranslationKey()+"_"+stack.getTag().getCompound("BlockEntityData").getString("resource");
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
                itemStack.getOrCreateChildTag("BlockEntityTag").putString("resource", name);
                items.add(itemStack);
            }
        }
    }
}
