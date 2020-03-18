package com.enigtech.mooshroomcraft.tile.container;


import com.enigtech.mooshroomcraft.Mooshroomcraft;
import com.enigtech.mooshroomcraft.block.BlockRegistry;
import com.enigtech.mooshroomcraft.item.ItemRegistry;
import com.enigtech.mooshroomcraft.tile.TileEntityStewDistiller;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nullable;

public class ContainerStewDistiller extends Container {
    @ObjectHolder("mooshroomcraft:stew_distiller")
    public static ContainerType<ContainerStewDistiller> CONTAINER_TYPE_STEW_DISTILLER = null;


    private TileEntityStewDistiller tileEntity;
    private IItemHandler playerInventory;
    private IIntArray distillerData;

    public ContainerStewDistiller(int id, World world, BlockPos blockPos, PlayerInventory playerInventory, PlayerEntity playerEntity, IIntArray distillerData) {
        super(CONTAINER_TYPE_STEW_DISTILLER, id);
        tileEntity = (TileEntityStewDistiller) world.getTileEntity(blockPos);
        this.playerInventory = new InvWrapper(playerInventory);

        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).ifPresent(handler -> addSlot(new SlotItemHandler(handler,0 ,56, 17)));
        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.DOWN).ifPresent(handler -> addSlot(new SlotItemHandler(handler,0 ,116, 35)));
        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.WEST).ifPresent(handler -> addSlot(new SlotItemHandler(handler,0,56, 53)));
        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.SOUTH).ifPresent(handler -> addSlot(new SlotItemHandler(handler,0,143, 35)));

        layoutPlayerInventorySlots(8,84);
        this.distillerData = distillerData;
        this.trackIntArray(this.distillerData);
    }

    private int addSlotRow(IItemHandler handler, int index, int x, int y, int amount, int dx){
        for(int i = 0; i < amount; i++){
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotGrid(IItemHandler handler, int index, int x, int y, int xAmount, int dx, int yAmount, int dy){
        for(int i = 0; i<yAmount; i++){
            index = addSlotRow(handler, index, x, y, xAmount, dx);
            y+=dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int startX, int startY){
        addSlotGrid(playerInventory, 9, startX, startY, 9, 18, 3, 18);
        startY+=58;
        addSlotRow(playerInventory, 0, startX, startY, 9, 18);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(playerIn.world, tileEntity.getPos()), playerIn, BlockRegistry.BLOCK_STEW_DISTILLER);
    }


    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        Slot slot = inventorySlots.get(index);

        if (slot == null || !slot.getHasStack()) {
            return null;
        }

        ItemStack newStack = slot.getStack(), oldStack = newStack.copy();

        boolean isMerged = false;

        if (index < 4) {
            isMerged = mergeItemStack(newStack, 4, 31, true);
        } else if (index < 31) {
            isMerged = mergeItemStack(newStack, 0, 4, false) || mergeItemStack(newStack, 31, 40, false);
        } else if (index < 40) {
            isMerged = mergeItemStack(newStack, 0, 4, false) || mergeItemStack(newStack, 4, 31, false);
        }

        if (!isMerged) {
            return null;
        }

        if (newStack.getCount() == 0) {
            slot.putStack(ItemStack.EMPTY);
        } else {
            slot.onSlotChanged();
        }

        slot.onTake(playerIn, newStack);

        return oldStack;
    }

    @OnlyIn(Dist.CLIENT)
    public int getProgressionScaled() {
        int i = distillerData.get(TileEntityStewDistiller.INDEX_PROGRESS);
        int j = distillerData.get(TileEntityStewDistiller.INDEX_PROGRESS_TOTAL);
        return j != 0 && i != 0 ? i * 24 / j : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public int getBurnLeftScaled() {
        float i = distillerData.get(TileEntityStewDistiller.INDEX_FUEL);
        float j = distillerData.get(TileEntityStewDistiller.INDEX_FUEL_TOTAL);
        return j != 0 && i != 0 ? (int) (i * 13 / j) : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isLit() {
        return distillerData.get(TileEntityStewDistiller.INDEX_FUEL)>0;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
    }


}
