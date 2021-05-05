package com.example.upgradedwolves.capabilities;

public enum WolfType {
    NotSet(0),
    Fighter(1),
    Scavenger(2),
    Farmer(3),
    Trading(4);

    private int value;
    private WolfType(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
