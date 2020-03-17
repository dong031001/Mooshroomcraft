package com.enigtech.mooshroomcraft.proxy;

import com.enigtech.mooshroomcraft.tile.container.ContainerStewDistiller;
import com.enigtech.mooshroomcraft.tile.container.ScreenStewDistiller;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ClientProxy implements IProxy {
    @Override
    public World getClientWorld() {
        return Minecraft.getInstance().world;
    }

    @Override
    public void setup(FMLCommonSetupEvent event) {
        ScreenManager.registerFactory(ContainerStewDistiller.CONTAINER_TYPE_STEW_DISTILLER, ScreenStewDistiller::new);
    }

    @Override
    public PlayerEntity getClientPlayer() {
        return Minecraft.getInstance().player;
    }
}
