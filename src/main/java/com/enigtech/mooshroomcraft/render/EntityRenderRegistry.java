package com.enigtech.mooshroomcraft.render;

import com.enigtech.mooshroomcraft.Mooshroomcraft;
import com.enigtech.mooshroomcraft.entity.EntityRegistry;
import com.enigtech.mooshroomcraft.render.entity.RenderFactoryResourceMooshroom;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Mooshroomcraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntityRenderRegistry {

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void clientInit(ModelRegistryEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.RESOURCE_MOOSHROOM_ENTITY_TYPE, new RenderFactoryResourceMooshroom());
    }
}