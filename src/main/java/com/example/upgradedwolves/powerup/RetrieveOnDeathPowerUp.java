package com.example.upgradedwolves.powerup;

import java.lang.reflect.InvocationTargetException;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.WolfEntity;

public class RetrieveOnDeathPowerUp extends PowerUp {

    public RetrieveOnDeathPowerUp(int levelRequirement) {
        super(levelRequirement, "death_retrieval");
    }

    @Override
    protected Goal goalConstructor(WolfEntity wolf) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, SecurityException {
        IWolfStats handler = WolfStatsHandler.getHandler(wolf);
        handler.setRetrievalFlag(true);
        return null;
    }
    
}
