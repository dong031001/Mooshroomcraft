package com.enigtech.mooshroomcraft.tile.container;


import com.enigtech.mooshroomcraft.block.BlockRegistry;
import com.enigtech.mooshroomcraft.item.ItemRegistry;
import com.enigtech.mooshroomcraft.tile.TileEntityStewDistiller;
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
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
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

    private TileEntity tileEntity;
    private PlayerEntity playerEntity;
    private IItemHandler playerInventory;

    public ContainerStewDistiller(int id, World world, BlockPos blockPos, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        super(CONTAINER_TYPE_STEW_DISTILLER, id);
        tileEntity = world.getTileEntity(blockPos);
        this.playerEntity = playerEntity;
        this.playerInventory = new InvWrapper(playerInventory);

        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).ifPresent(handler -> addSlot(new SlotItemHandler(handler,0 ,56, 17)));
        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.DOWN).ifPresent(handler -> addSlot(new SlotItemHandler(handler,0 ,116, 35)));
        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.WEST).ifPresent(handler -> addSlot(new SlotItemHandler(handler,0,56, 53)));
        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.SOUTH).ifPresent(handler -> addSlot(new SlotItemHandler(handler,0,143, 35)));


        layoutPlayerInventorySlots(8,84);
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
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();
            if (index == 0||index == 1||index == 2||index == 3) {
                if (!this.mergeItemStack(stack, 1, 40, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack, itemstack);
            } else {
                if (stack.getItem() == ItemRegistry.MUSHROOM_STEW) {
                    if (!this.mergeItemStack(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if(ForgeHooks.getBurnTime(stack)>0){
                    if (!this.mergeItemStack(stack, 2, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 31) {
                    if (!this.mergeItemStack(stack, 31, 40, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 40 && !this.mergeItemStack(stack, 1, 31, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }

        return itemstack;
    }
}
