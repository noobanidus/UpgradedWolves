package com.example.upgradedwolves.powerup;

import java.lang.reflect.InvocationTargetException;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Wolf;

public class ExamplePowerUp extends PowerUp{

    public ExamplePowerUp(int levelRequirement) {
        super(levelRequirement, "example_powerup");        
    }

    @Override
    protected Goal goalConstructor(Wolf wolf) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, SecurityException {
        return null;
    }

    
    
}
