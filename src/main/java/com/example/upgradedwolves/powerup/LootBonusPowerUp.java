package com.example.upgradedwolves.powerup;

import java.lang.reflect.InvocationTargetException;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Wolf;

public class LootBonusPowerUp extends PowerUp {

    public LootBonusPowerUp(int levelRequirement) {
        super(levelRequirement, "loot_bonus");        
    }

    @Override
    protected Goal goalConstructor(Wolf wolf) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, SecurityException {
        IWolfStats handler = WolfStatsHandler.getHandler(wolf);
        handler.setLootFlag(true);
        return null;
    }
    
}
