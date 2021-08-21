package com.example.upgradedwolves.powerup;

import java.lang.reflect.InvocationTargetException;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.entities.goals.FleeOnLowHealthGoal;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.WolfEntity;

public class FleeHealthPowerUp extends PowerUp {

    public FleeHealthPowerUp(int levelRequirement) {
        super(levelRequirement, UpgradedWolves.getId("powerups/flee_health.json"),FleeOnLowHealthGoal.class);
    }

    @Override
    public void LevelUpAction(WolfEntity wolf, WolfStatsEnum type, int number) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected Goal goalConstructor(WolfEntity wolf) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, SecurityException {
        return (Goal)relevantGoal.getDeclaredConstructors()[0].newInstance(wolf, 7.0F, 1.5D, 1.0D, 4.0F);
    }
    
}
