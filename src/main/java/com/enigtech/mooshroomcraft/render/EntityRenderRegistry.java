package com.enigtech.mooshroomcraft.render;

import com.enigtech.mooshroomcraft.IConfigHandler;
import com.enigtech.mooshroomcraft.Mooshroomcraft;
import com.enigtech.mooshroomcraft.entity.EntityResourceMooshroom;
import com.enigtech.mooshroomcraft.render.entity.RenderFactoryResourceMooshroom;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Mooshroomcraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRenderRegistry {

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void clientInit(ModelRegistryEvent event) {
        for(String name : IConfigHandler.getResourceNames()){
            EntityType<EntityResourceMooshroom> type = IConfigHandler.getMooshroom(name);
            RenderingRegistry.registerEntityRenderingHandler(type, new RenderFactoryResourceMooshroom(IConfigHandler.getMushroomBlock(name)));
        }
    }

}
