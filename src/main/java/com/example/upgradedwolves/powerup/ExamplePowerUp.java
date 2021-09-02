package com.example.upgradedwolves.powerup;

import java.lang.reflect.InvocationTargetException;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.WolfEntity;

public class ExamplePowerUp extends PowerUp{

    public ExamplePowerUp(int levelRequirement) {
        super(levelRequirement, "example_powerup");        
    }

    @Override
    protected Goal goalConstructor(WolfEntity wolf) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, SecurityException {
        return null;
    }

    
    
}
