package com.example.upgradedwolves.personality.expressions;

import com.example.upgradedwolves.personality.Behavior;
import com.example.upgradedwolves.personality.CommonActionsController;
import com.example.upgradedwolves.utils.RandomRangeTimer;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;

public class IntimidateExpression extends Expressions {
    private CommonActionsController controller;
    private RandomRangeTimer growl;
    private RandomRangeTimer jump;
    private RandomRangeTimer attack;
    private boolean canAttack = true;

    public IntimidateExpression(Wolf wolf, Behavior subBehavior) {
        super(wolf, subBehavior);
        maxEngagement = 250;
        controller = new CommonActionsController(wolf);
        growl = new RandomRangeTimer(40,40,wolf.getRandom());
        jump = new RandomRangeTimer(20,10,wolf.getRandom());
        growl.setFunction(() -> controller.growl());
        jump.setFunction(() -> controller.jumpLateral());
        attack = new RandomRangeTimer(35,5,wolf.getRandom());
        attack.setFunction(() -> {canAttack = true;});
    }

    @Override
    public void stop(){
    }

    @Override
    public void tick(){
        super.tick();
        wolf.getLookControl().setLookAt(partner);
        wolf.setYRot(wolf.getYHeadRot());
        subBehaviorTick();
    }

    @Override
    protected void subBehaviorTick() {
        switch(subBehavior){
            case Playful:
                if(partner.distanceTo(wolf) > 5){
                    jump.tick();
                } else {
                    wolf.getMoveControl().strafe(-.25f, 0);
                }
                growl.tick();
                break;
            case Affectionate:
            case Social:
                if(partner.distanceTo(wolf) < 5){
                    wolf.getMoveControl().strafe(-.25f, 0);
                }
                growl.tick();
                break;
            case Shy:
                if(partner.distanceTo(wolf) < 20){
                    wolf.getMoveControl().strafe(-.25f, 0);
                }
                break;
            case Aggressive:
                if(partner.distanceTo(wolf) < 2 && wolf.isOnGround()){
                    if(canAttack){
                        controller.jumpTowards(partner);
                        partner.hurt(DamageSource.mobAttack(wolf),2);
                        canAttack = false;
                    }
                }
                if(!canAttack){
                    attack.tick();
                }
            case Dominant:
                if(partner.distanceTo(wolf) > 5){
                    wolf.getMoveControl().strafe(.25f,0);
                }
            case Lazy:
                growl.tick();
                break;
            case EMPTY:
                break;
        }
    }

    @Override
    protected void changeState(int stateNumber) {
        arbitraryState = stateNumber;
    }

    @Override
    protected void changeEngagement() {
        float distance = partner.distanceTo(wolf);
        if(distance >= 10)
            engagement -= (.03 * (distance-4.3) * (distance-4.3));
        else if(engagement < maxEngagement)
            engagement++;        
    }

    @Override
    protected void setDefaultEngagement() {
        engagement = 60;
    }

    @Override
    protected LivingEntity searchForPartner() {        
        return getNonFriendlyPartner();
    }
}
