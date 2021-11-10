package com.example.upgradedwolves.personality.expressions;

import com.example.upgradedwolves.personality.Behavior;
import com.example.upgradedwolves.personality.CommonActionsController;
import com.example.upgradedwolves.utils.RandomRangeTimer;

import net.minecraft.world.entity.animal.Wolf;

public class IntimidateExpression extends Expressions {
    private CommonActionsController controller;
    private RandomRangeTimer growl;
    private RandomRangeTimer jump;

    public IntimidateExpression(Wolf wolf, Behavior subBehavior) {
        super(wolf, subBehavior);
        controller = new CommonActionsController(wolf);
        growl = new RandomRangeTimer(40,40,wolf.getRandom());
        jump = new RandomRangeTimer(20,10,wolf.getRandom());
        growl.setFunction(() -> controller.growl());
        jump.setFunction(() -> controller.jumpLateral());
    }

    @Override
    public void stop(){
    }

    @Override
    public void tick(){
        super.tick();
        wolf.getLookControl().setLookAt(partner);
        switch(subBehavior){
            case Affectionate:
            case Social:
            case Playful:
                
                break;
            case Shy:
            case Aggressive:
            case Dominant:
            case Lazy:
                break;
        }
    }

    @Override
    protected void changeState(int stateNumber) {
        
        
    }

    @Override
    protected void changeEngagement() {
        
        
    }

    @Override
    protected void setDefaultEngagement() {
        
        
    }

    @Override
    protected void searchForPartner() {
        
        
    }
    
}
