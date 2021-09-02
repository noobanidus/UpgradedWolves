package com.example.upgradedwolves.powerup;

import java.lang.reflect.InvocationTargetException;

import com.example.upgradedwolves.entities.goals.DisarmEnemyGoal;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.WolfEntity;

public class DisarmEnemyPowerUp extends PowerUp {

    public DisarmEnemyPowerUp(int levelRequirement) {
        super(levelRequirement, "disarm_enemy",DisarmEnemyGoal.class);        
    }

    @Override
    protected Goal goalConstructor(WolfEntity wolf) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, SecurityException {        
        return genericGoalConstructor(wolf);
    }
    

}
