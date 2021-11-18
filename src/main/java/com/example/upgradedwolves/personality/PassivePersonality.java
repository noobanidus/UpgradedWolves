package com.example.upgradedwolves.personality;

import java.util.stream.Stream;

import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.personality.expressions.Expressions;
import com.example.upgradedwolves.personality.expressions.PassiveExpression;

public class PassivePersonality extends WolfPersonality {

    protected PassivePersonality() {
        super(Behavior.Shy);
        //TODO Auto-generated constructor stub
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
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
        //TODO: attempt to make this non hard coded
        return Stream.of(PassiveExpression.class);
    }
}
