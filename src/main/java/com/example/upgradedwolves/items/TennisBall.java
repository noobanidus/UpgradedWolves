package com.example.upgradedwolves.items;

import com.example.upgradedwolves.UpgradedWolves;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class TennisBall extends Item {

    static private final int TENNIS_BALL_STACK_SIZE = 8;

    public TennisBall() {
        super(new Item.Properties().maxStackSize(TENNIS_BALL_STACK_SIZE).group(ItemGroup.MISC));
        this.setRegistryName(UpgradedWolves.getId("tennis_ball"));
    }
    
}
