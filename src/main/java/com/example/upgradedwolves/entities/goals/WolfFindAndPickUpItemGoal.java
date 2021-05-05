package com.example.upgradedwolves.entities.goals;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.example.upgradedwolves.itemHandler.ItemStackHandlerWolf;

import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.MathHelper;

public class WolfFindAndPickUpItemGoal extends Goal {    
    WolfEntity wolf;
    ItemStackHandlerWolf wolfInventory;
    ItemEntity item;
    int unseenMemoryTicks;
    int targetUnseenTicks;


    public WolfFindAndPickUpItemGoal(WolfEntity owner){
        this.wolf = owner;
        IWolfStats handler = WolfStatsHandler.getHandler(wolf);
        this.wolfInventory = handler.getInventory();
        this.unseenMemoryTicks = 10 * handler.getLevel(WolfStatsEnum.Intelligence);
    }

    @Override
    public boolean shouldExecute() {
        item = null;
        if(item != null || wolf.isSitting())
            return false;
        for(ItemEntity itementity : wolf.world.getEntitiesWithinAABB(ItemEntity.class, wolf.getBoundingBox().grow(12.0D, 0.0D, 12.0D))) {
            if (wolfInventory.getAvailableSlot(itementity.getItem()) >= 0 && canEasilyReach(itementity)) {                
                item = itementity;
                return true;
            }
        }
        return false;
    }

    public boolean shouldContinueExecuting() {
        ItemEntity item = (ItemEntity)wolf.world.getEntityByID(this.item.getEntityId());
        if (item == null || wolfInventory.getAvailableSlot(item.getItem()) < 0) {
            return false;
        } else {
            double d0 = this.getTargetDistance();
            if (wolf.getDistanceSq(item) > d0 * d0) {
                this.item = null;
                return false;
            } else {
                if (wolf.getEntitySenses().canSee(item)) {
                    this.targetUnseenTicks = 0;
                } else if (++this.targetUnseenTicks > this.unseenMemoryTicks) {
                    this.item = null;
                    return false;
                }                
                return true; 
            }
        }
    }

    protected double getTargetDistance() {
        return wolf.getAttributeValue(Attributes.FOLLOW_RANGE);
    }
    private boolean canEasilyReach(ItemEntity target) {        
        Path path = wolf.getNavigator().getPathToEntity(target, 0);
        if (path == null) {
           return false;
        } else {
           PathPoint pathpoint = path.getFinalPathPoint();
           if (pathpoint == null) {
              return false;
           } else {
              int i = pathpoint.x - MathHelper.floor(target.getPosX());
              int j = pathpoint.z - MathHelper.floor(target.getPosZ());
              return (double)(i * i + j * j) <= 2.25D;
           }
        }
    }

    @Override
    public void tick(){
        wolf.getMoveHelper().setMoveTo(item.getPosX(), item.getPosY(), item.getPosZ(), 1.0);
    }
}
