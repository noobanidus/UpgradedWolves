package com.example.upgradedwolves.capabilities;

import net.minecraft.item.ItemStack;

public interface IWolfStats {
    public void addXp(WolfStatsEnum wolfStats,int amount);
    public int getXp(WolfStatsEnum wolfStats);
    public int getLevel(WolfStatsEnum wolfStats);
    public void setLevel(WolfStatsEnum wolfStats,int amount);
    public int getWolfType();
    public void setWolfType(int type);
    public void InitLove();
    public double getWolfSpeed();
    public int getWolfStrength();
    //public ItemStackHandler getInventory();
    public boolean addItemStack(ItemStack item);
}