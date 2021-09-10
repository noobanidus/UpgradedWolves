package com.example.upgradedwolves.powerup;

import java.lang.reflect.InvocationTargetException;

import com.example.upgradedwolves.entities.goals.DigForItemGoal;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.WolfEntity;

public class DigForItemPowerUp extends PowerUp{

    public DigForItemPowerUp(int levelRequirement) {
        super(levelRequirement, "dig_for_item",DigForItemGoal.class);
        //TODO Auto-generated constructor stub
    }

    @Override
    protected Goal goalConstructor(WolfEntity wolf) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, SecurityException {
        // TODO Auto-generated method stub
        return genericGoalConstructor(wolf);
    }
    
}
