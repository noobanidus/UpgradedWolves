package com.example.upgradedwolves.personality;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.personality.expressions.Expressions;

import net.minecraft.world.entity.animal.Wolf;

public abstract class WolfPersonality {    
    public Behavior subBehavior;

    protected final Behavior mainBehavior;
    protected String name;

    protected WolfPersonality(Behavior mainBehavior){
        this.mainBehavior = mainBehavior;
    }

    public static WolfPersonality getRandomWolfPersonality(){
        //TODO: make this not hardcoded
        Set<WolfPersonality> personalities = Set.of(new PassivePersonality());
        Random rand = new Random();

        //bruh.... wtf?
        return (WolfPersonality)personalities.toArray()[rand.nextInt(personalities.toArray().length)];
    }
    
    public void setWolfExpressions(Wolf wolf) {
        getExpressions().forEach(x -> assignExpressions(wolf,x));        
    }
    
    public abstract String getName();

    public abstract int levelUpStatBonus(WolfStatsEnum stats);

    public abstract Stream<Class<? extends Expressions>> getExpressions();

    protected void assignExpressions(Wolf wolf, Class<? extends Expressions> clazz){
        try{
            clazz.getConstructors()[0].newInstance(wolf,subBehavior);
        } catch (Exception ignored){
            UpgradedWolves.LOGGER.error("Failed to add Expression:" + clazz.getName());
        }
    }
}
