package com.example.upgradedwolves.itemHandler;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.items.ItemStackHandler;

public class ItemStackHandlerWolf extends ItemStackHandler {
    
    public static final int MIN_WOLF_SLOT = 1;
    public static final int MAX_WOLF_SLOT = 7;

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
}
