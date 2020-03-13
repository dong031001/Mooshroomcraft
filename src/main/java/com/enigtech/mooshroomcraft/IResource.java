package com.enigtech.mooshroomcraft;

import com.enigtech.mooshroomcraft.item.ColoredItem;
import com.enigtech.mooshroomcraft.item.ItemMoosher;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class IResource {
    String name;
    ItemMoosher moosher;
    Item mushroom;
    ColoredItem mushroomStew;
    int color;
    EntityType mooshroom;
    Block mushroomBlock;


    public IResource(String resourceName, ItemStack itemIn, ItemStack itemOut, int color){
        this.name = resourceName;
        this.color = color;
        this.mushroomStew = (ColoredItem) new ColoredItem(color).setRegistryName("mushroom_stew_"+resourceName);
    }
}
