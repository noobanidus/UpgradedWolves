package com.example.upgradedwolves.entities.goals;

import java.util.List;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.example.upgradedwolves.entities.utilities.EntityFinder;

import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class DisarmEnemyGoal extends CoolDownGoal{
    protected final WolfEntity wolf;
    protected final EntityFinder<MonsterEntity> entityFinder;
    protected MonsterEntity target;

    public DisarmEnemyGoal(WolfEntity wolf){
        this.wolf = wolf;
        this.entityFinder = new EntityFinder<>(wolf,MonsterEntity.class);
        setCoolDownInSeconds(1800);
    }

    @Override
    public boolean shouldExecute() {
        if(active()){
            List<MonsterEntity> enemies = entityFinder.findWithPredicate(1.1, 1, enemy -> enemy.getHeldItemMainhand() != null);
            if(enemies.size() > 0){
                target = enemies.get(0);
                return true;
            }
        }
        return false;
    }

    @Override
    public void startExecuting() {
        float next = wolf.getRNG().nextFloat() * 100;
        if(next < 75){
            ItemStack item = target.getHeldItemMainhand();
            target.setHeldItem(Hand.MAIN_HAND, ItemStack.EMPTY);            
            if(next < 25){
                IWolfStats handler = WolfStatsHandler.getHandler(wolf);
                int itemSlot = handler.getInventory().getAvailableSlot(item);
                if(itemSlot >= 0){
                    handler.getInventory().insertItem(itemSlot, item, false);
                } else {
                    wolf.entityDropItem(item);
                }
            }
        }
        startCoolDown();
    }
    
}
