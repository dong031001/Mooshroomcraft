package com.enigtech.mooshroomcraft;

import com.enigtech.mooshroomcraft.entity.EntityResourceMooshroom;
import com.enigtech.mooshroomcraft.item.*;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

    ItemStack constructor;
    ItemStack result;

    public ItemStack getConstructor() {
        return constructor.copy();
    }

    public ItemStack getResult() {
        return result.copy();
    }



    public IResource(String resourceName, int color, String displayName, ArrayList<EffectInstance> stewEffects, ItemStack constructor, ItemStack result){
        this.displayName = displayName;
        this.name = resourceName;
        this.color = color;
        this.stewEffect = stewEffects.toArray(new EffectInstance[0]);
        this.constructor = constructor;
        this.result = result;
    }


}
