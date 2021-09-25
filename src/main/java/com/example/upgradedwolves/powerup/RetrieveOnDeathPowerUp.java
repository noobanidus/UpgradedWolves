package com.example.upgradedwolves.powerup;

import java.lang.reflect.InvocationTargetException;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Wolf;

public class RetrieveOnDeathPowerUp extends PowerUp {

    public RetrieveOnDeathPowerUp(int levelRequirement) {
        super(levelRequirement, "death_retrieval");
    }

    @Override
    protected Goal goalConstructor(Wolf wolf) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, SecurityException {
        IWolfStats handler = WolfStatsHandler.getHandler(wolf);
        handler.setRetrievalFlag(true);
        return null;
    }
    
}
