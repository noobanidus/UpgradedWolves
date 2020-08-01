package com.example.upgradedwolves.capabilities;

public interface IWolfStats {
    public void addXp(WolfStatsEnum wolfStats,int amount);
    public int getXp(WolfStatsEnum wolfStats);
    public int getLevel(WolfStatsEnum wolfStats);
    public void setLevel(WolfStatsEnum wolfStats,int amount);
    public int getWolfType();
    public void setWolfType(int type);
}