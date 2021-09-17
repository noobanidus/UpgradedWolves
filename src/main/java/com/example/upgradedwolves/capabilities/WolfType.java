package com.example.upgradedwolves.capabilities;

public enum WolfType {
    NotSet(0),
    Fighter(1),
    Scavenger(2),
    Show(3);

    private int value;
    private WolfType(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
