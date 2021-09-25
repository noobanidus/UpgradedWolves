package com.example.upgradedwolves.items;

import com.example.upgradedwolves.UpgradedWolves;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.LeadItem;


public class TugOfWarRopeItem extends LeadItem {
    public TugOfWarRopeItem() {
        super(new Item.Properties().stacksTo(1));
        this.setRegistryName(UpgradedWolves.getId("tug_of_war_rope"));
    }
    
    public boolean canLeashMob(Mob entity, Player player) {
		return entity instanceof Wolf;
	}
    
}
