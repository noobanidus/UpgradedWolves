package com.example.upgradedwolves.powerup;

import java.lang.reflect.InvocationTargetException;

import com.example.upgradedwolves.entities.goals.ArrowInterceptGoal;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Wolf;

public class ArrowInterceptPowerUp extends PowerUp {

    public ArrowInterceptPowerUp(int levelRequirement) {
        super(levelRequirement, "arrow_intercept",ArrowInterceptGoal.class);
        
    }

    @Override
    protected Goal goalConstructor(Wolf wolf) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, SecurityException {
        return (Goal)relevantGoal.getDeclaredConstructors()[0].newInstance(wolf);
    }
    
}
