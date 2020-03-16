package com.enigtech.mooshroomcraft.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class ResourceMooshroomFactory implements EntityType.IFactory<EntityResourceMooshroom> {

    @Override
    public EntityResourceMooshroom create(EntityType p_create_1_, World p_create_2_) {
        return new EntityResourceMooshroom(p_create_1_,p_create_2_);
    }
}
