package com.example.upgradedwolves.items.GoldenBone;

import com.example.upgradedwolves.capabilities.IWolfStats;

import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.Item;


public abstract class GoldenBoneAbstract extends Item {

    public GoldenBoneAbstract(Properties properties) {
        super(properties);
    }

    public abstract void rightClickWolf(Wolf wolf,IWolfStats handler);
}
