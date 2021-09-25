package com.example.upgradedwolves.powerup;

import java.lang.reflect.InvocationTargetException;

import com.example.upgradedwolves.entities.goals.ShareItemGoal;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Wolf;

public class ShareItemPowerUp extends PowerUp {

    public ShareItemPowerUp(int levelRequirement) {
        super(levelRequirement, "share_item", ShareItemGoal.class);
        
    }

    @Override
    protected Goal goalConstructor(Wolf wolf) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, SecurityException {        
        return genericGoalConstructor(wolf);
    }
    
}
