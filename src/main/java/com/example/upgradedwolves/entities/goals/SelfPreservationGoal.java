package com.example.upgradedwolves.entities.goals;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.example.upgradedwolves.itemHandler.WolfItemStackHandler;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.SoundEvents;

public class SelfPreservationGoal extends Goal {
    public Wolf wolf;
    final int eatingTime = 20 * 3;
    int currentTime = 0;
    int healAmount;

    public SelfPreservationGoal(Wolf wolf){
        this.wolf = wolf;
    }

    @Override
    public boolean canUse() {
        IWolfStats handler = WolfStatsHandler.getHandler(wolf);
        WolfItemStackHandler wolfInventory = handler.getInventory();
        if(wolf.getHealth() < 15 && wolfInventory.getArbitraryItem(item -> item.isEdible()) >= 0){
            int inventorySlot = wolfInventory.getArbitraryItem(item -> item.isEdible());
            ItemStack foodStack = wolfInventory.getStackInSlot(inventorySlot);
            healAmount = foodStack.getItem().getFoodProperties().getHealing();
            currentTime = 0;
            foodStack.shrink(1);
            return true;
        }
        healAmount = 0;
        return false;
    }

    public boolean shouldContinueExecuting(){
        if(currentTime++ < eatingTime){
            if(currentTime % 4 == 0)
            wolf.playSound(SoundEvents.ENTITY_GENERIC_EAT, 0.5F + 0.5F * (float)wolf.getRNG().nextInt(2), (wolf.getRNG().nextFloat() - wolf.getRNG().nextFloat()) * 0.2F + 1.0F);
            return true;
        }   
        wolf.heal(healAmount);
        healAmount = 0;
        return false;
    }
    
}
