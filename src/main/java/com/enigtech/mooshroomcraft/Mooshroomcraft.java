package com.enigtech.mooshroomcraft;

import com.enigtech.mooshroomcraft.entity.EntityRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.entity.MooshroomRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("mooshroomcraft")
public class Mooshroomcraft {
    public static final String MOD_ID = "mooshroomcraft";
    public static final Logger LOGGER = LogManager.getLogger();

    public Mooshroomcraft(){
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    }
}
