package com.example.upgradedwolves.utils;

import net.minecraft.world.entity.TamableAnimal;

public class CommonWolfUtils {

    public static boolean shareOwner(TamableAnimal first, TamableAnimal second){
        return first.isTame() && second.isTame() && first.getOwner() == second.getOwner();
    }
    
}
