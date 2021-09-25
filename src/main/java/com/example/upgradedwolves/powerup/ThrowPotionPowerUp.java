package com.example.upgradedwolves.powerup;

import java.lang.reflect.InvocationTargetException;

import com.example.upgradedwolves.entities.goals.ThrowPotionGoal;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Wolf;

public class ThrowPotionPowerUp extends PowerUp {

    public ThrowPotionPowerUp(int levelRequirement) {
        super(levelRequirement, "use_potion",ThrowPotionGoal.class);
    }

    @Override
    protected Goal goalConstructor(Wolf wolf) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, SecurityException {
        return genericGoalConstructor(wolf);
    }
    
}
