package com.example.upgradedwolves.personality;

import java.util.List;
import java.util.Random;
import java.util.Set;

import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.personality.expressions.Expressions;

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

    public abstract String getName();

    public abstract int levelUpStatBonus(WolfStatsEnum stats);

    public abstract List<Expressions> getExpressions();
}
