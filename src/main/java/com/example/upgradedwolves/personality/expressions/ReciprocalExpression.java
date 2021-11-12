package com.example.upgradedwolves.personality.expressions;

import com.example.upgradedwolves.personality.Behavior;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;

public class ReciprocalExpression extends Expressions {

    public ReciprocalExpression(Wolf wolf, Behavior subBehavior) {
        super(wolf, subBehavior);
        //TODO Auto-generated constructor stub
    }

    @Override
    protected void changeState(int stateNumber) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void changeEngagement() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void setDefaultEngagement() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected LivingEntity searchForPartner() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setPartnerExternal(LivingEntity partner){
        
    }
    
}
