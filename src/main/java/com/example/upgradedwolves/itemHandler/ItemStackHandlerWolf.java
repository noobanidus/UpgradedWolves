package com.example.upgradedwolves.itemHandler;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

public class ItemStackHandlerWolf extends ItemStackHandler {
    
    public static final int MIN_WOLF_SLOT = 1;
    public static final int MAX_WOLF_SLOT = 9;

    boolean isDirty = true;

    public ItemStackHandlerWolf(int numberOfSlots){
        super(MathHelper.clamp(numberOfSlots, MIN_WOLF_SLOT, MAX_WOLF_SLOT));
        if(numberOfSlots < MIN_WOLF_SLOT || numberOfSlots > MAX_WOLF_SLOT){
            throw new IllegalArgumentException("Invalid number of slots: " + numberOfSlots);
        }
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack){
        if (slot < 0 || slot >= MAX_WOLF_SLOT) {
            throw new IllegalArgumentException("Invalid slot number:"+slot);
        }
        if(stack.isEmpty()) return false;
        //if(item.isIn(ItemTags....))
        return true;
    }

    public int getNumberOfEmptySlots(){
        final int numberOfSlots = getSlots();

        int emptyCount = 0;
        for(int i = 0; i < numberOfSlots; i++){
            if(getStackInSlot(i) == ItemStack.EMPTY)
                emptyCount++;
        }

        return emptyCount;
    }

    public boolean isDirty(){
        boolean currentState = isDirty;
        isDirty = false;
        return currentState;
    }

    protected void onContentsChanged(int slot){
        isDirty = true;
    }

    public int getAvailableSlot(ItemStack item){
        for(int i = 0; i < getSlots(); i++){
            if(getStackInSlot(i) == ItemStack.EMPTY)
                return i;
            else if(ItemHandlerHelper.canItemStacksStack(getStackInSlot(i), item)){
                ItemStack slotItem = getStackInSlot(i);
                int max = slotItem.getMaxStackSize();
                if(slotItem.getCount() != max)
                    return i;
            }
        }
        return -1;
    }

    public int getSword(){
        for(int i = 0; i < getSlots(); i++){
            if(getStackInSlot(i).getItem() instanceof SwordItem)
                return i;
        }
        return -1;
    }
}
