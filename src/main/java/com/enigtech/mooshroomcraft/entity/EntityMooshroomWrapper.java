package com.enigtech.mooshroomcraft.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class EntityMooshroomWrapper extends MooshroomEntity {

    private int timeTillNextMilking = 0;

    public EntityMooshroomWrapper(EntityType type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        if(this.timeTillNextMilking==0){
            timeTillNextMilking+=400;
            return super.processInteract(player, hand);
        }
        return false;
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("timeTillNextMilking", timeTillNextMilking);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        timeTillNextMilking = compound.getInt("timeTillNextMilking");
    }

    @Override
    public void tick() {
        super.tick();
        if(timeTillNextMilking>0) timeTillNextMilking--;
    }
}
