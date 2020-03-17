package com.enigtech.mooshroomcraft.proxy;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ServerProxy implements IProxy {
    @Override
    public World getClientWorld() {
        throw new IllegalStateException("NO WORLD FOR U!");
    }

    @Override
    public PlayerEntity getClientPlayer() {
        throw new IllegalStateException("NO PLAYER FOR U!");
    }

    @Override
    public void setup(FMLCommonSetupEvent event) {

    }
}
