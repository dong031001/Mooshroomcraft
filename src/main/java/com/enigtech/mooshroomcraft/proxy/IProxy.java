package com.enigtech.mooshroomcraft.proxy;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public interface IProxy {
    World getClientWorld();
    PlayerEntity getClientPlayer();

    void setup(FMLCommonSetupEvent event);
}
