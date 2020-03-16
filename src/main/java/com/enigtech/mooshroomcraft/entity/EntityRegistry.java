package com.enigtech.mooshroomcraft.entity;

import com.enigtech.mooshroomcraft.Mooshroomcraft;
import net.minecraft.entity.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Mooshroomcraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRegistry {

    private static List<EntityType<? extends Entity>> entityTypeList = new ArrayList<>();
    private static List<Item> spawnEggList = new ArrayList<>();

    public final static EntityType<EntityResourceMooshroom> RESOURCE_MOOSHROOM_ENTITY_TYPE = createMooshroomType("mooshroom", new ResourceMooshroomFactory(), 0.9F, 1.4F, 0x000000, 0xFFFFFF);

    public static EntityType<EntityResourceMooshroom> createMooshroomType(String registryName, EntityType.IFactory<EntityResourceMooshroom> factory, float width, float height, int eggPrimary, int eggSecondary) {

        ResourceLocation location = new ResourceLocation(Mooshroomcraft.MOD_ID, registryName);

        EntityType<EntityResourceMooshroom> entity = EntityType.Builder.create(factory, EntityClassification.CREATURE)
                .size(width, height)
                .setTrackingRange(64)
                .setUpdateInterval(1)
                .build(location.toString());

        entity.setRegistryName(location);
        entityTypeList.add(entity);

        Item spawnEgg = new SpawnEggItem(entity, eggPrimary, eggSecondary, (new Item.Properties()).group(ItemGroup.MISC));
        spawnEgg.setRegistryName(new ResourceLocation(Mooshroomcraft.MOD_ID, registryName + "_spawn_egg"));
        spawnEggList.add(spawnEgg);

        return entity;
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        for (EntityType<? extends Entity> entity : entityTypeList) event.getRegistry().register(entity);
    }

    @SubscribeEvent
    public static void registerSpawnEggs(RegistryEvent.Register<Item> event) {
        for (Item spawnEgg : spawnEggList) event.getRegistry().register(spawnEgg);
    }
}
