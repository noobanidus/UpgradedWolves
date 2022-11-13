package com.example.upgradedwolves.utils;

import java.util.Random;

import net.minecraft.util.RandomSource;

public class RandomRangeTimer {
    public final int coolDown;
    public final int upwardRange;
    private final RandomSource random;
    private Runnable functionToRun;
    private int count;
    private int max;

    public RandomRangeTimer(int coolDown,int upwardRange,RandomSource random){
        this.coolDown = coolDown;
        this.upwardRange = upwardRange;
        this.random = random;
        reset();
    }

    public void setFunction(Runnable function){
        this.functionToRun = function;
    }

    public void tick(){
        count++;
        if(count > max){
            functionToRun.run();
            reset();
        }
    }

    private void reset(){
        max = coolDown + random.nextInt(upwardRange);
        count = 0;
    }
}
