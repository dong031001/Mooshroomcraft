package com.enigtech.mooshroomcraft.item;


import com.enigtech.mooshroomcraft.IConfigHandler;
import com.enigtech.mooshroomcraft.Mooshroomcraft;
import com.enigtech.mooshroomcraft.entity.EntityRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Mooshroomcraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemRegistry {

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        for(String resourceName: IConfigHandler.getResourceNames()){
            event.getRegistry().register(IConfigHandler.getMushroomStew(resourceName));
            event.getRegistry().register(IConfigHandler.getMushroom(resourceName));
            event.getRegistry().register(IConfigHandler.getMoosher(resourceName));
        }
    }
}
