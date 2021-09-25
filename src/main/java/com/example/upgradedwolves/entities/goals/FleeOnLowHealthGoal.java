package com.example.upgradedwolves.entities.goals;

import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.CreatureEntity;

public class FleeOnLowHealthGoal extends AvoidEntityGoal<MonsterEntity> {
    float minHealth;

    public FleeOnLowHealthGoal(CreatureEntity entityIn, float avoidDistanceIn,
            double farSpeedIn, double nearSpeedIn,float minHealth) {
        super(entityIn, MonsterEntity.class, avoidDistanceIn, farSpeedIn, nearSpeedIn);
        this.minHealth = minHealth;
    }

    @Override
    public boolean canUse() {
        if(this.entity.getHealth() < minHealth && !(this.entity instanceof TameableEntity && ((TameableEntity)this.entity).isSitting()))
            return super.canUse();
        return false;
    }
    
}
