package com.enigtech.mooshroomcraft.block;

import com.enigtech.mooshroomcraft.Mooshroomcraft;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Mooshroomcraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockRegistry {
    public static final Block BLOCK_RESOURCE_MUSHROOM = new BlockResourceMushroom(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().sound(SoundType.PLANT).lightValue(1)).setRegistryName("mushroom");
    public static final Block BLOCK_STEW_DISTILLER = new BlockStewDistiller().setRegistryName("stew_distiller");

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event){
        event.getRegistry().register(BLOCK_RESOURCE_MUSHROOM);
        event.getRegistry().register(BLOCK_STEW_DISTILLER);
    }
}
