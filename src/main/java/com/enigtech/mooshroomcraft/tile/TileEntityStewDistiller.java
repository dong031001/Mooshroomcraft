package com.enigtech.mooshroomcraft.tile;

import com.enigtech.mooshroomcraft.Mooshroomcraft;
import com.enigtech.mooshroomcraft.item.ItemRegistry;
import com.enigtech.mooshroomcraft.tile.container.ContainerStewDistiller;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityStewDistiller extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    @ObjectHolder(Mooshroomcraft.MOD_ID + ":stew_distiller")
    public static final TileEntityType<TileEntityStewDistiller> TYPE = null;

    public int progress;
    public int fuel;

    private ItemStackHandler inputHandler = new ItemStackHandler(1){
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return stack.getItem()==ItemRegistry.MUSHROOM_STEW;
        }
    };

    private ItemStackHandler fuelHandler = new ItemStackHandler(1){
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return ForgeHooks.getBurnTime(stack)>0;
        }
    };

    private ItemStackHandler outputHandler = new ItemStackHandler(1){
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return false;
        }
    };
    private ItemStackHandler bowlHandler = new ItemStackHandler(1){
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return false;
        }
    };

    private LazyOptional<ItemStackHandler> inputHandlerQuote = LazyOptional.of(()->inputHandler);
    private LazyOptional<ItemStackHandler> fuelHandlerQuote = LazyOptional.of(()->fuelHandler);
    private LazyOptional<ItemStackHandler> outputHandlerQuote = LazyOptional.of(()->outputHandler);
    private LazyOptional<ItemStackHandler> bowlHandlerQuote = LazyOptional.of(()->bowlHandler);

    public ItemStackHandler getInputHandler() {
        return inputHandler;
    }

    public ItemStackHandler getFuelHandler() {
        return fuelHandler;
    }

    public ItemStackHandler getOutputHandler() {
        return outputHandler;
    }

    public ItemStackHandler getBowlHandler() {
        return bowlHandler;
    }

    public TileEntityStewDistiller() {
        super(TYPE);
    }

    @Override
    public void read(CompoundNBT nbt){
        super.read(nbt);
        this.fuel = nbt.getInt("fuel");
        this.progress = nbt.getInt("progress");

        inputHandlerQuote.ifPresent(handler -> handler.deserializeNBT(nbt.getCompound("input")));
        outputHandlerQuote.ifPresent(handler -> handler.deserializeNBT(nbt.getCompound("output")));
        fuelHandlerQuote.ifPresent(handler -> handler.deserializeNBT(nbt.getCompound("fuelItem")));
        bowlHandlerQuote.ifPresent(handler -> handler.deserializeNBT(nbt.getCompound("bowl")));

    }

    @Override
    public CompoundNBT write(CompoundNBT nbt){
        inputHandlerQuote.ifPresent(itemStackHandler -> nbt.put("input", itemStackHandler.serializeNBT()));
        outputHandlerQuote.ifPresent(itemStackHandler -> nbt.put("output", itemStackHandler.serializeNBT()));
        fuelHandlerQuote.ifPresent(itemStackHandler -> nbt.put("fuelItem", itemStackHandler.serializeNBT()));
        bowlHandlerQuote.ifPresent(itemStackHandler -> nbt.put("bowl", itemStackHandler.serializeNBT()));

        nbt.putInt("fuel", this.fuel);
        nbt.putInt("progress", this.progress);

        return super.write(nbt);
    }


    @Override
    public void tick() {
        if(!world.isRemote){

        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            switch(side){
                case UP: return inputHandlerQuote.cast();
                case DOWN: return outputHandlerQuote.cast();
                case SOUTH: return bowlHandlerQuote.cast();
            }
            return fuelHandlerQuote.cast();
        }
        return super.getCapability(cap, side);
    }

    public <T> LazyOptional<T> getItemHandlerCapabilityByDirection(@Nullable Direction side) {
        return getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side).cast();
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new ContainerStewDistiller(i, world, pos, playerInventory, playerEntity);
        //return null;
    }
}
