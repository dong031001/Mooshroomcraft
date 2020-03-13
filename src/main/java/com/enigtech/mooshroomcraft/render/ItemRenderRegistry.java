package com.enigtech.mooshroomcraft.render;

import com.enigtech.mooshroomcraft.IConfigHandler;
import com.enigtech.mooshroomcraft.Mooshroomcraft;
import com.enigtech.mooshroomcraft.item.ColoredItem;
import com.enigtech.mooshroomcraft.item.ItemMoosher;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = Mooshroomcraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@OnlyIn(Dist.CLIENT)
public class ItemRenderRegistry {
    @SubscribeEvent
    public static void onItemColorsInit(ColorHandlerEvent.Item event){
        ItemColors itemColors = event.getItemColors();
        for(String resourceName: IConfigHandler.getResourceNames()){
            //registerItemColor(itemColors, IConfigHandler.getMoosher(resourceName));
            registerItemColor(itemColors, IConfigHandler.getMushroomStew(resourceName));
        }
    }

    static void registerItemColor(ItemColors itemColors, ColoredItem item){
        //为Layer1上色
        itemColors.register((stack, layer) -> (layer == 0)? -1 : item.getColor(), item);
    }
}
