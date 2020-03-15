package com.enigtech.mooshroomcraft;

import com.enigtech.mooshroomcraft.block.BlockResourceMushroom;
import com.enigtech.mooshroomcraft.entity.EntityResourceMooshroom;
import com.enigtech.mooshroomcraft.entity.ResourceMooshroomFactory;
import com.enigtech.mooshroomcraft.item.*;
import javafx.scene.effect.Effect;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;

import java.util.List;

import static com.enigtech.mooshroomcraft.entity.EntityRegistry.createEntityType;

public class IResource {

    String name;
    ItemMoosher moosher;
    Item mushroom;
    Item mushroomStew;
    EffectInstance[] stewEffect;
    int color;
    EntityType<EntityResourceMooshroom> mooshroomEntityType;
    Block mushroomBlock;


    public IResource(String resourceName, int color, EffectInstance... effectInstances){

        this.name = resourceName;
        this.color = color;
        this.stewEffect = effectInstances;

    }
}
