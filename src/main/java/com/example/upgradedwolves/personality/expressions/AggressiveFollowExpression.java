package com.example.upgradedwolves.personality.expressions;

import com.example.upgradedwolves.personality.Behavior;
import com.example.upgradedwolves.utils.RandomRangeTimer;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;

public class AggressiveFollowExpression extends Expressions {
    protected RandomRangeTimer whine;


    public AggressiveFollowExpression(Wolf wolf, Behavior subBehavior) {
        super(wolf, subBehavior);
        maxEngagement = 100;
        whine = new RandomRangeTimer(100,400,wolf.getRandom());
    }

    @Override
    public void tick(){
        super.tick();
        switch(subBehavior){
            case Affectionate:
                attach(.75f,1.4f);
                break;
            case Social:
            case Playful:
                attach(1.5f,1f);
                break;
            case Aggressive:
            case Dominant:
                attach(1,1.3f);    
                break;
            case Shy:
            case Lazy:
                attach(2,.75f);
                break;
        }
    }

    @Override
    protected void changeState(int stateNumber) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void changeEngagement() {
        if(partner.distanceTo(wolf) > 3)
            engagement--;
        else
            engagement++;
        
    }

    @Override
    protected void setDefaultEngagement() {        
        engagement = 50;
    }

    @Override
    protected LivingEntity searchForPartner() {        
        return getAnotherWolfOrOwner();
    }

    private void attach(float distance, float speed){
        if(partner.distanceTo(wolf) > distance){
            wolf.getMoveControl().setWantedPosition(partner.getX(), partner.getY(), partner.getZ(), speed);
        }
    }
    
}
