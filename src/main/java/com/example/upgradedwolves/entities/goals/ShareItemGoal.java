package com.example.upgradedwolves.entities.goals;

import java.util.List;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.example.upgradedwolves.entities.utilities.EntityFinder;
import com.example.upgradedwolves.itemHandler.WolfItemStackHandler;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.ItemStack;

public class ShareItemGoal extends Goal {
    protected final Wolf wolf;
    protected final IWolfStats handler;
    protected Wolf target;
    protected EntityFinder<Wolf> allyFinder;
    protected int slot;

    public ShareItemGoal(Wolf wolf){
        this.wolf = wolf;
        this.handler = WolfStatsHandler.getHandler(wolf);
        allyFinder = new EntityFinder<Wolf>(this.wolf,Wolf.class);
    }

    @Override
    public boolean canUse() {
        WolfItemStackHandler wolfItems = handler.getInventory();
        List<Wolf> allyList = allyFinder.findWithPredicate(7, 3, ally -> (ally.getOwner() == wolf.getOwner()) &&
        (ally.getHealth() <= 10));
        for (Wolf livingEntity : allyList) {
            int slot = -1;
            if(livingEntity.getHealth() <= 10){
                slot = wolfItems.getArbitraryItem(item -> item.isEdible() && item.getFoodProperties().isMeat());
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
    public void start() {
        ItemStack stack = handler.getInventory().extractItem(slot, 1, false);
        target.spawnAtLocation(stack);
        target = null;
    }
}
