package com.example.upgradedwolves.containers;

import javax.annotation.Nonnull;

import com.example.upgradedwolves.init.ModContainers;
import com.example.upgradedwolves.itemHandler.ItemStackHandlerWolf;

import org.apache.logging.log4j.LogManager;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.SlotItemHandler;

public class WolfContainer extends Container {

    public WolfEntity wolf;
    public ItemStackHandlerWolf wolfItemHandler;   
    public CompoundNBT nbt;

    private WolfContainer(int id, PlayerInventory playerInventory,ItemStackHandlerWolf wolfStackHandler,WolfEntity wolf,CompoundNBT nbt) {
        super(ModContainers.WOLF_CONTAINER,id);
        this.wolf = wolf;
        this.wolfItemHandler = wolfStackHandler;
        this.nbt = nbt;

        int startX = 7;
        int startY = 94;
        //The delta X and delta Y are the same
        int delta = 18;
        //Player Inventory
        for(int i = 0; i < 27; i++){
            this.addSlot(new Slot(playerInventory,9 + i,startX + delta * (i % 9),startY + delta * (i/9)));
        }
        
        // Hot bar
        for(int i = 0; i < 9; i++){
            this.addSlot(new Slot(playerInventory,i,startX + delta * (i % 9),152));
        }
        startY = 63;
        //Wolf Inventory
        for(int i = 0; i < wolfItemHandler.getSlots(); i++){
            this.addSlot(new SlotItemHandler(wolfStackHandler,i,startX + delta * (i % 9),startY + delta * (i/9)));
        }
    }

    public static WolfContainer createContainerClientSide(int id, PlayerInventory inventory, PacketBuffer data){
        int numberOfSlots = data.readInt();
        int wolfId = data.readInt();
        CompoundNBT nbt = data.readCompoundTag();

        try{
            ItemStackHandlerWolf wolfItemHandler = new ItemStackHandlerWolf(numberOfSlots);
            Minecraft mc = Minecraft.getInstance();
            WolfEntity wolf = (WolfEntity)mc.world.getEntityByID(wolfId);

            return new WolfContainer(id,inventory,wolfItemHandler, wolf,nbt);
        }catch(IllegalArgumentException iae){
            LogManager.getLogger().warn(iae);
        }
        return null;
    }

    public static WolfContainer createContainerServerSide(int id, PlayerInventory inventory,ItemStackHandlerWolf wolfItemHandler,WolfEntity wolf){
        return new WolfContainer(id,inventory,wolfItemHandler,wolf,null);
    }

    @Nonnull
	@Override
	public ItemStack transferStackInSlot(PlayerEntity player, int sourceSlotIndex) {
        Slot sourceSlot = inventorySlots.get(sourceSlotIndex);
        if (sourceSlot == null || !sourceSlot.getHasStack()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getStack();
        ItemStack copyOfSourceStack = sourceStack.copy();
        final int BAG_SLOT_COUNT = wolfItemHandler.getSlots();

        // Check if the slot clicked is one of the vanilla container slots
        if (sourceSlotIndex >= 0 && sourceSlotIndex < 27) {
        // This is a vanilla container slot so merge the stack into the bag inventory
        if (!mergeItemStack(sourceStack, 28, 28 + BAG_SLOT_COUNT, false)){
            return ItemStack.EMPTY;  // EMPTY_ITEM
        }
        } else if (sourceSlotIndex >= 28 && sourceSlotIndex < 28 + BAG_SLOT_COUNT) {
        // This is a bag slot so merge the stack into the players inventory
        if (!mergeItemStack(sourceStack, 0, 27, false)) {
            return ItemStack.EMPTY;
        }
        } else {
            LogManager.getLogger().warn("Invalid slotIndex:" + sourceSlotIndex);
            return ItemStack.EMPTY;
        }

        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.putStack(ItemStack.EMPTY);
        } else {
            sourceSlot.onSlotChanged();
        }

        sourceSlot.onTake(player, sourceStack);
        return copyOfSourceStack;
	}

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        
        return true;
    }

    
}