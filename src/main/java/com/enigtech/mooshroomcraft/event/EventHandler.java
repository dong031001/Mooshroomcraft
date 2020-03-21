package com.enigtech.mooshroomcraft.event;

import com.enigtech.mooshroomcraft.IConfigHandler;
import com.enigtech.mooshroomcraft.Mooshroomcraft;
import com.enigtech.mooshroomcraft.entity.EntityMooshroomWrapper;
import com.enigtech.mooshroomcraft.entity.EntityRegistry;
import com.enigtech.mooshroomcraft.entity.EntityResourceMooshroom;
import com.enigtech.mooshroomcraft.item.ItemResourceMushroomStew;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Mooshroomcraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {
    @SubscribeEvent
    public static void onMushroomStewEaten(LivingEntityUseItemEvent.Finish event){
        if(event.getEntityLiving() instanceof ServerPlayerEntity&&event.getItem().getItem() instanceof ItemResourceMushroomStew){
            ServerPlayerEntity player = (ServerPlayerEntity) event.getEntityLiving();
            ItemStack stack = event.getItem();
            if(stack.hasTag() || stack.getTag().contains("resource")){
                for(EffectInstance effectInstance:IConfigHandler.getEffectInstances(stack.getTag().getString("resource")))
                    player.addPotionEffect(effectInstance);
            }
        }
    }

    @SubscribeEvent
    public static void onMooshroomSpawn(EntityJoinWorldEvent event){
        if(event.getEntity() instanceof MooshroomEntity&&!(event.getEntity() instanceof EntityMooshroomWrapper)&&!(event.getEntity() instanceof EntityResourceMooshroom)){
            Entity target = event.getEntity();
            MooshroomEntity mooshroom = EntityRegistry.MOOSHROOM_WRAPPER_ENTITY_TYPE.create(event.getWorld());
            mooshroom.copyLocationAndAnglesFrom(target);
            target.remove();
            mooshroom.setNoAI(((CowEntity) target).isAIDisabled());
            event.getWorld().addEntity(mooshroom);
        }
    }
}
