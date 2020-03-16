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
import net.minecraft.potion.EffectInstance;

import java.util.ArrayList;

public class IResource {

    String name;
    String displayName;
    ItemMoosher moosher;
    Item mushroom;
    Item mushroomStew;
    EffectInstance[] stewEffect;
    int color;
    EntityType<EntityResourceMooshroom> mooshroomEntityType;
    Block mushroomBlock;


    public IResource(String resourceName, int color, String displayName, ArrayList<EffectInstance> stewEffects){
        this.displayName = displayName;
        this.name = resourceName;
        this.color = color;
        this.stewEffect = stewEffects.toArray(new EffectInstance[0]);

    }
}
