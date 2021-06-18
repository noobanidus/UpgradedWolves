package com.example.upgradedwolves.items.GoldenBone;

import com.example.upgradedwolves.capabilities.IWolfStats;

import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.Item;


public abstract class GoldenBoneAbstract extends Item {

    public GoldenBoneAbstract(Properties properties) {
        super(properties);
    }

    public abstract void rightClickWolf(WolfEntity wolf,IWolfStats handler);
}
