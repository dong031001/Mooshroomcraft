package com.enigtech.mooshroomcraft.render;

import com.enigtech.mooshroomcraft.IConfigHandler;
import com.enigtech.mooshroomcraft.Mooshroomcraft;
import com.enigtech.mooshroomcraft.MooshroomcraftPacketHandler;
import com.enigtech.mooshroomcraft.block.BlockRegistry;
import com.enigtech.mooshroomcraft.block.BlockResourceMushroom;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.client.event.ColorHandlerEvent;
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
            if(layer==0) return -1;
            //indicate("ON RENDERING");
            //light.getTileEntity(pos).getWorld().notifyBlockUpdate(pos, state, state, Constants.BlockFlags.DEFAULT);

            BlockResourceMushroom.TileEntityMushroom tileEntity = (BlockResourceMushroom.TileEntityMushroom) light.getTileEntity(pos);

            if(tileEntity!=null){
                if(tileEntity.resource==null) MooshroomcraftPacketHandler.INSTANCE.sendToServer("START_"+pos.getX()+"_"+pos.getY()+"_"+pos.getZ());
                CompoundNBT nbt = new CompoundNBT();
                tileEntity.write(nbt);
                if(nbt.contains("resource")) return IConfigHandler.getColor(nbt.getString("resource"));
            }

            return -1;
        }, block);
    }

    private static void indicate(String message){
        System.out.println(message);
    }
}
