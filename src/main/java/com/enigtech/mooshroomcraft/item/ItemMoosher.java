package com.enigtech.mooshroomcraft.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.Hand;

public class ItemMoosher extends ColoredItem {

    EntityType type;
    int color;

    public ItemMoosher(EntityType type, int color) {
        super(color);
        this.type = type;
        this.color = color;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        if(target instanceof CowEntity &&!(target instanceof MooshroomEntity)&&(!playerIn.world.isRemote)){
            if(!target.isChild()){
                Entity mooshroom = type.create(playerIn.world);
                if (mooshroom == null) throw new AssertionError();
                mooshroom.copyLocationAndAnglesFrom(target);
                target.remove();
                if(mooshroom instanceof MobEntity){
                    ((MooshroomEntity) mooshroom).setNoAI(((CowEntity) target).isAIDisabled());
                }
                playerIn.world.addEntity(mooshroom);
                stack.shrink(1);
                return true;
            }
        }
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }

    @Override
    public Rarity getRarity(ItemStack itemStack){
        return Rarity.RARE;
    }

    public int getColor() {
        return color;
    }
}
