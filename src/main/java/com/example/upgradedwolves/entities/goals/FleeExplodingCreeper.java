package com.example.upgradedwolves.entities.goals;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.TamableAnimal;

public class FleeExplodingCreeper extends AvoidEntityGoal<Creeper>{

    public FleeExplodingCreeper(PathfinderMob entityIn, float avoidDistanceIn,
            double farSpeedIn, double nearSpeedIn) {
        super(entityIn, Creeper.class, avoidDistanceIn, farSpeedIn, nearSpeedIn);        
    }

    @Override
    public boolean canUse() {
        boolean mayExecute = super.canUse();
        if(toAvoid == null)
            return false;
        if(toAvoid.isIgnited() && !(this.mob instanceof TamableAnimal && ((TamableAnimal)this.mob).isInSittingPose()))
            return mayExecute;
        return false;
    }
    
}
