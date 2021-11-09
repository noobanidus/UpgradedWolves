package com.example.upgradedwolves.personality;

import java.util.List;

import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.personality.expressions.Expressions;

public abstract class WolfPersonality {    
    public Behavior subBehavior;

    protected final Behavior mainBehavior;
    protected String name;

    protected WolfPersonality(Behavior mainBehavior){
        this.mainBehavior = mainBehavior;
    }

    public abstract String getName();

    public abstract int levelUpStatBonus(WolfStatsEnum stats);

    public abstract List<Expressions> getExpressions();
}
