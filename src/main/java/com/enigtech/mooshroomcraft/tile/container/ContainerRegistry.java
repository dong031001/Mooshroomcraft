package com.enigtech.mooshroomcraft.tile.container;

import com.enigtech.mooshroomcraft.Mooshroomcraft;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
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
            return new ContainerStewDistiller(windowId, Mooshroomcraft.proxy.getClientWorld(), blockPos, inv, Mooshroomcraft.proxy.getClientPlayer());
        }).setRegistryName("stew_distiller"));
    }
}
