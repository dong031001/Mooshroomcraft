package com.enigtech.mooshroomcraft.entity;

import com.enigtech.mooshroomcraft.item.ItemRegistry;
import com.mojang.datafixers.Dynamic;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityResourceMooshroom extends MooshroomEntity {

    public static final DataParameter<String> TYPE = EntityDataManager.createKey(EntityResourceMooshroom.class, DataSerializers.STRING);
    int tickToNextMilking = 0;
    public String resource;


    public EntityResourceMooshroom(EntityType<? extends MooshroomEntity> type, World worldIn, Item mushroomStew, Block mushroom) {
        super(type, worldIn);
    }

    public ItemStack getMushroomStew() {
        CompoundNBT tag = new CompoundNBT();
        tag.putString("resource", resource);
        return new ItemStack(ItemRegistry.MUSHROOM_STEW, 1, tag);
    }

    public EntityResourceMooshroom(EntityType<? extends MooshroomEntity> type, World world){
        super(type ,world);
    }

    protected void registerData(){
        super.registerData();
        dataManager.register(TYPE, "");
    }

    @Override
    public Type getMooshroomType() {
        return Type.BROWN;
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        if (itemstack.getItem() == Items.BOWL && !this.isChild() && canMilk()) {
            if(!player.abilities.isCreativeMode){
                itemstack.shrink(1);
            }
            ItemStack itemstack1 = getMushroomStew();
            if (itemstack.isEmpty()) {
                player.setHeldItem(hand, itemstack1);
            } else if (!player.inventory.addItemStackToInventory(itemstack1)) {
                player.dropItem(itemstack1, false);
            }

            player.playSound(SoundEvents.ENTITY_MOOSHROOM_MILK, 1.0F, 1.0F);
            return true;
        }
        return false;
    }

    @Override
    public void writeAdditional(CompoundNBT tag) {
        super.writeAdditional(tag);
        tag.putInt("TimeTillNextMilking", this.tickToNextMilking);
        if(resource!=null) {
            tag.putString("resource",resource);
            dataManager.set(TYPE,resource);
        }
    }

    @Override
    public void readAdditional(CompoundNBT tag) {
        super.readAdditional(tag);
        if (tag.contains("TimeTillNextMilking")) {
            this.tickToNextMilking = tag.getInt("TimeTillNextMilking");
        } if(tag.contains("resource")){
            this.resource = tag.getString("resource");
            dataManager.set(TYPE,resource);
        }
    }

    public boolean canMilk(){
        if(tickToNextMilking==0){
            tickToNextMilking+=400;
            return true;
        }
        return false;
    }

    @Override
    public void tick(){
        if(!this.world.isRemote && this.isAlive() && this.tickToNextMilking==0) this.tickToNextMilking -= 1;
        super.tick();
    }

    protected void entityInit(){

    }


}
