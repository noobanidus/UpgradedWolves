package com.example.upgradedwolves.entities.goals;

import net.minecraft.world.entity.ai.goal.Goal;

public abstract class CoolDownGoal extends Goal {
    protected int timeLeft;
    protected int defaultCooldown;    

    protected void setCoolDownInSeconds(double seconds){
        timeLeft = (int)(seconds * 20 * 3 / 4);
        defaultCooldown = (int)(seconds * 20);
    }

    protected void startCoolDown(){
        startCoolDown(0);
    }

    protected void startCoolDown(int reduction){
        timeLeft = defaultCooldown - reduction;        
    }

    protected boolean active(){
        return timeLeft-- <= 0;
    }

    @Override
    public void tick() {        
        super.tick();        
    }
}
