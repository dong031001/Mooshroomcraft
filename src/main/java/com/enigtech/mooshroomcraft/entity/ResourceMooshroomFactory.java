package com.enigtech.mooshroomcraft.entity;

import com.enigtech.mooshroomcraft.block.BlockResourceMushroom;
import net.minecraft.block.Block;
import net.minecraft.block.MushroomBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class ResourceMooshroomFactory implements EntityType.IFactory<EntityResourceMooshroom> {
    Item stew;
    Block mushroom;

    public ResourceMooshroomFactory(Item stew, Block mushroom){
        this.stew = stew;
        this.mushroom = mushroom;
    }

    @Override
    public EntityResourceMooshroom create(EntityType p_create_1_, World p_create_2_) {
        return new EntityResourceMooshroom(p_create_1_,p_create_2_, stew, mushroom);
    }
}
