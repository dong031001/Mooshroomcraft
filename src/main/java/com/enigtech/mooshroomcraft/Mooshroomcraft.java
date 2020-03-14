package com.enigtech.mooshroomcraft;

import com.enigtech.mooshroomcraft.render.EntityRenderRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("mooshroomcraft")
public class Mooshroomcraft {
    public static final String MOD_ID = "mooshroomcraft";
    public static final String MOD_NAME = "Mooshroomcraft";

    public Mooshroomcraft(){
        IConfigHandler.init();
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(EntityRenderRegistry::clientInit);
    }
}
