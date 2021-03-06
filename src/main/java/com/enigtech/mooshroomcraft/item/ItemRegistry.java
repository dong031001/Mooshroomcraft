package com.enigtech.mooshroomcraft.item;


import com.enigtech.mooshroomcraft.Mooshroomcraft;
import com.enigtech.mooshroomcraft.block.BlockRegistry;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Mooshroomcraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemRegistry {

    public static final ItemGroup ITEM_GROUP = new MooshroomcraftItemGroup("Mooshroomcraft");

    public static final Item MOOSHER = new ItemMoosher().setRegistryName("moosher");
    public static final Item MUSHROOM_STEW = new ItemResourceMushroomStew().setRegistryName("mushroom_stew");
    public static final Item MUSHROOM = new ItemMushroom().setRegistryName("mushroom");
    public static final Item ITEM_STEW_DISTILLER = new BlockItem(BlockRegistry.BLOCK_STEW_DISTILLER, new Item.Properties().group(ITEM_GROUP)).setRegistryName("stew_distiller");

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        event.getRegistry().register(MOOSHER);
        event.getRegistry().register(MUSHROOM);
        event.getRegistry().register(MUSHROOM_STEW);
        event.getRegistry().register(ITEM_STEW_DISTILLER);
    }

    public static class MooshroomcraftItemGroup extends ItemGroup{

        public MooshroomcraftItemGroup(String name) {
            super(name);
        }

        @Override
        public ItemStack createIcon() {
            return new ItemStack(MUSHROOM);
        }
    }
}
