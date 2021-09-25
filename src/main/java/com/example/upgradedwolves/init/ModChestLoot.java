package com.example.upgradedwolves.init;

import com.example.upgradedwolves.loot_table.init.ModGlobalLootTableModifier;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ModChestLoot{
    
    public ModChestLoot(){
        ModGlobalLootTableModifier.LOOT_MODIFIERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
