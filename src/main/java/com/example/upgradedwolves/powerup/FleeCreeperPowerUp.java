package com.example.upgradedwolves.powerup;

import java.lang.reflect.InvocationTargetException;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.entities.goals.WolfFleeExplodingCreeper;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.ResourceLocation;

public class FleeCreeperPowerUp extends PowerUp {

    public FleeCreeperPowerUp(int levelRequirement) {
        super(levelRequirement, UpgradedWolves.getId("powerups/flee_creeper.json"),WolfFleeExplodingCreeper.class);        
    }

    @Override
    public void LevelUpAction(WolfEntity wolf, WolfStatsEnum type, int number) {
        
        
    }

    @Override
    protected Goal goalConstructor(WolfEntity wolf) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, SecurityException {        
        return (Goal)relevantGoal.getDeclaredConstructors()[0].newInstance(wolf, 7.0F, 1.5D, 1.5D);
    }
    
}
