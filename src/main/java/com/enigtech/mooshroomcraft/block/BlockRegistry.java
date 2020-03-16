package com.enigtech.mooshroomcraft.block;

import com.enigtech.mooshroomcraft.Mooshroomcraft;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.enigtech.mooshroomcraft.block.BlockResourceMushroom.TileEntityMushroom;

@Mod.EventBusSubscriber(modid = Mooshroomcraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockRegistry {
    public static final Block BLOCK_RESOURCE_MUSHROOM = new BlockResourceMushroom(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().sound(SoundType.PLANT).lightValue(1)).setRegistryName("mushroom");

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event){
        event.getRegistry().register(BLOCK_RESOURCE_MUSHROOM);
    }

    @SubscribeEvent
    public static void onTileRegister(RegistryEvent.Register<TileEntityType<?>> event){
        event.getRegistry().register(TileEntityType.Builder.create(TileEntityMushroom::new, BLOCK_RESOURCE_MUSHROOM).build(null).setRegistryName("mooshroomcraft:mushroom"));
    }

    private static <T extends TileEntity> TileEntityType<T> register(String key, TileEntityType.Builder<T> builder) {
        Type<?> type = null;

        try {
            type = DataFixesManager.getDataFixer().getSchema(DataFixUtils.makeKey(SharedConstants.getVersion().getWorldVersion())).getChoiceType(TypeReferences.BLOCK_ENTITY, key);
        } catch (IllegalArgumentException illegalargumentexception) {
            if (SharedConstants.developmentMode) {
                throw illegalargumentexception;
            }
        }

        return Registry.register(Registry.BLOCK_ENTITY_TYPE, key, builder.build(type));
    }
}
