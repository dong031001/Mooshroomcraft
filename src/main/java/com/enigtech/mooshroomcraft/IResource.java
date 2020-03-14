package com.enigtech.mooshroomcraft;

import com.enigtech.mooshroomcraft.block.BlockResourceMushroom;
import com.enigtech.mooshroomcraft.entity.EntityResourceMooshroom;
import com.enigtech.mooshroomcraft.entity.ResourceMooshroomFactory;
import com.enigtech.mooshroomcraft.item.ColoredItem;
import com.enigtech.mooshroomcraft.item.ItemMoosher;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import static com.enigtech.mooshroomcraft.entity.EntityRegistry.createEntityType;

public class IResource {
    String name;
    ItemMoosher moosher;
    Item mushroom;
    ColoredItem mushroomStew;
    int color;
    EntityType<EntityResourceMooshroom> mooshroomEntityType;
    Block mushroomBlock;


    public IResource(String resourceName, ItemStack itemIn, ItemStack itemOut, int color){
        this.name = resourceName;
        this.color = color;
        this.mushroomStew = (ColoredItem) new ColoredItem(color).setRegistryName("mushroom_stew_"+resourceName);
        this.mushroomBlock = new BlockResourceMushroom(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().sound(SoundType.PLANT).lightValue(1)).setRegistryName("mushroom_"+resourceName);
        this.mushroom = new BlockItem(mushroomBlock, new Item.Properties()).setRegistryName("mushroom_"+resourceName);
        this.mooshroomEntityType = createEntityType("mooshroom_"+name, new ResourceMooshroomFactory(mushroomStew, mushroomBlock), 0.9F, 1.4F, 0x7C6900, color);
    }
}
