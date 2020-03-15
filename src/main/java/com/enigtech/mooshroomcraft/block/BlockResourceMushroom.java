package com.enigtech.mooshroomcraft.block;

import com.enigtech.mooshroomcraft.IConfigHandler;
import com.enigtech.mooshroomcraft.Mooshroomcraft;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.MushroomBlock;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.state.IProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class BlockResourceMushroom extends MushroomBlock {

    @ObjectHolder(Mooshroomcraft.MOD_ID + ":mushroom")
    public static final TileEntityType<TileEntityMushroom> TYPE = null;

    public BlockResourceMushroom(Properties properties) {
        super(properties);
    }

    @Override
    public boolean func_226940_a_(ServerWorld p_226940_1_, BlockPos p_226940_2_, BlockState p_226940_3_, Random p_226940_4_){
        return false;
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return false;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return false;
    }

    @Override
    public void grow(ServerWorld p_225535_1_, Random p_225535_2_, BlockPos p_225535_3_, BlockState p_225535_4_) { }

    @Override
    public boolean needsPostProcessing(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    @Override
    public boolean hasTileEntity(BlockState state){
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityMushroom();
    }

    public static class TileEntityMushroom extends TileEntity{
        public String resource;

        @Override
        public void read(CompoundNBT tag) {
            super.read(tag);
            if(tag.contains("resource"))
                this.resource = tag.getString("resource");
        }

        @Override
        public CompoundNBT write(CompoundNBT tag){
            super.write(tag);
            if(resource!=null)
                tag.putString("resource", resource);
            return tag;
        }

        public TileEntityMushroom(){
            super(TYPE);
        }

        @Override
        @Nullable
        public SUpdateTileEntityPacket getUpdatePacket() {
            CompoundNBT tag = new CompoundNBT();
            tag.putString("resource", resource);
            return new SUpdateTileEntityPacket(this.pos,1,tag);
        }

        @Override
        public void onDataPacket(net.minecraft.network.NetworkManager net, net.minecraft.network.play.server.SUpdateTileEntityPacket pkt){
            if(pkt.getNbtCompound().contains("resource")) resource = pkt.getNbtCompound().getString("resource");
        }

        @Override
        public CompoundNBT getUpdateTag() {
            System.out.println("SERVER SIDE: "+resource);
            CompoundNBT tag = new CompoundNBT();
            if(this.resource!=null) tag.putString("resource", resource);
            return tag;
        }

        @Override
        public void handleUpdateTag(CompoundNBT data) {
            System.out.println("CLIENT SIDE: "+resource);
            if(data.contains("resource")) this.resource=data.getString("resource");
        }

        @Override
        @OnlyIn(Dist.DEDICATED_SERVER)
        public void onLoad() {
            super.onLoad();
            this.refresh();
        }

        public void refresh()
        {
            if (hasWorld() && !world.isRemote)
            {
                System.out.println("REFRESHING WITH RESOURCE: "+resource);
                BlockState state = world.getBlockState(pos);
                requestModelDataUpdate();
                BlockState grass = world.getBlockState(pos.down());
                world.notifyBlockUpdate(pos.down(),  grass, grass, Constants.BlockFlags.DEFAULT);
            }
        }
    }
}

