package com.example.upgradedwolves.personality.expressions;

import com.example.upgradedwolves.personality.Behavior;
import com.example.upgradedwolves.personality.CommonActionsController;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;

public class ReciprocalExpression extends Expressions {
    CommonActionsController controller;


    public ReciprocalExpression(Wolf wolf, Behavior subBehavior) {
        super(wolf, subBehavior);
        controller = new CommonActionsController(wolf);
    }

    @Override
    public void tick(){
        if(arbitraryState == 1){
            chase();
        }else if(arbitraryState == 2){
            jumpAt();
        }else{
            glare();
        }
    }
    
    @Override
    protected void changeState(int stateNumber) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void changeEngagement() {
        engagement--;
    }

    @Override
    protected void setDefaultEngagement() {
        engagement = 300;
        
    }
    
    @Override
    protected LivingEntity searchForPartner() {
        // TODO Auto-generated method stub
        return null;
    }
    
    public void setPartnerExternal(LivingEntity partner){
        if(!isActive()){
            this.partner = partner;
        }
    }
    
    private void glare() {
        wolf.getLookControl().setLookAt(partner);
    }

    private void jumpAt() {
        controller.jumpTowards(partner);
    }

    private void chase() {
        wolf.getNavigation().moveTo(partner, 1);
    }
    
}
