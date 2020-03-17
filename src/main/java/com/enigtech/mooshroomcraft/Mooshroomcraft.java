package com.enigtech.mooshroomcraft;

import com.enigtech.mooshroomcraft.proxy.ClientProxy;
import com.enigtech.mooshroomcraft.proxy.IProxy;
import com.enigtech.mooshroomcraft.proxy.ServerProxy;
import com.enigtech.mooshroomcraft.render.EntityRenderRegistry;
import com.enigtech.mooshroomcraft.render.ScreenRenderRegistry;
import com.enigtech.mooshroomcraft.tile.container.ContainerStewDistiller;
import com.enigtech.mooshroomcraft.tile.container.ScreenStewDistiller;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.io.IOException;

@Mod("mooshroomcraft")
public class Mooshroomcraft {

    public static IProxy proxy = DistExecutor.runForDist(()->ClientProxy::new, ()->ServerProxy::new);

    public static final String MOD_ID = "mooshroomcraft";
    public static final String MOD_NAME = "Mooshroomcraft";
    public static final String CONFIG_FILE = "config/mooshroomcraft/resources.json";
    public static final String CONFIG_FOLDER = "config/mooshroomcraft";

    public Mooshroomcraft() throws IOException {
        IConfigHandler.init();
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(EntityRenderRegistry::clientInit);
        eventBus.addListener(this::setup);
        new MooshroomcraftPacketHandler();
    }
    private void setup(final FMLCommonSetupEvent event){
        proxy.setup(event);
    }
}
