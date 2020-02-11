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

@KiwiModule
public class MainModule extends AbstractModule {

    public static final Item MOOSHER = new ModItem(new Item.Properties().rarity(Rarity.EPIC)){
        @Override
        public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
            if(target instanceof CowEntity&&!(target instanceof MooshroomEntity)&&(!playerIn.world.isRemote)){
                if(!target.isChild()){
                    EntityIronMooshroom mooshroom = EntityRegistry.IRON_MOOSHROOM.create(playerIn.world);
                    mooshroom.copyLocationAndAnglesFrom(target);
                    target.remove();
                    mooshroom.setNoAI(((CowEntity) target).isAIDisabled());
                    playerIn.world.addEntity(mooshroom);
                    stack.shrink(1);
                    return true;
                }
            }
            return super.itemInteractionForEntity(stack, playerIn, target, hand);
        }
    };

    public static final Item IRON_MUSHROOM_STEW = new ModItem(new Item.Properties().rarity(Rarity.COMMON));
    public static final Item CONCENTRATED_MUSHROOM_STEW = new ModItem(new Item.Properties().rarity(Rarity.COMMON));

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void clientInit(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.IRON_MOOSHROOM, MooshroomRenderer::new);
    }

    @Override
    protected void serverInit(FMLServerStartingEvent event) {
        //TODO
    }
}