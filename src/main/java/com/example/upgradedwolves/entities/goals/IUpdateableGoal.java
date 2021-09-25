package com.example.upgradedwolves.entities.goals;

import com.example.upgradedwolves.capabilities.IWolfStats;

import net.minecraft.world.entity.animal.Wolf;

public interface IUpdateableGoal {
    public void Update(IWolfStats handler,Wolf wolf);
}
