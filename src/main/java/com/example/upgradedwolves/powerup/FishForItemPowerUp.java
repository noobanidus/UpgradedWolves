package com.example.upgradedwolves.powerup;

import java.lang.reflect.InvocationTargetException;

import com.example.upgradedwolves.entities.goals.FishForItemGoal;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Wolf;

public class FishForItemPowerUp extends PowerUp {

    public FishForItemPowerUp(int levelRequirement) {
        super(levelRequirement,"fish_catcher",FishForItemGoal.class);        
    }

    @Override
    protected Goal goalConstructor(Wolf wolf) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, SecurityException {
        
        return genericGoalConstructor(wolf);
    }
    
}
