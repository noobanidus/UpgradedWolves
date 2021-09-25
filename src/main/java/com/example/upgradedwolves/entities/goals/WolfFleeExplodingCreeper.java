package com.example.upgradedwolves.entities.goals;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;

import net.minecraft.world.entity.animal.Wolf;

public class WolfFleeExplodingCreeper extends FleeExplodingCreeper implements IUpdateableGoal {
    Wolf wolf;

    public WolfFleeExplodingCreeper(Wolf entityIn, float avoidDistanceIn, double farSpeedIn,
            double nearSpeedIn) {
        super(entityIn, avoidDistanceIn, farSpeedIn, nearSpeedIn);
        this.wolf = entityIn;
    }
    
    @Override
    public boolean canUse(){
        if(WolfStatsHandler.getHandler(wolf).getLevel(WolfStatsEnum.Intelligence) > 5)
            return super.canUse();
        return false;
    }

    @Override
    public void Update(IWolfStats handler, Wolf wolf) {
    }
}
