package com.example.upgradedwolves.entities.goals;

import com.example.upgradedwolves.capabilities.IWolfStats;

import net.minecraft.entity.passive.WolfEntity;

public interface IUpdateableGoal {
    public void Update(IWolfStats handler,WolfEntity wolf);
}
