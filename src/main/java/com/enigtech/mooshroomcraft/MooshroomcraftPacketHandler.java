package com.enigtech.mooshroomcraft;

import com.enigtech.mooshroomcraft.block.BlockResourceMushroom;
import com.mojang.brigadier.Message;
import net.minecraft.block.Blocks;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.lwjgl.system.windows.MSG;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;

public class MooshroomcraftPacketHandler {

    private int index = 0;

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Mooshroomcraft.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public MooshroomcraftPacketHandler(){
        INSTANCE.registerMessage(index++, String.class, Coder::encode, Coder::decode, Coder::onHandleString);
        INSTANCE.registerMessage(index++, BlockPos.class, Coder::encodeBlockPos, Coder::decodeBlockPos, Coder::onHandleBlockPos);
    }

    public static class Coder{

        public static void encodeBlockPos(BlockPos pos, PacketBuffer packetBuffer){
            packetBuffer.writeBlockPos(pos);
        }

        public static BlockPos decodeBlockPos(PacketBuffer packetBuffer){return packetBuffer.readBlockPos();}

        public static void onHandleBlockPos(BlockPos pos, Supplier<NetworkEvent.Context> supplier){
            supplier.get().enqueueWork(()->{
                System.out.println("ON BLOCKPOS RECEIVED");
                INetHandler handler = supplier.get().getNetworkManager().getNetHandler();
                if(handler instanceof ServerPlayNetHandler) {
                    System.out.println("REFRESHING: ON SERVER RECEIVE");
                    ServerWorld world = (ServerWorld) ((ServerPlayNetHandler) handler).player.world;
                    TileEntity tileEntity = world.getTileEntity(pos);
                    world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), Constants.BlockFlags.DEFAULT_AND_RERENDER);
                }
            });
            supplier.get().setPacketHandled(true);
        }

        public static void encode(String string, PacketBuffer packetBuffer){
            packetBuffer.writeString(string);
        }
        public static String decode(PacketBuffer packetBuffer){
            return packetBuffer.readString();
        }
        public static void onHandleString(String string, Supplier<NetworkEvent.Context> supplier){
            supplier.get().enqueueWork(()->{
                INetHandler handler = supplier.get().getNetworkManager().getNetHandler();
                World world;
                String[] pieces = string.split("_");
                if(string.startsWith("_")){
                    return;
                }

                int[] positions = new int[]{Integer.parseInt(pieces[1]),Integer.parseInt(pieces[2]),Integer.parseInt(pieces[3])};
                BlockPos pos = new BlockPos(positions[0],positions[1],positions[2]);

                if(handler instanceof ServerPlayNetHandler) {
                    world = ((ServerPlayNetHandler) supplier.get().getNetworkManager().getNetHandler()).player.world;
                    TileEntity tileEntity = world.getTileEntity(pos);
                    if(pieces[0].equals("FINISHED")){
                        world.notifyBlockUpdate(pos, tileEntity.getBlockState(), tileEntity.getBlockState(), Constants.BlockFlags.DEFAULT_AND_RERENDER);
                        INSTANCE.reply(String.join("_",pieces), supplier.get());
                        return;
                    }
                    CompoundNBT tag = new CompoundNBT();
                    tileEntity.write(tag);
                    pieces[0] = tag.getString("resource");
                    INSTANCE.reply(String.join("_",pieces), supplier.get());

                }else if(handler instanceof ClientPlayNetHandler){
                    world = ((ClientPlayNetHandler) handler).getWorld();
                    TileEntity tileEntity = world.getTileEntity(pos);
                    if(pieces[0].equals("FINISHED")){
                        world.notifyBlockUpdate(pos, tileEntity.getBlockState(), tileEntity.getBlockState(), Constants.BlockFlags.DEFAULT_AND_RERENDER);
                        return;
                    }
                    CompoundNBT tag = new CompoundNBT();
                    tag.putString("resource", pieces[0]);
                    BlockResourceMushroom.TileEntityMushroom tileEntityMushroom = (BlockResourceMushroom.TileEntityMushroom) world.getTileEntity(pos);
                    String before = tileEntityMushroom.resource;
                    tileEntityMushroom.read(tag);
                    if(before!=null && before.equals(tileEntityMushroom.resource)) return;
                    System.out.println("AFTER: "+tileEntityMushroom.resource);
                    pieces[0] = "FINISHED";
                    INSTANCE.reply(String.join("_",pieces), supplier.get());
                }
            });
            supplier.get().setPacketHandled(true);
        }
    }
}
