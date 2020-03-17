package com.enigtech.mooshroomcraft.tile.container;

import com.enigtech.mooshroomcraft.Mooshroomcraft;
import com.enigtech.mooshroomcraft.tile.TileEntityStewDistiller;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = Mooshroomcraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ContainerRegistry {

    @SubscribeEvent
    public static void registerContainers(final RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos blockPos = data.readBlockPos();
            World world = Mooshroomcraft.proxy.getClientWorld();
            return new ContainerStewDistiller(windowId,
                    world,
                    blockPos,
                    inv,
                    Mooshroomcraft.proxy.getClientPlayer(),
                    new IntArray(4));
        }).setRegistryName("stew_distiller"));
    }
}
