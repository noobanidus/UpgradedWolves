package com.example.upgradedwolves.personality;

import java.util.List;
import java.util.stream.Stream;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.personality.expressions.Expressions;
import com.example.upgradedwolves.personality.expressions.PassiveExpression;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.animal.Wolf;

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
    public void setWolfExpressions(Wolf wolf) {
        getExpressions().forEach(x -> assignExpressions(wolf,x));
        
    }

    @Override
    public Stream<Class<? extends Expressions>> getExpressions() {
        //TODO: attempt to make this non hard coded
        return Stream.of(PassiveExpression.class);
    }

    private void assignExpressions(Wolf wolf, Class<? extends Expressions> clazz){
        try{
            clazz.getConstructors()[0].newInstance(wolf,subBehavior);
        } catch (Exception ignored){
            UpgradedWolves.LOGGER.error("Failed to add Expression:" + clazz.getName());
        }
    }
    
}
