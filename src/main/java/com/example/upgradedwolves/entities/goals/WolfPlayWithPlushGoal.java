package com.example.upgradedwolves.entities.goals;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.example.upgradedwolves.itemHandler.WolfItemStackHandler;
import com.example.upgradedwolves.items.MobPlushy;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;

public class WolfPlayWithPlushGoal extends Goal {
    protected int timeLeftToPlay;
    protected Wolf wolf;

    public WolfPlayWithPlushGoal(Wolf wolf){
        this.wolf = wolf;
        timeLeftToPlay = 0;
    }

    @Override
    public boolean canUse() {
        ItemStack heldItem = wolf.getMainHandItem();
        if(heldItem != ItemStack.EMPTY && heldItem.getItem() instanceof MobPlushy){
            timeLeftToPlay = (int)((20*15) + Math.random() * (20*15));
            return true;
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        if(timeLeftToPlay-- > 0)
            return true;
        ItemStack wolfHeldItem = wolf.getMainHandItem();
        IWolfStats handler = WolfStatsHandler.getHandler(wolf);
        WolfItemStackHandler wolfInventory = handler.getInventory();
        handler.addXp(WolfStatsEnum.Intelligence, 2);
        int wolfSlot = wolfInventory.getAvailableSlot(wolfHeldItem);
        if(wolfSlot >= 0){
            ItemStack remaining = wolfInventory.insertItem(wolfSlot, wolfHeldItem, false);
            wolf.setItemInHand(InteractionHand.MAIN_HAND, remaining);
        }
        else{
            wolf.spawnAtLocation(wolfHeldItem);                    
            wolf.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
        }
        return false;
    }
    
}
