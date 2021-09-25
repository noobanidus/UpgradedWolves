package com.example.upgradedwolves.entities.goals;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.passive.TameableEntity;

public class FleeExplodingCreeper extends AvoidEntityGoal<CreeperEntity>{

    public FleeExplodingCreeper(CreatureEntity entityIn, float avoidDistanceIn,
            double farSpeedIn, double nearSpeedIn) {
        super(entityIn, CreeperEntity.class, avoidDistanceIn, farSpeedIn, nearSpeedIn);        
    }

    @Override
    public boolean canUse() {
        boolean mayExecute = super.canUse();
        if(avoidTarget == null)
            return false;
        if(avoidTarget.hasIgnited() && !(this.entity instanceof TameableEntity && ((TameableEntity)this.entity).isSitting()))
            return mayExecute;
        return false;
    }
    
}
