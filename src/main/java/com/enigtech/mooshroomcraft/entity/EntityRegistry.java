package com.enigtech.mooshroomcraft.entity;

import com.enigtech.mooshroomcraft.Mooshroomcraft;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.MooshroomEntity;
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

    public final static EntityType<EntityResourceMooshroom> RESOURCE_MOOSHROOM_ENTITY_TYPE = createMooshroomType("mooshroom", new ResourceMooshroomFactory(), 0.9F, 1.4F);
    public final static EntityType<EntityMooshroomWrapper> MOOSHROOM_WRAPPER_ENTITY_TYPE = createTypeWithNoEgg("mooshroom_wrapper", EntityMooshroomWrapper::new, 0.9F, 1.4F);

    public static EntityType<EntityResourceMooshroom> createMooshroomType(String registryName, EntityType.IFactory<EntityResourceMooshroom> factory, float width, float height) {
        return createTypeWithNoEgg(registryName, factory, width, height);
    }

    public static <T extends AnimalEntity> EntityType<T> createTypeWithNoEgg(String registryName, EntityType.IFactory<T> factory, float width, float height) {
        return createType(registryName, factory, width, height, false, 0, 0);
    }

    private static <T extends AnimalEntity> EntityType<T> createType(String registryName, EntityType.IFactory<T> factory, float width, float height, boolean egg, int eggPrimary, int eggSecondary) {
        ResourceLocation location = new ResourceLocation(Mooshroomcraft.MOD_ID, registryName);
        EntityType<T> entity = EntityType.Builder.create(factory, EntityClassification.CREATURE).size(width, height).setTrackingRange(64).setUpdateInterval(1).build(location.toString());
        entity.setRegistryName(location);
        entityTypeList.add(entity);
        if(!egg) {
            Item spawnEgg = new SpawnEggItem(entity, eggPrimary, eggSecondary, (new Item.Properties()).group(ItemGroup.MISC));
            spawnEgg.setRegistryName(new ResourceLocation(Mooshroomcraft.MOD_ID, registryName + "_spawn_egg"));
            spawnEggList.add(spawnEgg);
        }
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
