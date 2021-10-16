package com.example.upgradedwolves.powerup;

import java.lang.reflect.InvocationTargetException;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Wolf;

import com.example.upgradedwolves.capabilities.WolfStatsEnum;


public class ExamplePowerUp extends PowerUp{

    public ExamplePowerUp(int levelRequirement) {
        super(levelRequirement, "example_powerup");
        this.active = true;
        this.uLocation = 0;
        this.vLocation = 0;
        this.statType = WolfStatsEnum.values()[0];    
    }

    @Override
    protected Goal goalConstructor(Wolf wolf) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, SecurityException {
        return null;
    }

    
    
}
