package com.example.upgradedwolves.personality.expressions;

import com.example.upgradedwolves.personality.Behavior;
import com.example.upgradedwolves.utils.RandomRangeTimer;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class SocializeExpression extends Expressions {
    protected RandomRangeTimer observe;
    protected boolean available;
    protected Vec3 position;

    public SocializeExpression(Wolf wolf, Behavior subBehavior) {
        super(wolf, subBehavior);
        maxEngagement = 140;
        observe = new RandomRangeTimer(140,140,wolf.getRandom());
        observe.setFunction(() -> {available = true;});
    }

    @Override
    public void tick(){
        super.tick();
        wolf.getLookControl().setLookAt(partner);
        switch(subBehavior){
            case Affectionate:
            case Social:
            case Playful:                
            case Aggressive:                
            case Dominant:
                runTowards();
                break;
            case Shy:
            case Lazy:                
                break;
        }
    }

    @Override
    protected void changeState(int stateNumber) {
        
        
    }

    @Override
    protected void changeEngagement() {
        engagement--;
    }

    @Override
    protected void setDefaultEngagement() {
        engagement = 80 + wolf.getRandom().nextInt(60);
        
    }

    @Override
    protected LivingEntity searchForPartner() {
        if(available){
            available = false;
            return getNonFriendlyPartner();
        }
        observe.tick();
        return null;
    }
    
    private void runTowards(){
        if(position == null || wolf.getPosition(0).distanceTo(partner.getPosition(1)) > 5 || !wolf.getNavigation().isInProgress()){
            position = DefaultRandomPos.getPosTowards(wolf,20,5,partner.getPosition(1),1);
            if(position != null){
                Path path = wolf.getNavigation().createPath(position.x,position. y, position.z, 0);
                wolf.getNavigation().moveTo(path, 1);
            }
        }
    }
}
