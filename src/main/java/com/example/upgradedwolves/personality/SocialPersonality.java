package com.example.upgradedwolves.personality;

import java.util.stream.Stream;

import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.personality.expressions.Expressions;
import com.example.upgradedwolves.personality.expressions.SocializeExpression;

public class SocialPersonality extends WolfPersonality {

    protected SocialPersonality() {
        super(Behavior.Social);        
    }

    @Override
    public String getName() {
        
        return null;
    }

    @Override
    public int levelUpStatBonus(WolfStatsEnum stats) {
        if(stats == WolfStatsEnum.Intelligence)
            return 2;
        return 0;
    }

    @Override
    public Stream<Class<? extends Expressions>> getExpressions() {
        return Stream.of(SocializeExpression.class);
    }
    
}
