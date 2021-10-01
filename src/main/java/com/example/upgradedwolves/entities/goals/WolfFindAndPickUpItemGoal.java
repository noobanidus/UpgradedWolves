package com.example.upgradedwolves.entities.goals;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.example.upgradedwolves.entities.WolfChaseableEntity;
import com.example.upgradedwolves.entities.plushy.MobPlushyEntity;
import com.example.upgradedwolves.itemHandler.WolfItemStackHandler;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.util.Mth;

public class WolfFindAndPickUpItemGoal extends Goal implements IUpdateableGoal{    
    Wolf wolf;
    WolfItemStackHandler wolfInventory;
    Entity item;
    int unseenMemoryTicks;
    int targetUnseenTicks;
    double distance;
    Vec3 initialPoint;
    Vec3 endPoint;


    public WolfFindAndPickUpItemGoal(Wolf owner){
        this.wolf = owner;
        IWolfStats handler = WolfStatsHandler.getHandler(wolf);
        this.wolfInventory = handler.getInventory();
        this.unseenMemoryTicks = 10 * handler.getLevel(WolfStatsEnum.Intelligence);
        distance = 12.0D + handler.getDetectionBonus();
    }

    @Override
    public boolean canUse() {
        item = null;
        if(item != null || wolf.isInSittingPose() || wolf.getMainHandItem() != ItemStack.EMPTY)
            return false;
        for(ItemEntity itementity : wolf.level.getEntitiesOfClass(ItemEntity.class, wolf.getBoundingBox().expandTowards(12.0D, 0.0D, 12.0D))) {
            if (wolfInventory.getAvailableSlot(itementity.getItem()) >= 0 && canEasilyReach(itementity)) {                
                item = itementity;
                return true;
            }
        }
        for(WolfChaseableEntity wolfToy : wolf.level.getEntitiesOfClass(WolfChaseableEntity.class, wolf.getBoundingBox().expandTowards(36.0D, 5.0D, 36.0D))){
            if (wolfInventory.getAvailableSlot(wolfToy.getPickResult()) >= 0 && canEasilyReach(wolfToy)){
                item = wolfToy;
                initialPoint = wolf.getPosition(1);
                return true;
            }
        }
        for(MobPlushyEntity mobPlushy : wolf.level.getEntitiesOfClass(MobPlushyEntity.class, wolf.getBoundingBox().expandTowards(12.0D, 0.0D, 12.0D))){
            if (wolfInventory.getAvailableSlot(mobPlushy.getItem()) >= 0 && canEasilyReach(mobPlushy)){
                item = mobPlushy;
                return true;
            }
        }
        return false;
    }

    public boolean canContinueToUse() {
        if(item instanceof ItemEntity){
            ItemEntity item = (ItemEntity)wolf.level.getEntity(this.item.getId());
            if (item == null || wolfInventory.getAvailableSlot(item.getItem()) < 0) {
                return false;
            } else {
                return shouldChase(1,item);
            }
        } else if(item instanceof WolfChaseableEntity) {
            WolfChaseableEntity item = (WolfChaseableEntity)wolf.level.getEntity(this.item.getId());
            if (endPoint != null){
                double distance = initialPoint.distanceTo(endPoint);
                IWolfStats stats = WolfStatsHandler.getHandler(wolf);
                stats.addXp(WolfStatsEnum.Speed, (int)(distance/2));
                endPoint = null;
                initialPoint = null;
                return false;
            }
            else if (item == null || wolfInventory.getAvailableSlot(item.getPickResult()) < 0) {
                return false;
            } else {
                return shouldChase(3, item);
            }
        } else{
            MobPlushyEntity item = (MobPlushyEntity)wolf.level.getEntity(this.item.getId());
            if (item == null || wolfInventory.getAvailableSlot(item.getItem()) < 0 || wolf.getMainHandItem() != ItemStack.EMPTY) {
                return false;
            } else {
                return shouldChase(1,item);
            }
        }
    }

    private boolean shouldChase(double multiplier, Entity item){       
        double d0 = this.getTargetDistance();
        if (wolf.distanceToSqr(item) > d0 * d0 * multiplier) {
            this.item = null;
            return false;
        } else {
            if (wolf.getSensing().hasLineOfSight(item)) {
                this.targetUnseenTicks = 0;
            } else if (++this.targetUnseenTicks > this.unseenMemoryTicks * multiplier) {
                this.item = null;
                return false;
            }                
            return true; 
        }        
    }

    protected double getTargetDistance() {
        return wolf.getAttributeValue(Attributes.FOLLOW_RANGE);
    }
    private boolean canEasilyReach(Entity target) {        
        Path path = wolf.getNavigation().createPath(target, 0);
        if (path == null) {
           return false;
        } else {
           Node pathpoint = path.getEndNode();
           if (pathpoint == null) {
              return false;
           } else {
              int i = pathpoint.x - Mth.floor(target.getX());
              int j = pathpoint.z - Mth.floor(target.getZ());
              return (double)(i * i + j * j) <= 2.25D;
           }
        }
    }

    @Override
    public void tick(){
        wolf.getMoveControl().setWantedPosition(item.getX(), item.getY(), item.getZ(), 1.0);
    }

    @Override
    public void Update(IWolfStats handler, Wolf wolf) {        
        distance = 12.0D + handler.getDetectionBonus();
    }
    public void setEndPoint(Vec3 end){
        endPoint = end;
    }
}
