package com.enigtech.mooshroomcraft;

import com.enigtech.mooshroomcraft.render.EntityRenderRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.io.IOException;

@Mod("mooshroomcraft")
public class Mooshroomcraft {
    public static final String MOD_ID = "mooshroomcraft";
    public static final String MOD_NAME = "Mooshroomcraft";
    public static final String CONFIG_FILE = "config/mooshroomcraft/resources.json";
    public static final String CONFIG_FOLDER = "config/mooshroomcraft";

    public Mooshroomcraft() throws IOException {
        IConfigHandler.init();
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(EntityRenderRegistry::clientInit);
        new MooshroomcraftPacketHandler();
    }
}
