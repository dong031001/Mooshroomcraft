package com.enigtech.mooshroomcraft.item;

import com.enigtech.mooshroomcraft.IConfigHandler;
import com.enigtech.mooshroomcraft.MooshroomcraftPacketHandler;
import com.enigtech.mooshroomcraft.block.BlockRegistry;
import com.enigtech.mooshroomcraft.block.BlockResourceMushroom;
import net.minecraft.block.Block;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkDirection;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.Objects;

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

    @Override
    public String getTranslationKey(ItemStack stack) {
        if(stack.getTag() != null && stack.getTag().contains("BlockEntityTag")) return this.getTranslationKey()+"_"+stack.getTag().getCompound("BlockEntityTag").getString("resource");
        return getTranslationKey();
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
