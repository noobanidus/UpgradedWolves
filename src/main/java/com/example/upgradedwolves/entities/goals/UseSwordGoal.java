package com.example.upgradedwolves.entities.goals;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.example.upgradedwolves.itemHandler.ItemStackHandlerWolf;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class UseSwordGoal extends Goal {
    protected final WolfEntity wolf;
    protected ItemStack sword;

    
    public UseSwordGoal(WolfEntity wolf){
        this.wolf = wolf;
    }

    @Override
    public boolean shouldExecute() {
        if(wolf.getAttackTarget() != null){
            IWolfStats handler = WolfStatsHandler.getHandler(wolf);
            ItemStackHandlerWolf wolfInventory = handler.getInventory();
            int swordSlot = wolfInventory.getSword();
            if(swordSlot >= 0){
                sword = wolfInventory.extractItem(wolfInventory.getSword(), 1, false);
                wolf.setHeldItem(Hand.MAIN_HAND, sword);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean shouldContinueExecuting() {        
        return wolf.getAttackTarget() != null && super.shouldContinueExecuting();
    }
    
}
