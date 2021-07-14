package com.example.upgradedwolves.items;

import com.example.upgradedwolves.UpgradedWolves;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.LeadItem;


public class TugOfWarRopeItem extends LeadItem {
    public TugOfWarRopeItem() {
        super(new Item.Properties().maxStackSize(1).group(ItemGroup.MISC));
        this.setRegistryName(UpgradedWolves.getId("tug_of_war_rope"));
    }
    
    public boolean canLeashMob(MobEntity entity, PlayerEntity player) {
		return entity instanceof WolfEntity;
	}
    
}
