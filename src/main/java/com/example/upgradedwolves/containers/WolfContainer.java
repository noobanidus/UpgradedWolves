package com.example.upgradedwolves.containers;

import javax.annotation.Nonnull;

import com.example.upgradedwolves.init.ModContainers;
import com.example.upgradedwolves.itemHandler.WolfItemStackHandler;

import org.apache.logging.log4j.LogManager;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.items.SlotItemHandler;

public class WolfContainer extends AbstractContainerMenu {

    public Wolf wolf;
    public WolfItemStackHandler wolfItemHandler;   
    public CompoundTag nbt;
    public Inventory playerInventory;

    private WolfContainer(int id, Inventory playerInventory,WolfItemStackHandler wolfStackHandler,Wolf wolf,CompoundTag nbt) {
        super(ModContainers.WOLF_CONTAINER,id);
        this.wolf = wolf;
        this.wolfItemHandler = wolfStackHandler;
        this.nbt = nbt;
        this.playerInventory = playerInventory;
        setupContainer();
    }

    public static WolfContainer createContainerClientSide(int id, Inventory inventory, FriendlyByteBuf data){
        int numberOfSlots = data.readInt();
        int wolfId = data.readInt();
        CompoundTag nbt = data.readNbt();

        try{
            WolfItemStackHandler wolfItemHandler = new WolfItemStackHandler(numberOfSlots);
            Minecraft mc = Minecraft.getInstance();
            Wolf wolf = (Wolf)mc.level.getEntity(wolfId);

            return new WolfContainer(id,inventory,wolfItemHandler, wolf,nbt);
        }catch(IllegalArgumentException iae){
            LogManager.getLogger().warn(iae);
        }
        return null;
    }

    public static WolfContainer createContainerServerSide(int id, Inventory inventory,WolfItemStackHandler wolfItemHandler,Wolf wolf){
        return new WolfContainer(id,inventory,wolfItemHandler,wolf,null);
    }

    @Nonnull
	@Override
	public ItemStack quickMoveStack(Player player, int sourceSlotIndex) {
        Slot sourceSlot = slots.get(sourceSlotIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();
        final int BAG_SLOT_COUNT = wolfItemHandler.getSlots();

        // Check if the slot clicked is one of the vanilla container slots
        if (sourceSlotIndex >= 0 && sourceSlotIndex < 27) {
        // This is a vanilla container slot so merge the stack into the bag inventory
        if (!moveItemStackTo(sourceStack, 28, 28 + BAG_SLOT_COUNT, false)){
            return ItemStack.EMPTY;  // EMPTY_ITEM
        }
        } else if (sourceSlotIndex >= 28 && sourceSlotIndex < 28 + BAG_SLOT_COUNT) {
        // This is a bag slot so merge the stack into the players inventory
        if (!moveItemStackTo(sourceStack, 0, 27, false)) {
            return ItemStack.EMPTY;
        }
        } else {
            LogManager.getLogger().warn("Invalid slotIndex:" + sourceSlotIndex);
            return ItemStack.EMPTY;
        }

        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        sourceSlot.onTake(player, sourceStack);
        return copyOfSourceStack;
	}

    public void clearContainer(){
        this.slots.clear();
    }

    public void setupContainer(){
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
            this.addSlot(new SlotItemHandler(wolfItemHandler,i,startX + delta * (i % 9),startY + delta * (i/9)));
        }
    }

    @Override
    public boolean stillValid(Player p_38874_) {        
        return true;
    }
    
}