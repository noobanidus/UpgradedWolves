package com.example.upgradedwolves.entities.goals;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.example.upgradedwolves.itemHandler.ItemStackHandlerWolf;
import com.example.upgradedwolves.items.MobPlushy;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class WolfPlayWithPlushGoal extends Goal {
    protected int timeLeftToPlay;
    protected WolfEntity wolf;

    public WolfPlayWithPlushGoal(WolfEntity wolf){
        this.wolf = wolf;
        timeLeftToPlay = 0;
    }

    @Override
    public boolean shouldExecute() {
        ItemStack heldItem = wolf.getHeldItemMainhand();
        if(heldItem != ItemStack.EMPTY && heldItem.getItem() instanceof MobPlushy){
            timeLeftToPlay = (int)((20*15) + Math.random() * (20*15));
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldContinueExecuting() {
        if(timeLeftToPlay-- > 0){
            ItemStack wolfHeldItem = wolf.getHeldItemMainhand();
            IWolfStats handler = WolfStatsHandler.getHandler(wolf);
            ItemStackHandlerWolf wolfInventory = handler.getInventory();
            handler.addXp(WolfStatsEnum.Strength, 1);
            int wolfSlot = wolfInventory.getAvailableSlot(wolfHeldItem);
            if(wolfSlot >= 0){
                ItemStack remaining = wolfInventory.insertItem(wolfSlot, wolfHeldItem, false);
                wolf.setHeldItem(Hand.MAIN_HAND, remaining);
            }
            else{
                wolf.entityDropItem(wolfHeldItem);                    
                wolf.setHeldItem(Hand.MAIN_HAND, ItemStack.EMPTY);
            }
            return true;
        }
        return false;
    }
    
}
