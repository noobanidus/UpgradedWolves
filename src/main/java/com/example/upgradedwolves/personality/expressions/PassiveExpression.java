package com.example.upgradedwolves.personality.expressions;

import java.util.List;

import com.example.upgradedwolves.entities.utilities.EntityFinder;
import com.example.upgradedwolves.personality.Behavior;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class PassiveExpression extends Expressions {

    Vec3 position;
    int barkSpam;

    public PassiveExpression(Wolf wolf, Behavior subBehavior) {
        super(wolf, subBehavior);        
    }
    

    @Override
    public void stop(){
        this.wolf.getNavigation().stop();
        position = null;
    }

    @Override
    public void tick(){
        super.tick();
        wolf.getLookControl().setLookAt(partner);
        switch(subBehavior){
            case Affectionate:
            case Social:
            case Playful:
            case Shy:
                runAway();
                break;
            case Aggressive:
                bark();
                runAway();
                break;
            case Dominant:
            case Lazy:
                //Do nothing..
                break;
        }
    }

    @Override
    protected void changeState(int stateNumber) {
        
        
    }

    @Override
    protected void changeEngagement() {
        if(partner.distanceTo(wolf) >= 10)
            engagement--;
        else if(engagement < maxEngagement)
            engagement++;
    }

    @Override
    protected void setDefaultEngagement() {
        engagement = 60;
    }

    @Override
    protected void searchForPartner() {
        //All monsters except skeletons
        EntityFinder<LivingEntity> playerOrMonster = new EntityFinder<LivingEntity>(wolf,LivingEntity.class);
        List<LivingEntity> entities = playerOrMonster.findWithPredicate(10, 5, x -> (x instanceof Monster && !(x instanceof AbstractSkeleton)) || (x instanceof Player && !isOwner((Player)x)));
        setPartner(setPartnerFromList(entities));
    }
    
    private boolean isOwner(Player player){
        return wolf.isTame() && wolf.getOwner() == player;
    }

    private void runAway(){
        if(position == null || position.distanceTo(partner.getPosition(1)) < 10 || !movingToPosition()){
            position = DefaultRandomPos.getPosAway(wolf,20,5,partner.getPosition(1));
            if(position != null){
                Path path = wolf.getNavigation().createPath(position.x,position. y, position.z, 0);
                wolf.getNavigation().moveTo(path, 1);
            }
        }
    }

    private boolean movingToPosition(){
        return wolf.getNavigation().isInProgress();
    }

    private void bark(){
        if(barkSpam-- <= 0){
            barkSpam = wolf.getRandom().nextInt(40) + 10;
            wolf.playAmbientSound();
        }
    }
}
