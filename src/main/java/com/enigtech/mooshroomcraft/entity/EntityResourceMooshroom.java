package com.enigtech.mooshroomcraft.entity;

import com.enigtech.mooshroomcraft.item.ItemRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.List;

public class EntityResourceMooshroom extends MooshroomEntity {

    public static final DataParameter<String> TYPE = EntityDataManager.createKey(EntityResourceMooshroom.class, DataSerializers.STRING);
    int tickToNextMilking = 0;
    public String resource;

    public ItemStack getMushroomStew() {
        ItemStack stack = new ItemStack(ItemRegistry.MUSHROOM_STEW);
        stack.getOrCreateTag().putString("resource", dataManager.get(TYPE));
        return stack;
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
            ItemStack itemStack1 = getMushroomStew();
            if (itemstack.isEmpty()) {
                player.setHeldItem(hand, itemStack1);
            } else if (!player.inventory.addItemStackToInventory(itemStack1)) {
                player.dropItem(itemStack1, false);
            }
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
            this.playSound(SoundEvents.ENTITY_MOOSHROOM_MILK, 1.0F, 1.0F);
            return true;
        }
        return false;
    }

    @Override
    public void tick(){
        if(!this.world.isRemote && this.isAlive() && this.tickToNextMilking>0) this.tickToNextMilking -= 1;
        super.tick();
    }

    @Override
    public List<ItemStack> onSheared(ItemStack item, IWorld world, BlockPos pos, int fortune) {
        java.util.List<ItemStack> ret = new java.util.ArrayList<>();
        this.world.addParticle(ParticleTypes.EXPLOSION, this.getPosX(), this.getPosYHeight(0.5D), this.getPosZ(), 0.0D, 0.0D, 0.0D);
        if (!this.world.isRemote) {
            this.remove();
            CowEntity cowentity = EntityType.COW.create(this.world);
            cowentity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
            cowentity.setHealth(this.getHealth());
            cowentity.renderYawOffset = this.renderYawOffset;
            if (this.hasCustomName()) {
                cowentity.setCustomName(this.getCustomName());
                cowentity.setCustomNameVisible(this.isCustomNameVisible());
            }
            this.world.addEntity(cowentity);
            for(int i = 0; i < 5; ++i) {
                ItemStack itemStack = new ItemStack(ItemRegistry.MUSHROOM);
                itemStack.getOrCreateChildTag("BlockEntityTag").putString("resource", resource);
                ret.add(itemStack);
            }
            this.playSound(SoundEvents.ENTITY_MOOSHROOM_SHEAR, 1.0F, 1.0F);
        }
        return ret;
    }
}
