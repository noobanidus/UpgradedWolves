package com.example.upgradedwolves.powerup;

import java.lang.reflect.InvocationTargetException;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.entities.goals.ArrowInterceptGoal;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.WolfEntity;

public class ArrowInterceptPowerUp extends PowerUp {

    public ArrowInterceptPowerUp(int levelRequirement) {
        super(levelRequirement, UpgradedWolves.getId("powerups/arrow_intercept.java"),ArrowInterceptGoal.class);
        
    }

    @Override
    protected Goal goalConstructor(WolfEntity wolf) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, SecurityException {
        return (Goal)relevantGoal.getDeclaredConstructors()[0].newInstance(wolf);
    }
    
}
