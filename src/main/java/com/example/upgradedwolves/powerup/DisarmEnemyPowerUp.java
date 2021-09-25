package com.example.upgradedwolves.powerup;

import java.lang.reflect.InvocationTargetException;

import com.example.upgradedwolves.entities.goals.DisarmEnemyGoal;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Wolf;

public class DisarmEnemyPowerUp extends PowerUp {

    public DisarmEnemyPowerUp(int levelRequirement) {
        super(levelRequirement, "disarm_enemy",DisarmEnemyGoal.class);        
    }

    @Override
    protected Goal goalConstructor(Wolf wolf) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, SecurityException {        
        return genericGoalConstructor(wolf);
    }
    

}
