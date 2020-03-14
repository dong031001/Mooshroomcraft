package com.enigtech.mooshroomcraft.entity;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class EntityResourceMooshroom extends MooshroomEntity {

    Block mushroom;
    Item mushroomStew;

    public EntityResourceMooshroom(EntityType<? extends MooshroomEntity> type, World worldIn, Item mushroomStew, Block mushroom) {
        super(type, worldIn);
        this.mushroom = mushroom;
        this.mushroomStew = mushroomStew;
    }

    @Override
    public Type getMooshroomType() {
        return Type.BROWN;
    }

    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        if (itemstack.getItem() == Items.BOWL && !this.isChild()) {
            if(!player.abilities.isCreativeMode){
                itemstack.shrink(1);
            }
            ItemStack itemstack1 = new ItemStack(mushroomStew);
            if (itemstack.isEmpty()) {
                player.setHeldItem(hand, itemstack1);
            } else if (!player.inventory.addItemStackToInventory(itemstack1)) {
                player.dropItem(itemstack1, false);
            }

            this.playSound(SoundEvents.ENTITY_MOOSHROOM_MILK, 1.0F, 1.0F);
            return true;
        }
        return false;
    }
}
