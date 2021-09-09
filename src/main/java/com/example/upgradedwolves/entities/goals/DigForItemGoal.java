package com.example.upgradedwolves.entities.goals;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.passive.WolfEntity;

public class DigForItemGoal extends CoolDownGoal {
    public WolfEntity wolf;
    protected BlockState type;
    protected final int timer = 40;
    protected int currentTime;

    public DigForItemGoal (WolfEntity wolf){
        this.wolf = wolf;
        setCoolDownInSeconds(1800);
        currentTime = 0;
    }

    @Override
    public boolean shouldExecute() {
        //Gets block "type" at the position below the wolf
        BlockState blockStandingOn = wolf.world.getBlockState(wolf.getPosition().add(0, -1, 0));        
        if(active() && blockStandingOn.getMaterial() == Material.EARTH || blockStandingOn.getMaterial() == Material.SAND){
            type = blockStandingOn;
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldContinueExecuting() {
        if(currentTime++ < timer){
            wolf.playSound(type.getBlock().getSoundType(null, null, null, null).getPlaceSound(), 0.5F, (1.0F + (wolf.getRNG().nextFloat() - wolf.getRNG().nextFloat()) * 0.2F) * 0.7F);
            return true;
        }
        currentTime = 0;
        startCoolDown();
        return false;
    }
    
}
