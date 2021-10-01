package com.example.upgradedwolves.entities.goals;

import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.PathfinderMob;

public class FleeOnLowHealthGoal extends AvoidEntityGoal<Monster> {
    float minHealth;

    public FleeOnLowHealthGoal(PathfinderMob entityIn, float avoidDistanceIn,
            double farSpeedIn, double nearSpeedIn,float minHealth) {
        super(entityIn, Monster.class, avoidDistanceIn, farSpeedIn, nearSpeedIn);
        this.minHealth = minHealth;
    }

    @Override
    public boolean canUse() {
        if(this.mob.getHealth() < minHealth && !(this.mob instanceof TamableAnimal && ((TamableAnimal)this.mob).isInSittingPose()))
            return super.canUse();
        return false;
    }
    
}
