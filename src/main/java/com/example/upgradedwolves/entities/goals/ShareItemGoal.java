package com.example.upgradedwolves.entities.goals;

import java.util.List;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.example.upgradedwolves.entities.utilities.EntityFinder;
import com.example.upgradedwolves.itemHandler.WolfItemStackHandler;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.ItemStack;

public class ShareItemGoal extends Goal {
    protected final WolfEntity wolf;
    protected final IWolfStats handler;
    protected WolfEntity target;
    protected EntityFinder<WolfEntity> allyFinder;
    protected int slot;

    public ShareItemGoal(WolfEntity wolf){
        this.wolf = wolf;
        this.handler = WolfStatsHandler.getHandler(wolf);
        allyFinder = new EntityFinder<WolfEntity>(this.wolf,WolfEntity.class);
    }

    @Override
    public boolean shouldExecute() {
        WolfItemStackHandler wolfItems = handler.getInventory();
        List<WolfEntity> allyList = allyFinder.findWithPredicate(7, 3, ally -> (ally.getOwner() == wolf.getOwner()) &&
        (ally.getHealth() <= 10));
        for (WolfEntity livingEntity : allyList) {
            int slot = -1;
            if(livingEntity.getHealth() <= 10){
                slot = wolfItems.getArbitraryItem(item -> item.isFood() && item.getFood().isMeat());
            }
            if(slot >= 0){
                this.slot = slot;
                target = livingEntity;
                break;
            }
        }
        return target != null;
    }

    @Override
    public void startExecuting() {
        ItemStack stack = handler.getInventory().extractItem(slot, 1, false);
        target.entityDropItem(stack);
        target = null;
    }
}
