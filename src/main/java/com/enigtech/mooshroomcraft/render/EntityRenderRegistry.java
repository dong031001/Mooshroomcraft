package com.enigtech.mooshroomcraft.render;

import com.enigtech.mooshroomcraft.Mooshroomcraft;
import com.enigtech.mooshroomcraft.entity.EntityRegistry;
import com.enigtech.mooshroomcraft.entity.EntityResourceMooshroom;
import com.enigtech.mooshroomcraft.render.entity.RenderFactoryResourceMooshroom;
import com.enigtech.mooshroomcraft.tile.container.ContainerStewDistiller;
import com.enigtech.mooshroomcraft.tile.container.ScreenStewDistiller;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Mooshroomcraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntityRenderRegistry {

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void clientInit(ModelRegistryEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.RESOURCE_MOOSHROOM_ENTITY_TYPE, new RenderFactoryResourceMooshroom());
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