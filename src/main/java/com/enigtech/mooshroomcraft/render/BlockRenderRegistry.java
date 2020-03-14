package com.enigtech.mooshroomcraft.render;

import com.enigtech.mooshroomcraft.IConfigHandler;
import com.enigtech.mooshroomcraft.Mooshroomcraft;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Mooshroomcraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@OnlyIn(Dist.CLIENT)
public class BlockRenderRegistry {
    @SubscribeEvent
    public static void onItemColorsInit(ColorHandlerEvent.Block event){
        BlockColors blockColors = event.getBlockColors();
        for(String resourceName: IConfigHandler.getResourceNames()){
            Block block = IConfigHandler.getMushroomBlock(resourceName);
            RenderTypeLookup.setRenderLayer(block, RenderType.getCutoutMipped());
            blockColors.register((state, light, pos, layer)->(layer==0? -1:IConfigHandler.getColor(resourceName)), block);
        }
    }
}
