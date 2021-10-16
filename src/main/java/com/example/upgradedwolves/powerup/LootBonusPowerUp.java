package com.example.upgradedwolves.powerup;

import java.lang.reflect.InvocationTargetException;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Wolf;

public class LootBonusPowerUp extends PowerUp {

    public LootBonusPowerUp(int levelRequirement) {
        super(levelRequirement, "loot_bonus"); 
        this.active = false;
        this.uLocation = 166;
        this.vLocation = 198;
        this.statType = WolfStatsEnum.values()[0];    
    }

    @Override
    protected Goal goalConstructor(Wolf wolf) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, SecurityException {
        IWolfStats handler = WolfStatsHandler.getHandler(wolf);
        handler.setLootFlag(true);
        return null;
    }
    
}
