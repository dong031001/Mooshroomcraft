package com.enigtech.mooshroomcraft.render;

import com.enigtech.mooshroomcraft.IConfigHandler;
import com.enigtech.mooshroomcraft.Mooshroomcraft;
import com.enigtech.mooshroomcraft.entity.EntityResourceMooshroom;
import net.minecraft.client.renderer.entity.MooshroomRenderer;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


public class EntityRenderRegistry {

    @OnlyIn(Dist.CLIENT)
    public static void clientInit(FMLClientSetupEvent event) {
        for(String name : IConfigHandler.getResourceNames()){
            EntityType<EntityResourceMooshroom> type = IConfigHandler.getMooshroom(name);
            RenderingRegistry.registerEntityRenderingHandler(type, MooshroomRenderer::new);
        }
    }

}
