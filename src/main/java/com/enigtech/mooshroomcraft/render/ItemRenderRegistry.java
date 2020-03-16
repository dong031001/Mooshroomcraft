package com.enigtech.mooshroomcraft.render;

import com.enigtech.mooshroomcraft.IConfigHandler;
import com.enigtech.mooshroomcraft.Mooshroomcraft;
import com.enigtech.mooshroomcraft.item.ItemRegistry;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
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
        registerItemColor(itemColors, ItemRegistry.MUSHROOM_STEW);
        registerItemColor(itemColors, ItemRegistry.MOOSHER);
        registerItemColor(itemColors, ItemRegistry.MUSHROOM);
    }

    static void registerItemColor(ItemColors itemColors, Item item){
        //为Layer1上色
        itemColors.register((stack, layer) -> (layer == 0)? -1
                : stack.getOrCreateTag().isEmpty()? -1
                : stack.getTag().contains("BlockEntityTag")? IConfigHandler.getColor(stack.getTag().getCompound("BlockEntityTag").getString("resource"))
                : stack.getTag().contains("resource")? IConfigHandler.getColor(stack.getTag().getString("resource"))
                : -1
                , item);
    }

    private static void registerItem(ItemColors itemColors, Item item, int color){
        itemColors.register((stack, layer) -> (layer == 0)? -1 : color, item);
    }
}
