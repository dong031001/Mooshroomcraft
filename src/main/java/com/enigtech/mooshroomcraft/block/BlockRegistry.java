package com.enigtech.mooshroomcraft.block;

import com.enigtech.mooshroomcraft.IConfigHandler;
import com.enigtech.mooshroomcraft.IResource;
import com.enigtech.mooshroomcraft.Mooshroomcraft;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Mooshroomcraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockRegistry {
    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event){
        for(String resourceName : IConfigHandler.getResourceNames()){
            Block block = IConfigHandler.getMushroomBlock(resourceName);
            event.getRegistry().register(block);
        }
    }
}
