package com.example.upgradedwolves.powerup;

import java.lang.reflect.InvocationTargetException;

import com.example.upgradedwolves.entities.goals.DigForItemGoal;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Wolf;

public class DigForItemPowerUp extends PowerUp{

    public DigForItemPowerUp(int levelRequirement) {
        super(levelRequirement, "dig_for_item",DigForItemGoal.class);
    }

    @Override
    protected Goal goalConstructor(Wolf wolf) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, SecurityException {
        return genericGoalConstructor(wolf);
    }
    
}
