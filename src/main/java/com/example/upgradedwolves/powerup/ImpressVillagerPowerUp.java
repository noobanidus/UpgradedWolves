package com.example.upgradedwolves.powerup;

import java.lang.reflect.InvocationTargetException;

import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.entities.goals.ImpressVillagerGoal;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Wolf;

public class ImpressVillagerPowerUp extends PowerUp {

    public ImpressVillagerPowerUp(int levelRequirement) {
        super(levelRequirement, "impress",ImpressVillagerGoal.class);   
        this.active = true;
        this.uLocation = 86;
        this.vLocation = 214;
        this.statType = WolfStatsEnum.values()[2];
        this.defaultPriority = 3;     
    }

    @Override
    protected Goal goalConstructor(Wolf wolf) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, SecurityException {        
        return genericGoalConstructor(wolf);
    }
    
    
}
