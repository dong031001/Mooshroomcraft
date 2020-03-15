package com.enigtech.mooshroomcraft.item;

import com.enigtech.mooshroomcraft.IConfigHandler;
import com.enigtech.mooshroomcraft.entity.EntityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;

public class ItemMoosher extends ColoredItem {

    EntityType type;
    String resource;

    public ItemMoosher(EntityType type, int color, String resource) {
        super(color);
        this.type = type;
        this.resource = resource;
    }

    public ItemMoosher(){
        super(0);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        if(target instanceof CowEntity &&!(target instanceof MooshroomEntity)&&(!playerIn.world.isRemote)){
            if(!target.isChild()){
                MooshroomEntity mooshroom;
                if(!stack.hasTag()) mooshroom = EntityType.MOOSHROOM.create(playerIn.world);
                else mooshroom = EntityRegistry.RESOURCE_MOOSHROOM_ENTITY_TYPE.create(playerIn.world);
                CompoundNBT tag = new CompoundNBT();
                tag.putString("resource", stack.getOrCreateTag().getString("resource"));
                mooshroom.readAdditional(new CompoundNBT());
                mooshroom.copyLocationAndAnglesFrom(target);
                target.remove();
                mooshroom.setNoAI(((CowEntity) target).isAIDisabled());
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
