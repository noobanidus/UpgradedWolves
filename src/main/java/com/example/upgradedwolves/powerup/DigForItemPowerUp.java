package com.example.upgradedwolves.powerup;

import java.lang.reflect.InvocationTargetException;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.WolfEntity;

public class DigForItemPowerUp extends PowerUp{

    public DigForItemPowerUp(int levelRequirement) {
        super(levelRequirement, "dig_for_item");
        //TODO Auto-generated constructor stub
    }

    @Override
    protected Goal goalConstructor(WolfEntity wolf) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, SecurityException {
        // TODO Auto-generated method stub
        return genericGoalConstructor(wolf);
    }
    
}
