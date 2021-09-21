package com.example.upgradedwolves.entities.utilities;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;

import net.minecraft.entity.passive.WolfEntity;

public class AbilityEnhancer {
    public static int detectionSkill(WolfEntity wolf){
        IWolfStats handler = WolfStatsHandler.getHandler(wolf);
        return handler.getLevel(WolfStatsEnum.Intelligence);
    }

    public static int minMaxIncrease(WolfEntity wolf, int maxIncrease, int minLevel, int maxLevel){
        int level = detectionSkill(wolf);
        if(level <= minLevel){
            return 0;
        }
        else if (level >= maxLevel){
            return maxIncrease;
        }
        else{
            return (int)((double)(level - minLevel) / (maxLevel - minLevel) * maxIncrease);
        }
    }
    public static int increaseEveryLevel(WolfEntity wolf, int minLevel, int divisor, int amount){
        int level = detectionSkill(wolf);
        if(level < minLevel){
            return 0;
        }
        else{
            return ((level - minLevel) / divisor) * amount;
        }
    }
    public static int increaseEveryLevel(WolfEntity wolf, int minLevel, int divisor){
        return increaseEveryLevel(wolf, minLevel, divisor,0);
    }

    public static int increaseMin(WolfEntity wolf, int minLevel){
        int level = detectionSkill(wolf);
        if(level < minLevel)
            return 0;
        else{
            return level - minLevel;
        }
    }
}
