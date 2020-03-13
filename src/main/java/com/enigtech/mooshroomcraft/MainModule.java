package com.enigtech.mooshroomcraft;

import com.enigtech.mooshroomcraft.entity.EntityIronMooshroom;
import net.minecraft.block.Block;
import net.minecraft.block.MushroomBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.entity.MooshroomRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.Hand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import snownee.kiwi.AbstractModule;
import snownee.kiwi.KiwiModule;
import snownee.kiwi.block.ModBlock;
import snownee.kiwi.item.ModItem;
import com.enigtech.mooshroomcraft.entity.EntityRegistry;

import java.io.File;
import java.io.IOException;

@KiwiModule
public class MainModule extends AbstractModule {

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void clientInit(FMLClientSetupEvent event) {
        //RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.IRON_MOOSHROOM, MooshroomRenderer::new);
    }

    @Override
    protected void serverInit(FMLServerStartingEvent event) {
    }

    @Override
    protected void preInit(){
        IConfigHandler.init();
    }
}