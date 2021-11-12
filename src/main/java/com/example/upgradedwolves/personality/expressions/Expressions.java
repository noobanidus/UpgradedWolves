package com.example.upgradedwolves.personality.expressions;

import java.util.EnumSet;
import java.util.List;

import com.example.upgradedwolves.entities.utilities.EntityFinder;
import com.example.upgradedwolves.personality.Behavior;

import org.apache.logging.log4j.LogManager;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;

public abstract class Expressions extends Goal {
    //Add Wolf Behavior
    public final Wolf wolf;
    
    protected int arbitraryState;
    protected Behavior subBehavior;
    protected boolean waitingForInvite;
    protected int engagement;
    protected int maxEngagement;
    protected int defaultState = 0;
    
    LivingEntity partner;

    public Expressions(Wolf wolf,Behavior subBehavior){
        this.wolf = wolf;
        this.subBehavior = subBehavior;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK,Goal.Flag.TARGET));
    }

    @Override
    public boolean canUse(){
        arbitraryState = defaultState;
        partner = searchForPartner();
        setDefaultEngagement();
        return partner != null;
    }

    @Override
    public void tick(){
        changeEngagement();
    }

    @Override
    public boolean canContinueToUse(){
        if(bored()){
            partner = null;
            return false;
        }
        return true;
    }

    public void reciprocateAction(Wolf wolfEntity){

    }
    
    protected abstract void changeState(int stateNumber);
    
    protected abstract void changeEngagement();

    protected abstract void setDefaultEngagement();

    protected abstract LivingEntity searchForPartner();


    protected void attacked(LivingEntity attackedBy){

    }        

    protected boolean bored(){
        return engagement <= 0 || partner == null;
    }

    protected LivingEntity getRandomPartner(){
        EntityFinder<LivingEntity> finder = new EntityFinder<LivingEntity>(wolf,LivingEntity.class);
        return getRandomPartner(finder);
    }

    protected <T extends LivingEntity> LivingEntity getRandomPartner(EntityFinder<T> finder){
        List<T> entities = finder.findWithinRange(10.0, 5.0);
        return setPartnerFromList(entities);
    }

    protected LivingEntity getOwnerAsPartner(){
        if(wolf.isTame()){
            return wolf.getOwner();
        } else {
            LogManager.getLogger().info("Wolf is not tame, cannot set owner as partner");
            return null;
        }
    }

    protected LivingEntity getAnotherWolfOrOwner(){
        EntityFinder<LivingEntity> finder = new EntityFinder<LivingEntity>(wolf,LivingEntity.class);
        List<LivingEntity> entities = finder.findWithPredicate(10, 5, x -> (x instanceof Wolf && shareOwner((Wolf)x)) || x == wolf.getOwner());
        return setPartnerFromList(entities);
    }

    protected LivingEntity getNonFriendlyPartner(){
        EntityFinder<LivingEntity> playerOrMonster = new EntityFinder<LivingEntity>(wolf,LivingEntity.class);
        List<LivingEntity> entities = playerOrMonster.findWithPredicate(10, 5, x -> (x instanceof Monster && !(x instanceof AbstractSkeleton)) || (x instanceof Player && !isOwner((Player)x)) || (x instanceof Wolf && !sameSide((Wolf)x)));
        return setPartnerFromList(entities);
    }
    
    
    protected <T> T setPartnerFromList(List<T> entities){
        if(entities.size() > 0){
            int rand = wolf.getRandom().nextInt(entities.size());
            return entities.get(rand);
        }
        return null;
    }

    private boolean isOwner(Player player){
        return wolf.isTame() && wolf.getOwner() == player;
    }

    private boolean shareOwner(Wolf otherWolf){
        return otherWolf.isTame() && otherWolf.getOwner() == wolf.getOwner();
    }

    private boolean sameSide(Wolf otherWolf){
        return shareOwner(otherWolf) || (!wolf.isTame() && !otherWolf.isTame());
    }

}
