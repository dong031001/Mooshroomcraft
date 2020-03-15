package com.enigtech.mooshroomcraft.render;

import com.enigtech.mooshroomcraft.IConfigHandler;
import com.enigtech.mooshroomcraft.Mooshroomcraft;
import com.enigtech.mooshroomcraft.block.BlockRegistry;
import com.enigtech.mooshroomcraft.block.BlockResourceMushroom;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Mooshroomcraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockRenderRegistry {
    @SubscribeEvent
    public static void onItemColorsInit(ColorHandlerEvent.Block event){
        BlockColors blockColors = event.getBlockColors();
        Block block = BlockRegistry.BLOCK_RESOURCE_MUSHROOM;
        RenderTypeLookup.setRenderLayer(block, RenderType.getCutoutMipped());
        blockColors.register((state, light, pos, layer)->{
            indicate("ON RENDERING");
            //light.getTileEntity(pos).getWorld().notifyBlockUpdate(pos, state, state, Constants.BlockFlags.DEFAULT);
            BlockResourceMushroom.TileEntityMushroom tileEntity = (BlockResourceMushroom.TileEntityMushroom) light.getTileEntity(pos);
            indicate(tileEntity.toString());
            indicate(tileEntity.resource);
            if(tileEntity.resource!=null) return IConfigHandler.getColor(tileEntity.resource);
            return -1;
        }, block);
    }

    private static void indicate(String message){
        System.out.println(message);
    }
}
