package com.example.upgradedwolves.entities.goals;

import java.util.List;
import java.util.Random;

import com.example.upgradedwolves.entities.utilities.AbilityEnhancer;
import com.example.upgradedwolves.entities.utilities.EntityFinder;
import com.example.upgradedwolves.network.PacketHandler;
import com.example.upgradedwolves.network.message.CreateParticleForMobMessage;

import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;

public class BarkStunGoal extends CoolDownGoal {
    protected final EntityFinder<Monster> entityFinder;
    protected final Wolf wof;
    private List<Monster> enemies;
    private final Random rand;
    private int stunDuration = 30;

    public BarkStunGoal(Wolf wolf){
        this.wof = wolf;
        this.entityFinder = new EntityFinder<>(wof, Monster.class);
        stunDuration = 30 + AbilityEnhancer.minMaxIncrease(wof, 90, 10, 50);
        setCoolDownInSeconds(20);
        rand = new Random();
    }

    @Override
    public boolean canUse() {
        if(active()){
            List<Monster> entityList = entityFinder.findWithPredicate(5, 2, enemy -> !(enemy instanceof NeutralMob) || ((NeutralMob)enemy).getTarget() != null);
            if(entityList.size() > 0){
                enemies = entityList;
                return true;
            }
        }
        return false;
    }

    @Override
    public void start() {
        wof.playSound(SoundEvents.WOLF_AMBIENT, 20, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.02F + .7F);        
        for (Monster mobEntity : enemies) {
            mobEntity.setNoAi(true);
            PacketHandler.instance.send(PacketDistributor.TRACKING_ENTITY.with(() -> mobEntity), new CreateParticleForMobMessage(mobEntity.getId(),ParticleTypes.FLAME,10));
        }
        startCoolDown();
    }
    
    @Override
    public boolean canContinueToUse() {
        int bonus = AbilityEnhancer.minMaxIncrease(wof, 90, 10, 50);
        if(stunDuration-- <= 0){
            for (Monster mobEntity : enemies) {
                mobEntity.setNoAi(false);
            }
            stunDuration = 50 + bonus;
            return false;
        }
        return true;
    }
}
