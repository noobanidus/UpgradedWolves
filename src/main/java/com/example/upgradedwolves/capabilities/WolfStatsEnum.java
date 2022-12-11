package com.example.upgradedwolves.capabilities;

public enum WolfStatsEnum {
    Speed(0,"Speed"),
    Strength(1,"Strength"),
    Intelligence(2,"Intelligence"),
    Love(3,"");

    private final int id;
    private final String name;

    private WolfStatsEnum(int p_46390_, String p_46391_) {
        this.id = p_46390_;
        this.name = p_46391_;
    }

    public String getName() {
        return this.name;
     }
}