package com.example.upgradedwolves.powerup;

import java.lang.reflect.InvocationTargetException;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class BonusStatPowerUp extends PowerUp{
    protected double bonus;

    public BonusStatPowerUp(int levelRequirement, String resourceLocationName,int effectiveLevel) {
        super(levelRequirement, resourceLocationName);
        this.effectiveLevel = effectiveLevel;
    }

    protected abstract void enhanceAttribute(Wolf wolf, IWolfStats handler);    

    @Override
    public Goal fetchRelevantGoal(Wolf wolf) {
        IWolfStats handler = WolfStatsHandler.getHandler(wolf);
        int statLevel = handler.getLevel(statType);
        if(statLevel >= levelRequirement){
            enhanceAttribute(wolf,handler);
        }
        return null;
    }

    @Override
    protected Goal goalConstructor(Wolf wolf) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, SecurityException {        
        return null;
    }

    @Override
    public ITextComponent getDescription(Wolf wolf){
        return new TranslationTextComponent(description,wolf.hasCustomName() ? wolf.getCustomName() : "this wolf");
    }
    
}
