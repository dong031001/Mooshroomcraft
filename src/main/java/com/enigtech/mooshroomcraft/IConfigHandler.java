package com.enigtech.mooshroomcraft;

import com.enigtech.mooshroomcraft.entity.EntityResourceMooshroom;
import com.enigtech.mooshroomcraft.item.ColoredItem;
import com.enigtech.mooshroomcraft.item.ItemMoosher;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.HashMap;
import java.util.Set;

public class IConfigHandler {
    /**
     * 待之后实现配置文件或ZS控制增删资源
     */
    private static HashMap<String, IResource> resourceMap = new HashMap<>();

    static void init(){
        registerResource("iron",0x0F0F0F);
        registerResource("gold",0xffde05);
    }

    public static int getColor(String name){
        return resourceMap.get(name).color;
    }

    public static ItemMoosher getMoosher(String name){
        return resourceMap.get(name).moosher;
    }

    public static Item getMushroom(String name){
        return resourceMap.get(name).mushroom;
    }

    public static ColoredItem getMushroomStew(String name){
        return resourceMap.get(name).mushroomStew;
    }

    public static EntityType<EntityResourceMooshroom> getMooshroom(String name){
        return resourceMap.get(name).mooshroomEntityType;
    }

    public static Block getMushroomBlock(String name){ return resourceMap.get(name).mushroomBlock;}

    public static Set<String> getResourceNames(){
        return resourceMap.keySet();
    }

    private static void registerResource(String name, int color){
        resourceMap.put(name, new IResource(name, color));
    }

}
