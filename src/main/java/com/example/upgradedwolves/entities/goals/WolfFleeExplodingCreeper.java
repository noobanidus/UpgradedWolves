package com.example.upgradedwolves.entities.goals;

import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;

import net.minecraft.entity.passive.WolfEntity;

public class WolfFleeExplodingCreeper extends FleeExplodingCreeper {
    WolfEntity wolf;

    public WolfFleeExplodingCreeper(WolfEntity entityIn, float avoidDistanceIn, double farSpeedIn,
            double nearSpeedIn) {
        super(entityIn, avoidDistanceIn, farSpeedIn, nearSpeedIn);
        this.wolf = entityIn;
    }
    
    @Override
    public boolean shouldExecute(){
        if(WolfStatsHandler.getHandler(wolf).getLevel(WolfStatsEnum.Intelligence) > 5)
            super.shouldExecute();
        return false;
    }
}
