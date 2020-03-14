package com.enigtech.mooshroomcraft;

import com.enigtech.mooshroomcraft.block.BlockResourceMushroom;
import com.enigtech.mooshroomcraft.entity.EntityResourceMooshroom;
import com.enigtech.mooshroomcraft.entity.ResourceMooshroomFactory;
import com.enigtech.mooshroomcraft.item.ColoredItem;
import com.enigtech.mooshroomcraft.item.ItemColored;
import com.enigtech.mooshroomcraft.item.ItemMoosher;
import com.enigtech.mooshroomcraft.item.ItemResourceMushroomStew;
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
        this.mushroomStew = new ItemResourceMushroomStew(color).setRegistryName("mushroom_stew_"+resourceName);
        this.mushroomBlock = new BlockResourceMushroom(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().sound(SoundType.PLANT).lightValue(1), this.color).setRegistryName("mushroom_"+resourceName);
        this.mushroom = new BlockItem(mushroomBlock, new Item.Properties()).setRegistryName("mushroom_"+resourceName);
        this.mooshroomEntityType = createEntityType("mooshroom_"+name, new ResourceMooshroomFactory(mushroomStew, mushroomBlock), 0.9F, 1.4F, 0x7C6900, this.color);
        this.moosher = (ItemMoosher) new ItemMoosher(mooshroomEntityType, this.color).setRegistryName("moosher_"+name);
    }
}
