package com.enigtech.mooshroomcraft.render;

import com.enigtech.mooshroomcraft.IConfigHandler;
import com.enigtech.mooshroomcraft.Mooshroomcraft;
import com.enigtech.mooshroomcraft.block.BlockResourceMushroom;
import com.enigtech.mooshroomcraft.entity.EntityRegistry;
import com.enigtech.mooshroomcraft.entity.EntityResourceMooshroom;
import com.enigtech.mooshroomcraft.entity.ResourceMooshroomFactory;
import com.enigtech.mooshroomcraft.render.entity.RenderFactoryResourceMooshroom;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Mooshroomcraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntityRenderRegistry {

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void clientInit(ModelRegistryEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.RESOURCE_MOOSHROOM_ENTITY_TYPE, new RenderFactoryResourceMooshroom());
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onBlockPlaced(RenderLivingEvent.Post event){
        if(event.getEntity() instanceof EntityResourceMooshroom){
            EntityResourceMooshroom entity = (EntityResourceMooshroom) event.getEntity();
        }
    }
/*
    @SubscribeEvent
    public static void onMooshroomMilk(PlayerInteractEvent.EntityInteractSpecific event){
        if(event.getTarget() instanceof MooshroomEntity && !(event.getTarget() instanceof EntityResourceMooshroom)){
            MooshroomEntity entity = (MooshroomEntity) event.getTarget();
            if(event.getPlayer().getHeldItemMainhand().getItem() == Items.BOWL && !entity.isChild()){
                if()
            }
        }
    }

    @SubscribeEvent
    public static void onMooshroomSpawn(EntityJoinWorldEvent event){
        if(!(event.getEntity() instanceof MooshroomEntity || event.getEntity() instanceof EntityResourceMooshroom)){
            return;
        }
        MooshroomEntity entity = (MooshroomEntity) event.getEntity();
        entity.writeAdditional(new CompoundNBT(){{putInt("time", 0);}});
    }
 */
}