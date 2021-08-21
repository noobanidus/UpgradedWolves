package com.example.upgradedwolves.powerup;

import java.lang.reflect.InvocationTargetException;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.ResourceLocation;

public abstract class BonusStatPowerUp extends PowerUp{

    public BonusStatPowerUp(int levelRequirement, ResourceLocation resourceLocation,int effectiveLevel) {
        super(levelRequirement, resourceLocation);
        this.effectiveLevel = effectiveLevel;
    }

    protected abstract void enhanceAttribute(WolfEntity wolf);    

    @Override
    public Goal fetchRelevantGoal(WolfEntity wolf) {
        enhanceAttribute(wolf);
        return null;
    }

    @Override
    protected Goal goalConstructor(WolfEntity wolf) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, SecurityException {        
        enhanceAttribute(wolf);
        return null;
    }

    
}
