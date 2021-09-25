package com.example.upgradedwolves.powerup;

import java.lang.reflect.InvocationTargetException;

import com.example.upgradedwolves.entities.goals.SelfPreservationGoal;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Wolf;

public class SelfPreservationPowerUp extends PowerUp {

    public SelfPreservationPowerUp(int levelRequirement) {
        super(levelRequirement, "self_preservation",SelfPreservationGoal.class);        
    }

    @Override
    protected Goal goalConstructor(Wolf wolf) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, SecurityException {
        return genericGoalConstructor(wolf);
    }
    
}
