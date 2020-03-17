package com.enigtech.mooshroomcraft.tile;

import com.enigtech.mooshroomcraft.IConfigHandler;
import com.enigtech.mooshroomcraft.Mooshroomcraft;
import com.enigtech.mooshroomcraft.item.ItemRegistry;
import com.enigtech.mooshroomcraft.tile.container.ContainerStewDistiller;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import jdk.nashorn.internal.ir.annotations.Ignore;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityStewDistiller extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    public static final int INDEX_FUEL = 0;
    public static final int INDEX_FUEL_TOTAL = 1;
    public static final int INDEX_PROGRESS = 2;
    public static final int INDEX_PROGRESS_TOTAL = 3;

    @ObjectHolder(Mooshroomcraft.MOD_ID + ":stew_distiller")
    public static final TileEntityType<TileEntityStewDistiller> TYPE = null;

    private static final ITextComponent TITLE = new TranslationTextComponent("gui."+ Mooshroomcraft.MOD_ID+".stew_distiller", ObjectArrays.EMPTY_ARRAY);


    private int progress;
    private int fuel;
    private int lastFuel;
    private int totalProgress = 100;

    protected IIntArray distillerData = new IIntArray(){
        public int get(int index) {
            switch(index){
                case INDEX_FUEL: return TileEntityStewDistiller.this.fuel;
                case INDEX_FUEL_TOTAL: return TileEntityStewDistiller.this.lastFuel;
                case INDEX_PROGRESS: return TileEntityStewDistiller.this.progress;
                case INDEX_PROGRESS_TOTAL: return TileEntityStewDistiller.this.totalProgress;
            }
            throw new IllegalStateException("NO DATA FOR YOU");
        }

        public void set(int index, int value) {
            switch(index){
                case INDEX_FUEL: TileEntityStewDistiller.this.fuel = value;
                case INDEX_FUEL_TOTAL: TileEntityStewDistiller.this.lastFuel = value;
                case INDEX_PROGRESS: TileEntityStewDistiller.this.progress = value;
                case INDEX_PROGRESS_TOTAL: TileEntityStewDistiller.this.totalProgress = value;
            }
        }

        public int size() { return 4; }
    };

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
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if (stack.isEmpty())
                return ItemStack.EMPTY;

            validateSlotIndex(slot);

            ItemStack existing = this.stacks.get(slot);

            int limit = getStackLimit(slot, stack);

            if (!existing.isEmpty())
            {
                if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
                    return stack;

                limit -= existing.getCount();
            }

            if (limit <= 0)
                return stack;

            boolean reachedLimit = stack.getCount() > limit;

            if (!simulate)
            {
                if (existing.isEmpty())
                {
                    this.stacks.set(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
                }
                else
                {
                    existing.grow(reachedLimit ? limit : stack.getCount());
                }
                onContentsChanged(slot);
            }

            return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount()- limit) : ItemStack.EMPTY;
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

    public int getProgress(){
        return progress;
    }

    public int getTotalProgress(){
        return totalProgress;
    }

    public boolean onBurn(){
        return fuel>0;
    }

    public int getFuel(){
        return fuel;
    }

    public int getTotalFuel(){
        return lastFuel;
    }

    public IIntArray getDistillerData() {
        return distillerData;
    }

    public TileEntityStewDistiller() {
        super(TYPE);
    }

    @Override
    public void read(CompoundNBT nbt){
        super.read(nbt);
        this.fuel = nbt.getInt("fuel");
        this.progress = nbt.getInt("progress");
        this.lastFuel = nbt.getInt("lastFuel");

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
        nbt.putInt("lastFuel", this.lastFuel);

        return super.write(nbt);
    }


    @Override
    public void tick() {
        if(!world.isRemote){
            if(fuelCheck()|progressCheck()|finishedCheck()) markDirty();
        }
    }

    private boolean fuelCheck(){
        ItemStack fuelInSlot = fuelHandler.getStackInSlot(0);
        //如果有燃料且机器剩余燃烧时值为0，消耗燃料并填充到机器剩余燃烧时值中
        if(fuel == 0 && fuelInSlot.getCount() > 0 && fuelInSlot.getItem()!= Items.AIR && fuelInSlot!=ItemStack.EMPTY){
            lastFuel = ForgeHooks.getBurnTime(fuelInSlot) + 1;
            lastFuel--;
            fuel+=ForgeHooks.getBurnTime(fuelInSlot);
            fuelInSlot.shrink(1);
            return true;
        }
        return false;
    }

    private boolean progressCheck(){
        ItemStack inputInSlot = inputHandler.getStackInSlot(0);
        //若机器有剩余燃料时值且输入符合要求，开始处理原料
        if(fuel>0){
            fuel--;
            if(inputInSlot.getCount()>0
                    && inputInSlot.getItem()!=Items.AIR
                    && inputInSlot.hasTag()
                    && addOrSetStackInHandler(outputHandler, IConfigHandler.getResultFromStew(inputInSlot),true) != IConfigHandler.getResultFromStew(inputInSlot)){
                progress++;
            }else if(inputInSlot.getItem()==Items.AIR){
                progress = 0;
            }
            return true;
        }
        return false;
    }

    private boolean finishedCheck(){
        ItemStack inputInSlot = inputHandler.getStackInSlot(0);
        if(progress >= totalProgress){
            progress = 0;

            addOrSetStackInHandler(outputHandler, IConfigHandler.getResultFromStew(inputInSlot), false);
            addOrSetStackInHandler(bowlHandler, new ItemStack(Items.BOWL), false);

            inputInSlot.shrink(1);

            return true;
        }
        return false;
    }

    private static ItemStack addOrSetStackInHandler(ItemStackHandler handler, ItemStack stack, boolean simulation){
        ItemStack inSlot = handler.getStackInSlot(0);
        //若输出槽已满，不做任何操作
        if(inSlot.getItem().getMaxStackSize()==inSlot.getCount()) return stack;
        //若输出槽没有物品，直接转移
        if(inSlot.getItem()==Items.AIR||inSlot==ItemStack.EMPTY){
            if(!simulation) handler.setStackInSlot(0, stack);
            return ItemStack.EMPTY;
        }
        //若物品相同，拥有相同标签或都没有标签进入处理阶段
        if(inSlot.getItem()==stack.getItem()
                && (inSlot.hasTag() && stack.hasTag() && inSlot.getTag().equals(stack.getTag()) ||(!inSlot.hasTag()&&!stack.hasTag()))){
            int afterAmount = inSlot.getCount()+stack.getCount();
            //若数量相加大于最大数量，优先注满输出槽
            if(afterAmount > inSlot.getItem().getMaxStackSize()){
                if(!simulation) inSlot.setCount(inSlot.getItem().getMaxStackSize());
                stack.setCount(afterAmount-inSlot.getItem().getMaxStackSize());
                return stack;
            }else{
                if(!simulation) inSlot.setCount(afterAmount);
                return ItemStack.EMPTY;
            }
        }
        return stack;
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
        return TITLE;
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new ContainerStewDistiller(i, world, pos, playerInventory, playerEntity, distillerData);
    }
}
