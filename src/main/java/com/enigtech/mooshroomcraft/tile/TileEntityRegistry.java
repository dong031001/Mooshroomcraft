package com.enigtech.mooshroomcraft.tile;

import com.enigtech.mooshroomcraft.Mooshroomcraft;
import com.enigtech.mooshroomcraft.block.BlockResourceMushroom;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.enigtech.mooshroomcraft.block.BlockRegistry.BLOCK_RESOURCE_MUSHROOM;
import static com.enigtech.mooshroomcraft.block.BlockRegistry.BLOCK_STEW_DISTILLER;

@Mod.EventBusSubscriber(modid = Mooshroomcraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TileEntityRegistry {
    @SubscribeEvent
    public static void onTileRegister(RegistryEvent.Register<TileEntityType<?>> event){
        event.getRegistry().register(TileEntityType.Builder.create(BlockResourceMushroom.TileEntityMushroom::new, BLOCK_RESOURCE_MUSHROOM).build(null).setRegistryName("mooshroomcraft:mushroom"));
        event.getRegistry().register(TileEntityType.Builder.create(TileEntityStewDistiller::new,BLOCK_STEW_DISTILLER).build(null).setRegistryName("stew_distiller"));
    }
}
