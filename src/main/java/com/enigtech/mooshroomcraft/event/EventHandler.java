package com.enigtech.mooshroomcraft.event;

import com.enigtech.mooshroomcraft.IConfigHandler;
import com.enigtech.mooshroomcraft.Mooshroomcraft;
import com.enigtech.mooshroomcraft.item.ItemResourceMushroomStew;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
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
    public static void onWorldLoad(WorldEvent.Load event){
        System.out.println("ON LOADING WORLD");
    }
}
