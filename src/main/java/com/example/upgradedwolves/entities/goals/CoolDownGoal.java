package com.example.upgradedwolves.entities.goals;

import net.minecraft.entity.ai.goal.Goal;

public abstract class CoolDownGoal extends Goal {
    protected int timeLeft;
    protected int defaultCooldown;    

    protected void setCoolDownInSeconds(double seconds){
        timeLeft = (int)(seconds * 20 / 4);
        defaultCooldown = (int)(seconds * 20);
    }

    protected void startCoolDown(){
        timeLeft = defaultCooldown;        
    }

    protected boolean active(){
        return timeLeft-- <= 0;
    }

    @Override
    public void tick() {        
        super.tick();        
    }
}
