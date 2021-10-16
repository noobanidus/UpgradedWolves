package com.example.upgradedwolves.powerup;

import java.lang.reflect.InvocationTargetException;

import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.entities.goals.SelfPreservationGoal;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Wolf;

public class SelfPreservationPowerUp extends PowerUp {

    public SelfPreservationPowerUp(int levelRequirement) {
        super(levelRequirement, "self_preservation",SelfPreservationGoal.class);   
        this.active = false;
        this.uLocation = 150;
        this.vLocation = 198;
        this.statType = WolfStatsEnum.values()[0];
        this.defaultPriority = 3;     
    }

    @Override
    protected Goal goalConstructor(Wolf wolf) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, SecurityException {
        return genericGoalConstructor(wolf);
    }
    
}
