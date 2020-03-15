package com.enigtech.mooshroomcraft.item;


import com.enigtech.mooshroomcraft.IConfigHandler;
import com.enigtech.mooshroomcraft.Mooshroomcraft;
import com.enigtech.mooshroomcraft.block.BlockRegistry;
import com.enigtech.mooshroomcraft.entity.EntityRegistry;
import net.minecraft.entity.EntityType;
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

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        event.getRegistry().register(MOOSHER);
        event.getRegistry().register(MUSHROOM);
        event.getRegistry().register(MUSHROOM_STEW);
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
