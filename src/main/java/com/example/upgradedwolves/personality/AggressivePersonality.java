package com.example.upgradedwolves.personality;

import java.util.stream.Stream;

import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.personality.expressions.Expressions;
import com.example.upgradedwolves.personality.expressions.IntimidateExpression;

import net.minecraft.world.entity.animal.Wolf;

public class AggressivePersonality extends WolfPersonality {

    protected AggressivePersonality(Behavior mainBehavior) {
        super(mainBehavior);
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int levelUpStatBonus(WolfStatsEnum stats) {
        if(stats == WolfStatsEnum.Strength)
            return 2;
        return 0;
    }

    @Override
    public Stream<Class<? extends Expressions>> getExpressions() {        
        return Stream.of(IntimidateExpression.class);
    }
    
}
