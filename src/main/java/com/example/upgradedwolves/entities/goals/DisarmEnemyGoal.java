package com.example.upgradedwolves.entities.goals;

import java.util.List;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.example.upgradedwolves.entities.utilities.AbilityEnhancer;
import com.example.upgradedwolves.entities.utilities.EntityFinder;

import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;

public class DisarmEnemyGoal extends CoolDownGoal{
    protected final Wolf wolf;
    protected final EntityFinder<Monster> entityFinder;
    protected Monster target;

    public DisarmEnemyGoal(Wolf wolf){
        this.wolf = wolf;
        this.entityFinder = new EntityFinder<>(wolf,Monster.class);
        setCoolDownInSeconds(1800);
    }

    @Override
    public boolean canUse() {
        if(active()){
            List<Monster> enemies = entityFinder.findWithPredicate(1.1, 1, enemy -> enemy.getMainHandItem() != null);
            if(enemies.size() > 0){
                target = enemies.get(0);
                return true;
            }
        }
        return false;
    }

    @Override
    public void startExecuting() {
        float next = wolf.getRandom().nextFloat() * 100;
        int bonus = AbilityEnhancer.detectionSkill(wolf);
        if(next < 40 + bonus){
            ItemStack item = target.getMainHandItem();
            target.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);            
            if(next < 10 + bonus){
                IWolfStats handler = WolfStatsHandler.getHandler(wolf);
                int itemSlot = handler.getInventory().getAvailableSlot(item);
                if(itemSlot >= 0){
                    handler.getInventory().insertItem(itemSlot, item, false);
                } else {
                    wolf.spawnAtLocation(item);
                }
            }
        }
        startCoolDown();
    }
    
}
