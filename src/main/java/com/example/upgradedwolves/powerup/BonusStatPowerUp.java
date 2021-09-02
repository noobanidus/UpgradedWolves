package com.example.upgradedwolves.powerup;

import java.lang.reflect.InvocationTargetException;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class BonusStatPowerUp extends PowerUp{
    protected double bonus;

    public BonusStatPowerUp(int levelRequirement, String resourceLocationName,int effectiveLevel) {
        super(levelRequirement, resourceLocationName);
        this.effectiveLevel = effectiveLevel;
    }

    protected abstract void enhanceAttribute(WolfEntity wolf, IWolfStats handler);    

    @Override
    public Goal fetchRelevantGoal(WolfEntity wolf) {
        IWolfStats handler = WolfStatsHandler.getHandler(wolf);
        int statLevel = handler.getLevel(statType);
        if(statLevel >= levelRequirement){
            enhanceAttribute(wolf,handler);
        }
        return null;
    }

    @Override
    protected Goal goalConstructor(WolfEntity wolf) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, SecurityException {        
        return null;
    }

    @Override
    public ITextComponent getDescription(WolfEntity wolf){
        return new TranslationTextComponent(description,wolf.hasCustomName() ? wolf.getCustomName() : "this wolf");
    }
    
}
