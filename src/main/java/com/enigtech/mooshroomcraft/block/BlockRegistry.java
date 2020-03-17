package com.enigtech.mooshroomcraft.block;

import com.enigtech.mooshroomcraft.Mooshroomcraft;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.enigtech.mooshroomcraft.block.BlockResourceMushroom.TileEntityMushroom;

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
